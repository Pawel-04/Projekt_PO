package accountsystem;

/**
 * Klasa reprezentująca administratora systemu.
 * Dziedziczy po klasie Account i dodaje poziom uprawnień.
 */
public class Admin extends Account{
    private int level;

    public Admin(String login, String password, int level) {
        super(login, password);
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public void showProfile() {
        System.out.println("Administrator: "+ login+ ", poziom: "+ level);
    }
}
