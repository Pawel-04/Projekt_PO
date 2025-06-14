package equipment;

public class SimpleRental implements Rental{
    private final EquipmentType equipment;
    private final int quantity;
    private boolean returned = false;

    public SimpleRental(EquipmentType equipment, int quantity) {
        this.equipment = equipment;
        if (quantity <= 0) {
            throw new IllegalArgumentException("Ilość musi być większa od zera");
        }
        this.quantity = quantity;
    }

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

    @Override
    public double getTotalPrice() {
        return quantity * equipment.getPrice();
    }

    @Override
    public String toString() {
        return "Sprzęt " +equipment.getName()+
                " | Ilość: " +quantity+ " | Razem: " +getTotalPrice();
    }
}
