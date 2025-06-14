package accountsystem;

/**
 * Klasa abstrakcyjna reprezentująca ogólne konto użytkownika systemu.
 */
public abstract class Account {
    protected String login; // Login użytkownika
    protected String password; //Hasło użytkownika

    public Account(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Account(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    abstract public void showProfile();
}
