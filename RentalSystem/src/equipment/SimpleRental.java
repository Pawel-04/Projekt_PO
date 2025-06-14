package equipment;

/**
 * Klasa reprezentująca proste wypożyczenie sprzętu.
 * Zawiera informacje o rodzaju sprzętu, liczbie wypożyczonych sztuk oraz statusie zwrotu.
 */
public class SimpleRental implements Rental{
    private final EquipmentType equipment;
    private final int quantity;
    private boolean returned = false;

    /**
     * Tworzy nowe wypożyczenie sprzętu.
     * @param equipment obiekt sprzętu do wypożyczenia
     * @param quantity ilość sztuk sprzętu do wypożyczenia (musi być > 0)
     */
    public SimpleRental(EquipmentType equipment, int quantity) {
        this.equipment = equipment;
        if (quantity <= 0) {
            throw new IllegalArgumentException("Ilość musi być większa od zera");
        }
        this.quantity = quantity;
    }

    /**
     * Ustawia status wypożyczenia jako zwrócony lub niezwrócony.
     * @param returned
     */
    @Override
    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    @Override
    public EquipmentType getEquipment() {
        return equipment;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    /**
     * Oblicza całkowity koszt wypożyczenia.
     * @return cena całkowita to (ilość × cena jednostkowa)
     */
    @Override
    public double getTotalPrice() {
        return quantity * equipment.getPrice();
    }

    /**
     * Zwraca tekstową reprezentację wypożyczenia.
     * @return opis wypożyczenia
     */
    @Override
    public String toString() {
        return "Sprzęt " +equipment.getName()+
                " | Ilość: " +quantity+ " | Razem: " +getTotalPrice();
    }
}
