package accountsystem;

import equipment.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Klasa reprezentująca użytkownika systemu wypożyczania sprzętu sportowego.
 * Dziedziczy po klasie Account i pozwala na wypożyczanie oraz zwracanie sprzętu.
 */
public class User extends Account{
    /**
     * Lista aktywnych wypożyczeń użytkownika
     */
    private List<Rental> rentals;

    public User(String login, String password) {
        super(login, password);
        this.rentals = new ArrayList<>();
    }

    @Override
    public void showProfile() {
        System.out.println("Użytkownik: "+ login);
    }

    /**
     * Metoda pozwalająca użytkownikowi wypożyczyć określony sprzęt..
     * @param equipment Typ sprzętu do wypożyczenia
     * @param quantity Ilość sprzętu do wypożyczenia
     */
    public void rentEquipment(EquipmentType equipment, int quantity) {
        if (equipment.getQuantity() >= quantity) {
            //Aktualizacja ilości sprzętu w magazynie
            equipment.setQuantity(equipment.getQuantity() - quantity);
            System.out.println("Sprzęt został wypożyczony.");
            //Tworzymy nowe wypożyczenie i dodajemy je do listy oraz rejestru
            Rental rental = new SimpleRental(equipment, quantity);
            rentals.add(rental);
            RentalRegistry.registerRental(rental);
        } else {
            System.out.println("Niewystarczająca ilość sprzętu dostępna.");
        }
    }

    /**
     * Metoda pozwalająca użytkownikowi zwrócić wypożyczony sprzęt po nazwie.
     * @param equipmentName Nazwa zwracanego sprzętu
     */
    public void returnEquipment(String equipmentName) {
        Iterator<Rental> iterator = rentals.iterator();
        boolean found = false;
        while (iterator.hasNext()) {
            Rental rental = iterator.next();
            if (rental.getEquipment().getName().equalsIgnoreCase(equipmentName)) {
                //Zwiększamy ilość sprzętu w magazynie o ilość zwróconą
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

    /**
     * Oblicza łączną kwotę do zapłaty za wszystkie aktywne wypożyczenia użytkownika.
     * @return Suma kosztów wypożyczeń
     */
    public double getTotalBill() {
        double total = 0;
        for (Rental rental : rentals) {
            total += rental.getTotalPrice();
        }
        return total;
    }

    /**
     * Wyświetla listę aktywnych wypożyczeń oraz łączną kwotę do zapłaty.
     */
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
