public class Main {
    public static void main(String[] args) {

        Menu menu = new Menu();

        menu.getAccountManager().loadAccountsFromFile("data.txt");
        menu.getEquipmentManager().loadEquipmentFromFile("equipment.txt");
        menu.showMainMenu();
    }
}