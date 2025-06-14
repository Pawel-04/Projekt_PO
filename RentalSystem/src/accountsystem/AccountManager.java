package accountsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
* Klasa zarządzająca kontami zwykłych użytkowników i administratorów.
* Pozwala na dodawanie, logowanie i wyświetlanie kont oraz ich wczytywanie z pliku.
*/
public class AccountManager {
    //Przechowuje konta użytkowników
    private final Map<String, User> users = new HashMap<>();
    //Przechowuje konta administratorów
    private final Map<String, Admin> admins = new HashMap<>();

    /**
     * Dodawanie nowego użytkownika, jeśli login nie jest już zajęty.
     * @param login Login użytkownika
     * @param password Hasło użytkownika
     */
    public void addUser(String login, String password) {
        if (users.containsKey(login)) {
            System.out.println("Użytkownik o takim loginie już istnieje.");
            return;
        }
        if (admins.containsKey(login)) {
            System.out.println("Login jest już zajęty przez administratora.");
            return;
        }
        users.put(login, new User(login, password));
        System.out.println("Dodano użytkownika: " +login);
    }

    /**
     * Dodawanie nowego administratora z określeniem poziomu uprawnień, jeśli login nie jest już zajęty.
     * @param login Login administratora
     * @param password Hasło administratora
     * @param level Poziom uprawnień (1-2)
     */
    public void addAdmin(String login, String password, int level) {
        if (level < 1 || level > 2) {
            System.out.println("Poziom administratora musi być w zakresie od 1 do 2.");
            return;
        }
        if (admins.containsKey(login)) {
            System.out.println("Administrator o takim loginie już istnieje.");
            return;
        }
        if (users.containsKey(login)) {
            System.out.println("Login jest już zajęty przez użytkownika");
            return;
        }
        admins.put(login, new Admin(login, password, level));
        System.out.println("Dodano administratora: " +login);
    }

    /**
     * Logowanie użytkownika do systemu.
     * @param login Login
     * @param password Hasło
     * @return Zalogowany obiekt Account (User lub Admin) lub null jeśli dane są niepoprawne
     */
    public Account login(String login, String password) {
        User user = users.get(login);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Zalogowano jako użytkownik: " +login);
            return user;
        }

        Admin admin = admins.get(login);
        if (admin != null && admin.getPassword().equals(password)) {
            System.out.println("Zalogowano jako administrator: " +login);
            return admin;
        }
        return null;
    }

    /**
     * Wyświetlenie wszytskich kont znajdujących się w systemie.
     */
    public void showAccounts() {
        if (users.isEmpty() && admins.isEmpty()) {
            System.out.println("Nie ma żadnego konta w systemie.\n");
        } else {
            System.out.println("Istniejące konta: \n");
            admins.forEach((login, admin) -> System.out.println("# " +login +" - " +admin.getLevel() +" poziom."));
            users.forEach((login, user) -> System.out.println("# " +login +" | Hasło: " +user.getPassword()));
        }
    }

    /**
     * Wczytywanie kont użytkowników i administratorów z pliku tekstowego.
     * Każda linia powinna mieć format:
     * - admin;login;hasło;poziom
     * -user;login;hasło
     * @param filename Wczytuje konta z domyślnego pliku 'data.txt'
     */
    public void loadAccountsFromFile (String filename) {
        try {
            File plik = new File("data.txt");
            Scanner scanner = new Scanner(plik);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");
                // Dzielimy linię tekstu na segmenty (rozdzielone średnikiem), aby wyodrębnić typ konta, login, hasło i poziom
                if (parts.length > 0) {
                    if (parts[0].equalsIgnoreCase("admin") && parts.length == 4) {
                        String login = parts[1];
                        String password = parts[2];
                        int level = Integer.parseInt(parts[3]);
                        addAdmin(login, password, level);
                    } else if (parts[0].equalsIgnoreCase("user") && parts.length == 3) {
                        String login = parts[1];
                        String password = parts[2];
                        addUser(login, password);
                    } else {
                        System.out.println("Niepoprawny format linii: " +line);
                    }
                }
            }
            scanner.close();
            System.out.println("Wczytano konta z pliku: " +filename);
        } catch (FileNotFoundException e) {
            System.out.println("Plik nie znaleziony.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Błędny format liczby w pliku: " +e.getMessage());
        }
    }
}
