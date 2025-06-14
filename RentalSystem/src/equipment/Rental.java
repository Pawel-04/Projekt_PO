package equipment;

public interface Rental {
    EquipmentType getEquipment();
    int getQuantity();
    double getTotalPrice();
    void setReturned(boolean b);
}
