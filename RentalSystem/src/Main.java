public class Main {
    public static void main(String[] args) {

        //Tworzę nowy obiekt Menu, który zarządza logiką aplikacji
        Menu menu = new Menu();

        //Wczytywanie  użytkowników i administratorów z pliku "data.txt"
        menu.getAccountManager().loadAccountsFromFile("data.txt");
        //Wczytywanie dostępnego sprzęt z pliku "equipment.txt"
        menu.getEquipmentManager().loadEquipmentFromFile("equipment.txt");
        //Uruchomienie głównego menu aplikacji
        menu.showMainMenu();
    }
}