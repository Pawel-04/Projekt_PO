package equipment;

import exception.EquipmentNotFoundException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class EquipmentManager {
    private final Map<String, EquipmentType> equipmentTypeMap = new HashMap<>();

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
            equipment.setQuantity(equipment.getQuantity() + amount);
            equipment.setPrice(price);
            System.out.println("Zwiększono ilość sprzętu: " +name +" o " +amount +" sztuk. Zaktualizowano cenę do: " +price +" zł.");
        } else {
            equipmentTypeMap.put(key, new EquipmentType(name, amount, price));
            System.out.println("Dodano nowy sprzęt: " +name +" w ilości: " +amount +" sztuk. Cena: " +price +" zł.");
        }
    }

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

    public List<EquipmentType> getAvailableEquipment() {
        List<EquipmentType> available = new ArrayList<>();
        for (EquipmentType e : equipmentTypeMap.values()) {
            if (e.getQuantity() > 0) {
                available.add(e);
            }
        }
        return available;
    }

    public EquipmentType findEquipmentByName(String name) throws EquipmentNotFoundException {
        EquipmentType equipment = equipmentTypeMap.get(name.toLowerCase());
        if (equipment == null) {
            throw new EquipmentNotFoundException("Nie znaleziono sprzętu o nazwie: " +name);
        }
        return equipment;
    }

    public void loadEquipmentFromFile(String filename) {
        try {
            File plik = new File("equipment.txt");
            Scanner scanner = new Scanner(plik);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");
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
