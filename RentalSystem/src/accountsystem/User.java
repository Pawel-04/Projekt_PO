package accountsystem;

public class User extends Account{

    public User(String login, String password) {
        super(login, password);
    }

    @Override
    public void showProfile() {
        System.out.println("Użytkownik: "+ login);
    }
}
