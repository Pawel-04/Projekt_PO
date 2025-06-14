import accountsystem.*;
import equipment.*;
import exception.EquipmentNotFoundException;

import java.util.List;
import java.util.Scanner;

/**
 * Klasa obsługująca interfejs użytkownika w konsoli.
 * Umożliwia rejestrację, logowanie, zarządzanie sprzętem oraz wypożyczeniami.
 */
public class Menu {
    private final AccountManager accountManager = new AccountManager();
    private final EquipmentManager equipmentManager = new EquipmentManager();
    Scanner scanner = new Scanner(System.in);

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public EquipmentManager getEquipmentManager() {
        return equipmentManager;
    }

    /**
     * Wyświetlenie głównego menu programu i przetwarzanie wybór użytkownika.
     */
    public void showMainMenu() {
        while (true) {
            System.out.println("\n=== MENU GŁÓWNE ===");
            System.out.println("1. Rejestracja konta");
            System.out.println("2. Logowanie");
            System.out.println("0. Wyjście");
            System.out.print("Wybierz opcję: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> registerAccount();
                    case 2 -> login();
                    case 0 -> {
                        System.out.println("Zamykam program.");
                        return;
                    }
                    default -> System.out.println("Nieprawidłowa opcja.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Nieprawidłowa opcja, spróbuj ponownie.");
            }
        }
    }

    /**
     * Proces rejestracji nowego konta (admin lub user).
     */
    private void registerAccount() {
        System.out.print("Nazwa użytkownika: ");
        String login = scanner.nextLine();
        System.out.print("Hasło: ");
        String password = scanner.nextLine();
        System.out.print("Typ konta (admin/user): ");
        String type = scanner.nextLine();

        if (type.equalsIgnoreCase("admin")) {
            System.out.print("Poziom admina: ");
            int level = scanner.nextInt();
            scanner.nextLine();
            accountManager.addAdmin(login, password, level);
        } else if (type.equalsIgnoreCase("user")) {
            accountManager.addUser(login, password);
        } else {
            System.out.println("Nieznany typ konta.");
        }
    }

    /**
     * Obsługuje logowanie użytkownika do systemu.
     */
    private void login() {
        System.out.print("Nazwa użytkownika: ");
        String login = scanner.nextLine();
        System.out.print("Hasło: ");
        String password = scanner.nextLine();

        Account account = accountManager.login(login, password);

        if (account instanceof Admin admin) {
            adminMenu(admin);
        } else if (account instanceof User user) {
            userMenu(user);
        } else {
            System.out.println("Niepoprawne dane logowania.");
        }
    }

    /**
     * Menu dostępne dla zalogowanego administratora.
     * @param admin zalogowany administrator
     */
    private void adminMenu(Admin admin) {
        while (true) {
            System.out.println("\n=== MENU ADMINA ===");
            System.out.println("1. Dodaj sprzęt");
            System.out.println("2. Usuń sprzęt");
            System.out.println("3. Pokaż dostępny sprzęt");
            System.out.println("4. Wygeneruj raport z wypożyczeń");
            System.out.println("5. Sprawdź dane użytkowników systemu");
            System.out.println("0. Wyloguj");
            System.out.print("Wybierz opcję: ");

            try{
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> addEquipment();
                    case 2 -> removeEquipment();
                    case 3 -> showAvailableEquipment();
                    case 4 -> RentalRegistry.printAllRentals();
                    case 5 -> {
                        if (admin.getLevel() == 2) {
                            accountManager.showAccounts();
                        } else {
                            System.out.println("Brak uprawnień do wykonania tej operacji.");
                        }
                    }
                    case 0 -> {
                        System.out.println("Wylogowano.");
                        return;
                    }
                    default -> System.out.println("Nieprawidłowa opcja.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Nieprawidłowa opcja, spróbuj ponownie.");
            }
        }
    }

    /**
     * Metoda dodająca nowy sprzęt do systemu
     */
    private void addEquipment() {
        System.out.print("Nazwa sprzętu: ");
        String name = scanner.nextLine();

        int quantity;
        double price;

        try {
            System.out.print("Ilość: ");
            String tempQuantity = scanner.nextLine().trim();
            quantity = Integer.parseInt(tempQuantity);
            if (quantity <= 0) {
                System.out.println("Ilość musi być większa od zera.");
                return;
            }

            System.out.print("Cena za wynajem: ");
            String tempPrice = scanner.nextLine().trim();
            price = Double.parseDouble(tempPrice);
            if (price <= 0) {
                System.out.println("Cena musi być większa od zera.");
                return;
            }
            // Dodanie sprzętu do systemu (lub aktualizacja istniejącego)
            equipmentManager.addEquipment(name, quantity, price);
        } catch (NumberFormatException e) {
            System.out.println("Błędny format liczby. Spróbuj ponownie.");
        }
    }

    /**
     * Usuwanie sprzętu z magazynu
     */
    private void removeEquipment() {
        System.out.print("Nazwa sprzętu do usunięcia: ");
        String name = scanner.nextLine();
        equipmentManager.removeEquipment(name);
    }

    /**
     * Wyświetla listę dostępnego sprzętu w magazynie.
     */
    private void showAvailableEquipment() {
        List<EquipmentType> equipment = equipmentManager.getAvailableEquipment();
        if (equipment.isEmpty()) {
            System.out.println("Brak sprzętu.");
        } else {
            equipment.forEach(System.out::println);
        }
    }

    /**
     * Menu dostępne dla zalogowanego użytkownika.
     * @param user zalogowany użytkownik
     */
    private void userMenu(User user) {
        while (true) {
            System.out.println("\n=== MENU UŻYTKOWNIKA ===");
            System.out.println("1. Wypożycz sprzęt");
            System.out.println("2. Zwróć sprzęt");
            System.out.println("3. Pokaż wypożyczenia i rachunek");
            System.out.println("4. Pokaż dostępny sprzęt");
            System.out.println("0. Wyloguj");
            System.out.print("Wybierz opcję: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> rentEquipment(user);
                    case 2 -> {
                        System.out.print("Podaj nazwę sprzętu do zwrotu: ");
                        String name = scanner.nextLine();
                        user.returnEquipment(name);
                    }
                    case 3 -> user.showRentals();
                    case 4 -> showAvailableEquipment();
                    case 0 -> {
                        System.out.println("Wylogowano.");
                        return;
                    }
                    default -> System.out.println("Nieprawidłowa opcja.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Nieprawidłowa opcja, spróbuj ponownie.");
            }
        }
    }

    private void rentEquipment(User user) {
        List<EquipmentType> available = equipmentManager.getAvailableEquipment();
        if (available.isEmpty()) {
            System.out.println("Brak sprzętu do wypożyczenia.");
        } else {
            System.out.println("Dostępny sprzęt:");
            for (EquipmentType eq : available) {
                System.out.println(eq);
            }
            System.out.print("Nazwa sprzętu: ");
            String name = scanner.nextLine();
            EquipmentType equipment = null;
            try {
                equipment = equipmentManager.findEquipmentByName(name);
            } catch (EquipmentNotFoundException e) {
                System.out.println(e.getMessage());
                return;
            }
            if (equipment != null) {
                try {
                    System.out.print("Ilość do wypożyczenia: ");
                    int qty = Integer.parseInt(scanner.nextLine());
                    if (qty <= 0) {
                        System.out.println("Ilość musi być większa od zera.");
                        return;
                    }
                    user.rentEquipment(equipment, qty);
                } catch (NumberFormatException e) {
                    System.out.println("Nieprawidłowa ilość. Wprowadź liczbę.");
                }
            }
        }
    }
}
