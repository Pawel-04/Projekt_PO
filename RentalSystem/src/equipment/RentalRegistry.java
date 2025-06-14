package equipment;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RentalRegistry {
    private static final List<Rental> allRentals = new ArrayList<>();

    public static void registerRental(Rental rental) {
        allRentals.add(rental);
    }

    public static double getTotalRevenue() {
        double sum = 0;
        for (Rental r : allRentals) {
            sum += r.getTotalPrice();
        }
        return sum;
    }

    public static void printAllRentals() {
        if (allRentals.isEmpty()) {
            System.out.println("Brak zarejestrowanych wypożyczeń.");
        } else {
            try (FileWriter writer =new FileWriter("wyniki.txt")) {
                writer.write("=== WSZYSTKIE WYPOŻYCZENIA ===\n\n");
                for (Rental rental : allRentals) {
                    writer.write(rental + "\n");
                }
                writer.write("Łączny utarg: " +getTotalRevenue());
                writer.close();
                System.out.println("Raport został zapisany do pliku 'wyniki.txt'.");
            } catch (IOException e) {
                System.out.println("Błąd zapisu do pliku: "+ e.getMessage());
            }
        }
    }
}
