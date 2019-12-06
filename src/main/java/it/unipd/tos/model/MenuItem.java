////////////////////////////////////////////////////////////////////
// [Nicolo] [Fassina] [1166190]
////////////////////////////////////////////////////////////////////

package it.unipd.tos.model;

public class MenuItem {
    public enum ItemType {Panini, Fritti, Bevande};
    private ItemType type;
    private String name;
    private double price;

    public MenuItem (ItemType itemType, String itemName, double itemPrice) {
        type = itemType;
        name = itemName;
        price = itemPrice;
    }

    public ItemType getItemType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
