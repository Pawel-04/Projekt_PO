package equipment;

/**
 * Klasa reprezentująca typ sprzętu w systemie.
 * Przechowuje nazwę, ilość oraz cenę danego sprzętu.
 */
public class EquipmentType {
    private String name;
    private int quantity;
    private double price;

    /**
     * Tworzy nowy obiekt typu sprzętu.
     * @param name Nazwa sprzętu
     * @param quantity Ilość dostępnych sztuk (musi być > 0)
     * @param price Cena jednej sztuki (musi być > 0)
     */
    public EquipmentType(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Ilość musi być większa od zera!");
        }
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Cena musi być większa od zera!");
        }
        this.price = price;
    }

    @Override
    public String toString() {
        return "Sprzęt: " + name + " | Stan: " + quantity + " | Cena: " + price + " zł/szt.";
    }
}
