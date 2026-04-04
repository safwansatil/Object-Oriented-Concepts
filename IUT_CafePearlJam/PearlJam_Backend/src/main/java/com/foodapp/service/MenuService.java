package com.foodapp.service;

import com.foodapp.dao.MenuCategoryDAO;
import com.foodapp.dao.MenuItemAddonDAO;
import com.foodapp.dao.MenuItemDAO;
import com.foodapp.dao.RestaurantDAO;
import com.foodapp.exception.DatabaseException;
import com.foodapp.exception.ValidationException;
import com.foodapp.model.MenuCategory;
import com.foodapp.model.MenuItem;
import com.foodapp.model.MenuItemAddon;
import com.foodapp.util.TimeUtil;
import com.foodapp.util.UUIDGenerator;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for menu management and lookup.
 */
public class MenuService {
    private final MenuCategoryDAO categoryDAO;
    private final MenuItemDAO itemDAO;
    private final MenuItemAddonDAO addonDAO;
    private final RestaurantDAO restaurantDAO;

    public MenuService(MenuCategoryDAO categoryDAO, MenuItemDAO itemDAO, MenuItemAddonDAO addonDAO, RestaurantDAO restaurantDAO) {
        this.categoryDAO = categoryDAO;
        this.itemDAO = itemDAO;
        this.addonDAO = addonDAO;
        this.restaurantDAO = restaurantDAO;
    }

    /**
     * Adds a new category to a restaurant.
     */
    public MenuCategory addCategory(String restaurantId, String name, int displayOrder) {
        try {
            if (restaurantDAO.findById(restaurantId).isEmpty()) {
                throw new ValidationException("Restaurant not found.");
            }
            MenuCategory category = new MenuCategory(UUIDGenerator.generate(), restaurantId, name, displayOrder);
            categoryDAO.save(category);
            return category;
        } catch (DatabaseException e) {
            throw new RuntimeException("Service error adding category", e);
        }
    }

    /**
     * Adds a new menu item.
     */
    public MenuItem addItem(String restaurantId, String categoryId, String name, String description, double basePrice, 
                            String imageUrl, Integer prepTime, boolean trackQuantity, Integer initialStock) {
        try {
            String id = UUIDGenerator.generate();
            String createdAt = TimeUtil.nowISO();
            MenuItem item = new MenuItem(id, categoryId, restaurantId, name, description, basePrice, imageUrl, true, trackQuantity, initialStock, prepTime, createdAt);
            itemDAO.save(item);
            return item;
        } catch (DatabaseException e) {
            throw new RuntimeException("Service error adding menu item", e);
        }
    }

    /**
     * Adds an addon to a menu item.
     */
    public MenuItemAddon addAddon(String menuItemId, String name, double extraPrice) {
        try {
            MenuItemAddon addon = new MenuItemAddon(UUIDGenerator.generate(), menuItemId, name, extraPrice, true);
            addonDAO.save(addon);
            return addon;
        } catch (DatabaseException e) {
            throw new RuntimeException("Service error adding addon", e);
        }
    }

    /**
     * Returns the full menu as an ordered map: category → items under it.
     */
    public Map<MenuCategory, List<MenuItem>> getFullMenu(String restaurantId) {
        try {
            List<MenuCategory> categories = categoryDAO.findByRestaurantId(restaurantId);
            List<MenuItem> items = itemDAO.findByRestaurantId(restaurantId);
            
            Map<MenuCategory, List<MenuItem>> fullMenu = new LinkedHashMap<>();
            for (MenuCategory category : categories) {
                List<MenuItem> categoryItems = items.stream()
                        .filter(i -> i.getCategoryId().equals(category.getId()))
                        .collect(Collectors.toList());
                fullMenu.put(category, categoryItems);
            }
            return fullMenu;
        } catch (DatabaseException e) {
            throw new RuntimeException("Service error getting full menu", e);
        }
    }

    /**
     * Sets item availability.
     */
    public void setItemAvailability(String itemId, boolean available) {
        try {
            itemDAO.updateAvailability(itemId, available);
        } catch (DatabaseException e) {
            throw new RuntimeException("Service error updating availability", e);
        }
    }

    /**
     * Updates item stock.
     */
    public void updateItemStock(String itemId, int newQuantity) {
        try {
            itemDAO.updateStock(itemId, newQuantity);
        } catch (DatabaseException e) {
            throw new RuntimeException("Service error updating stock", e);
        }
    }

    /**
     * Search menu items in a restaurant.
     */
    public List<MenuItem> searchMenuItems(String restaurantId, String query) {
        try {
            List<MenuItem> items = itemDAO.findByRestaurantId(restaurantId);
            String lowerQuery = query.toLowerCase();
            return items.stream()
                    .filter(i -> i.getName().toLowerCase().contains(lowerQuery) || i.getDescription().toLowerCase().contains(lowerQuery))
                    .collect(Collectors.toList());
        } catch (DatabaseException e) {
            throw new RuntimeException("Service error searching menu items", e);
        }
    }
}
