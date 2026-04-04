package com.foodapp.model;

/**
 * MenuCategory domain entity.
 */
public class MenuCategory {
    private String id;
    private String restaurantId;
    private String name;
    private int displayOrder;

    public MenuCategory() {}

    public MenuCategory(String id, String restaurantId, String name, int displayOrder) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.name = name;
        this.displayOrder = displayOrder;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRestaurantId() { return restaurantId; }
    public void setRestaurantId(String restaurantId) { this.restaurantId = restaurantId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }

    @Override
    public String toString() {
        return "MenuCategory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", displayOrder=" + displayOrder +
                '}';
    }
}
