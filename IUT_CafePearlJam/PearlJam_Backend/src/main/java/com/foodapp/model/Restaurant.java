package com.foodapp.model;

/**
 * Restaurant domain entity.
 */
public class Restaurant {
    private String id;
    private String ownerId;
    private String name;
    private String description;
    private String address;
    private String area;
    private double latitude;
    private double longitude;
    private String phone;
    private String cuisineType;
    private boolean isActive;
    private String opensAt;
    private String closesAt;
    private String createdAt;

    public Restaurant() {}

    public Restaurant(String id, String ownerId, String name, String description, String address, String area, 
                      double latitude, double longitude, String phone, String cuisineType, 
                      boolean isActive, String opensAt, String closesAt, String createdAt) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.area = area;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
        this.cuisineType = cuisineType;
        this.isActive = isActive;
        this.opensAt = opensAt;
        this.closesAt = closesAt;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCuisineType() { return cuisineType; }
    public void setCuisineType(String cuisineType) { this.cuisineType = cuisineType; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public String getOpensAt() { return opensAt; }
    public void setOpensAt(String opensAt) { this.opensAt = opensAt; }

    public String getClosesAt() { return closesAt; }
    public void setClosesAt(String closesAt) { this.closesAt = closesAt; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", cuisineType='" + cuisineType + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
