package com.foodapp.api.soap;

import com.foodapp.api.soap.dto.AddonInfo;
import com.foodapp.api.soap.dto.CategoryWithItems;
import com.foodapp.api.soap.dto.MenuItemInfo;
import com.foodapp.api.soap.dto.MenuResponse;
import com.foodapp.dao.MenuItemAddonDAO;
import com.foodapp.exception.DatabaseException;
import com.foodapp.model.MenuCategory;
import com.foodapp.model.MenuItem;
import com.foodapp.model.MenuItemAddon;
import com.foodapp.service.MenuService;
import jakarta.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of Menu Query SOAP Service.
 */
@WebService(endpointInterface = "com.foodapp.api.soap.MenuQueryService", targetNamespace = "http://foodapp.com/ws", serviceName = "MenuQueryService")
public class MenuQueryServiceImpl implements MenuQueryService {
    
    private final MenuService menuService;
    private final MenuItemAddonDAO addonDAO;

    public MenuQueryServiceImpl(MenuService menuService, MenuItemAddonDAO addonDAO) {
        this.menuService = menuService;
        this.addonDAO = addonDAO;
    }

    @Override
    public MenuResponse getMenu(String restaurantId) {
        Map<MenuCategory, List<MenuItem>> fullMenu = menuService.getFullMenu(restaurantId);
        List<CategoryWithItems> categories = new ArrayList<>();
        
        for (Map.Entry<MenuCategory, List<MenuItem>> entry : fullMenu.entrySet()) {
            MenuCategory cat = entry.getKey();
            List<MenuItemInfo> items = entry.getValue().stream()
                    .map(this::mapToInfo)
                    .collect(Collectors.toList());
            categories.add(new CategoryWithItems(cat.getId(), cat.getName(), items));
        }
        
        return new MenuResponse(categories);
    }

    @Override
    public List<MenuItemInfo> searchMenuItems(String restaurantId, String query) {
        return menuService.searchMenuItems(restaurantId, query).stream()
                .map(this::mapToInfo)
                .collect(Collectors.toList());
    }

    @Override
    public List<AddonInfo> getItemAddons(String menuItemId) {
        try {
            List<MenuItemAddon> addons = addonDAO.findByMenuItemId(menuItemId);
            return addons.stream()
                    .map(a -> new AddonInfo(a.getId(), a.getName(), a.getExtraPrice(), a.isAvailable()))
                    .collect(Collectors.toList());
        } catch (DatabaseException e) {
            throw new RuntimeException("Service error getting addons", e);
        }
    }

    private MenuItemInfo mapToInfo(MenuItem i) {
        return new MenuItemInfo(i.getId(), i.getCategoryId(), i.getRestaurantId(), i.getName(), i.getDescription(), 
                                i.getBasePrice(), i.getImageUrl(), i.isAvailable(), i.getPreparationTimeMinutes());
    }
}
