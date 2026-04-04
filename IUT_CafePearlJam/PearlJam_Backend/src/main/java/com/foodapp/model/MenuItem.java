package com.foodapp.model;

/**
 * MenuItem domain entity.
 */
public class MenuItem {
    private String id;
    private String categoryId;
    private String restaurantId;
    private String name;
    private String description;
    private double basePrice;
    private String imageUrl;
    private boolean isAvailable;
    private boolean trackQuantity;
    private Integer quantityInStock;
    private Integer preparationTimeMinutes;
    private String createdAt;

    public MenuItem() {}

    public MenuItem(String id, String categoryId, String restaurantId, String name, String description, 
                    double basePrice, String imageUrl, boolean isAvailable, boolean trackQuantity, 
                    Integer quantityInStock, Integer preparationTimeMinutes, String createdAt) {
        this.id = id;
        this.categoryId = categoryId;
        this.restaurantId = restaurantId;
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        this.imageUrl = imageUrl;
        this.isAvailable = isAvailable;
        this.trackQuantity = trackQuantity;
        this.quantityInStock = quantityInStock;
        this.preparationTimeMinutes = preparationTimeMinutes;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }

    public String getRestaurantId() { return restaurantId; }
    public void setRestaurantId(String restaurantId) { this.restaurantId = restaurantId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double basePrice) { this.basePrice = basePrice; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    public boolean isTrackQuantity() { return trackQuantity; }
    public void setTrackQuantity(boolean trackQuantity) { this.trackQuantity = trackQuantity; }

    public Integer getQuantityInStock() { return quantityInStock; }
    public void setQuantityInStock(Integer quantityInStock) { this.quantityInStock = quantityInStock; }

    public Integer getPreparationTimeMinutes() { return preparationTimeMinutes; }
    public void setPreparationTimeMinutes(Integer preparationTimeMinutes) { this.preparationTimeMinutes = preparationTimeMinutes; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", basePrice=" + basePrice +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
