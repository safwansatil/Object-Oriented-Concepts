package com.foodapp.api.servlet;

import com.foodapp.config.AppConstants;
import com.foodapp.exception.AppException;
import com.foodapp.model.MenuCategory;
import com.foodapp.model.MenuItem;
import com.foodapp.service.MenuService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Servlet for menu management.
 */
@WebServlet("/api/menu/*")
public class MenuServlet extends BaseServlet {
    private MenuService menuService;

    @Override
    public void init() throws ServletException {
        menuService = (MenuService) getServletContext().getAttribute(AppConstants.ATTR_MENU_SERVICE);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        if ("PATCH".equalsIgnoreCase(method)) {
            doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if ("/categories".equals(path)) {
            handleAddCategory(req, resp);
        } else if ("/items".equals(path)) {
            handleAddItem(req, resp);
        } else {
            writeError(resp, 404, "Endpoint not found");
        }
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = getPathParam(req, 1);
        String sub = getPathParam(req, 2);
        
        if (id != null && "availability".equals(sub)) {
            handleUpdateAvailability(id, req, resp);
        } else if (id != null && "stock".equals(sub)) {
            handleUpdateStock(id, req, resp);
        } else {
            writeError(resp, 404, "Unknown patch endpoint");
        }
    }

    private void handleAddCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> b = jsonMapper.fromJson(readBody(req), Map.class);
        MenuCategory c = menuService.addCategory((String) b.get("restaurantId"), (String) b.get("name"), (Integer) b.get("displayOrder"));
        writeJson(resp, 201, c);
    }

    private void handleAddItem(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> b = jsonMapper.fromJson(readBody(req), Map.class);
        MenuItem item = menuService.addItem(
            (String) b.get("restaurantId"),
            (String) b.get("categoryId"),
            (String) b.get("name"),
            (String) b.get("description"),
            ((Number) b.get("basePrice")).doubleValue(),
            (String) b.get("imageUrl"),
            (Integer) b.get("prepTime"),
            (Boolean) b.getOrDefault("trackQuantity", false),
            (Integer) b.get("initialStock")
        );
        writeJson(resp, 201, item);
    }

    private void handleUpdateAvailability(String id, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Boolean> b = jsonMapper.fromJson(readBody(req), Map.class);
        menuService.setItemAvailability(id, b.get("available"));
        writeJson(resp, 200, Map.of("success", true));
    }

    private void handleUpdateStock(String id, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Integer> b = jsonMapper.fromJson(readBody(req), Map.class);
        menuService.updateItemStock(id, b.get("quantity"));
        writeJson(resp, 200, Map.of("success", true));
    }
}
