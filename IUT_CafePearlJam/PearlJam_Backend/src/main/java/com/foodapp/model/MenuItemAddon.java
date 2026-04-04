package com.foodapp.model;

/**
 * MenuItemAddon domain entity.
 */
public class MenuItemAddon {
    private String id;
    private String menuItemId;
    private String name;
    private double extraPrice;
    private boolean isAvailable;

    public MenuItemAddon() {}

    public MenuItemAddon(String id, String menuItemId, String name, double extraPrice, boolean isAvailable) {
        this.id = id;
        this.menuItemId = menuItemId;
        this.name = name;
        this.extraPrice = extraPrice;
        this.isAvailable = isAvailable;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMenuItemId() { return menuItemId; }
    public void setMenuItemId(String menuItemId) { this.menuItemId = menuItemId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getExtraPrice() { return extraPrice; }
    public void setExtraPrice(double extraPrice) { this.extraPrice = extraPrice; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    @Override
    public String toString() {
        return "MenuItemAddon{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", extraPrice=" + extraPrice +
                '}';
    }
}
