package accountsystem;

public abstract class Account {
    protected String login;
    protected String password;

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
