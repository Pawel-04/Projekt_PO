package accountsystem;

import equipment.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class User extends Account{
    private List<Rental> rentals;

    public User(String login, String password, List<Rental> rentals) {
        super(login, password);
        this.rentals = new ArrayList<>();
    }

    public User(String login, String password) {
        super(login, password);
    }

    @Override
    public void showProfile() {
        System.out.println("Użytkownik: "+ login);
    }

    public void rentEquipment(EquipmentType equipment, int quantity) {
        if (equipment.getQuantity() >= quantity) {
            equipment.setQuantity(equipment.getQuantity() - quantity);
            System.out.println("Sprzęt został wypożyczony.");
            Rental rental = new SimpleRental(equipment, quantity);
            rentals.add(rental);
            RentalRegistry.registerRental(rental);
        } else {
            System.out.println("Niewystarczająca ilość sprzętu dostępna.");
        }
    }

    public void returnEquipment(String equipmentName) {
        Iterator<Rental> iterator = rentals.iterator();
        boolean found = false;
        while (iterator.hasNext()) {
            Rental rental = iterator.next();
            if (rental.getEquipment().getName().equalsIgnoreCase(equipmentName)) {
                rental.getEquipment().setQuantity(rental.getEquipment().getQuantity() + rental.getQuantity());
                rental.setReturned(true);
                iterator.remove();
                found = true;
                System.out.println("Sprzęt zwrócony.");
            }
        }
        if (!found) {
            System.out.println("Nie znaleziono wypożyczenia o podanej nazwie.");
        }
    }

    public double getTotalBill() {
        double total = 0;
        for (Rental rental : rentals) {
            total += rental.getTotalPrice();
        }
        return total;
    }

    public void showRentals() {
        if (rentals.isEmpty()) {
            System.out.println("Brak aktywnych wypożyczeń.");
        } else {
            for (Rental rental : rentals) {
                System.out.println(rental);
            }
            System.out.println("Łączna kwota do zapłaty: " +getTotalBill()+ " zł.");
        }
    }
}
