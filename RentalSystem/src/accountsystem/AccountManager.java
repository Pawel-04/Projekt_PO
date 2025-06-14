package accountsystem;

import java.util.HashMap;
import java.util.Map;

public class AccountManager {
    private Map<String, User> users = new HashMap<>();
    private Map<String, Admin> admins = new HashMap<>();

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

    public void addAdmin(String login, String password, int level) {
        if (level < 1 || level > 3) {
            System.out.println("Poziom administratora musi być w zakresie od 1 do 3.");
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

    public void showAccounts() {
        if (users.isEmpty() && admins.isEmpty()) {
            System.out.println("Nie ma żadnego konta w systemie.\n");
        } else {
            System.out.println("Istniejące konta: \n");
            admins.forEach((login, admin) -> System.out.println("# " +login +" - " +admin.getLevel() +" poziom."));
            users.forEach((login, user) -> System.out.println("# " +login +" | Hasło: " +user.getPassword()));
        }
    }
}
