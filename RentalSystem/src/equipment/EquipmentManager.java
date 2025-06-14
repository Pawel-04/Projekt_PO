package equipment;

import exception.EquipmentNotFoundException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Klasa zarządzająca sprzętem w systemie.
 * Pozwala na dodawanie i usuwanie oraz ładowanie sprzętu z pliku.
 */
public class EquipmentManager {
    //Mapa przechowująca sprzęt według nazwy (z małych liter dla ułatwienia porównań)
    private final Map<String, EquipmentType> equipmentTypeMap = new HashMap<>();

    /**
     * Dodaje nowy sprzęt lub zwiększa ilość już istniejącego, aktualizując jego cenę,
     * @param name Nazwa sprzętu
     * @param amount Ilość do dodania (musi być > 0)
     * @param price Cena za jednostkę (musi być > 0)
     */
    public void addEquipment(String name, int amount, double price) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Ilość musi być większa od zera");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Cena musi być większa od zera");
        }

        String key = name.toLowerCase();

        EquipmentType equipment = equipmentTypeMap.get(key);
        if (equipment != null) {
            //Jeśli sprzęt już istnieje, zwiększamy jego ilość i aktualizujemy cenę
            equipment.setQuantity(equipment.getQuantity() + amount);
            equipment.setPrice(price);
            System.out.println("Zwiększono ilość sprzętu: " +name +" o " +amount +" sztuk. Zaktualizowano cenę do: " +price +" zł.");
        } else {
            //Jeśli to nowy sprzęt – dodajemy go do mapy
            equipmentTypeMap.put(key, new EquipmentType(name, amount, price));
            System.out.println("Dodano nowy sprzęt: " +name +" w ilości: " +amount +" sztuk. Cena: " +price +" zł.");
        }
    }

    /**
     * Usuwa sprzęt o podanej nazwie z systemu.
     * @param name Nazwa sprzętu do usunięcia
     */
    public void removeEquipment(String name) {
        String key = name.toLowerCase();
        EquipmentType equipment = equipmentTypeMap.get(key);
        if (equipment != null) {
            equipmentTypeMap.remove(key);
            System.out.println("Usunięto sprzęt: " +name);
        } else {
            System.out.println("Nie znaleziono sprzętu: " +name);
        }
    }

    /**
     * Zwraca listę sprzętów, które są aktualnie dostępne.
     * @return Lista dostępnego sprzętu
     */
    public List<EquipmentType> getAvailableEquipment() {
        List<EquipmentType> available = new ArrayList<>();
        for (EquipmentType e : equipmentTypeMap.values()) {
            if (e.getQuantity() > 0) {
                available.add(e);
            }
        }
        return available;
    }

    /**
     * Wyszukuje sprzęt po nazwie.
     * @param name Nazwa sprzętu
     * @return Znaleziony obiekt sprzętu
     * @throws EquipmentNotFoundException jeśli nie znaleziono sprzętu o podanej nazwie rzuca wyjątek
     */
    public EquipmentType findEquipmentByName(String name) throws EquipmentNotFoundException {
        EquipmentType equipment = equipmentTypeMap.get(name.toLowerCase());
        if (equipment == null) {
            throw new EquipmentNotFoundException("Nie znaleziono sprzętu o nazwie: " +name);
        }
        return equipment;
    }

    /**
     * Wczytuje dane sprzętowe z pliku tekstowego.
     * Format każdej linii: nazwa;ilość;cena
     * @param filename Ścieżka do pliku wejściowego, w tym przypadku odnoszę się do domyślnego pliku 'equipment.txt'.
     */
    public void loadEquipmentFromFile(String filename) {
        try {
            File plik = new File("equipment.txt");
            Scanner scanner = new Scanner(plik);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");
                //Oczekiwany format: nazwa;ilość;cena
                if (parts.length == 3) {
                    String name = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    double price = Double.parseDouble(parts[2]);
                    addEquipment(name, quantity, price);
                } else {
                    System.out.println("Niepoprawny format linii: " +line);
                }
            }
            System.out.println("Wczytano sprzęt z pliku: " +filename);
        } catch (FileNotFoundException e) {
            System.out.println("Plik nie został znaleziony.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Błędny format liczby w pliku: " +e.getMessage());
        }
    }
}
