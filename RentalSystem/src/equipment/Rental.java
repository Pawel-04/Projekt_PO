package equipment;

/**
 * Interfejs reprezentujący wypożyczenie sprzętu.
 * Definiuje metody do pobierania informacji o wypożyczeniu oraz jego statusie.
 */
public interface Rental {
    EquipmentType getEquipment();
    int getQuantity();
    double getTotalPrice();
    void setReturned(boolean b);
}
