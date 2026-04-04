package com.foodapp.api.servlet;

import com.foodapp.config.AppConstants;
import com.foodapp.exception.AppException;
import com.foodapp.model.Restaurant;
import com.foodapp.service.MenuService;
import com.foodapp.service.RestaurantService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Servlet for restaurant operations.
 */
@WebServlet("/api/restaurants/*")
public class RestaurantServlet extends BaseServlet {
    private RestaurantService restaurantService;
    private MenuService menuService;

    @Override
    public void init() throws ServletException {
        restaurantService = (RestaurantService) getServletContext().getAttribute(AppConstants.ATTR_RESTAURANT_SERVICE);
        menuService = (MenuService) getServletContext().getAttribute(AppConstants.ATTR_MENU_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = getPathParam(req, 0);
        String subResource = getPathParam(req, 1);

        if (id == null) {
            handleList(req, resp);
        } else if (subResource == null) {
            handleGet(id, resp);
        } else if ("menu".equals(subResource)) {
            handleGetMenu(id, resp);
        } else {
            writeError(resp, 404, "Unknown sub-resource");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("/register".equals(req.getPathInfo())) {
            handleRegister(req, resp);
        } else {
            writeError(resp, 404, "Unknown restaurant endpoint");
        }
    }

    private void handleList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String area = req.getParameter("area");
        List<Restaurant> list = restaurantService.getRestaurantsInArea(area);
        writeJson(resp, 200, list);
    }

    private void handleGet(String id, HttpServletResponse resp) throws IOException {
        Optional<Restaurant> restaurant = restaurantService.getRestaurantDetails(id);
        if (restaurant.isPresent()) {
            writeJson(resp, 200, restaurant.get());
        } else {
            writeError(resp, 404, "Restaurant not found");
        }
    }

    private void handleGetMenu(String id, HttpServletResponse resp) throws IOException {
        writeJson(resp, 200, menuService.getFullMenu(id));
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> b = jsonMapper.fromJson(readBody(req), Map.class);
        try {
            Restaurant r = restaurantService.register(
                (String) b.get("ownerId"),
                (String) b.get("name"),
                (String) b.get("address"),
                (String) b.get("area"),
                ((Number) b.getOrDefault("latitude", 0.0)).doubleValue(),
                ((Number) b.getOrDefault("longitude", 0.0)).doubleValue(),
                (String) b.get("phone"),
                (String) b.get("cuisineType"),
                (String) b.get("opensAt"),
                (String) b.get("closesAt"),
                (String) b.get("description")
            );
            writeJson(resp, 201, r);
        } catch (AppException e) {
            writeError(resp, e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            writeError(resp, 500, e.getMessage());
        }
    }
}
