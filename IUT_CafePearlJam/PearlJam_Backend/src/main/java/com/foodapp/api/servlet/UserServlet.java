package com.foodapp.api.servlet;

import com.foodapp.config.AppConstants;
import com.foodapp.exception.AppException;
import com.foodapp.model.User;
import com.foodapp.model.UserRole;
import com.foodapp.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * Servlet for user operations.
 */
@WebServlet("/api/users/*")
public class UserServlet extends BaseServlet {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(UserServlet.class.getName());
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute(AppConstants.ATTR_USER_SERVICE);
        if (userService == null) {
            LOGGER.severe("UserService not found in ServletContext! Initialization may have failed.");
        } else {
            LOGGER.info("UserServlet initialized successfully.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if ("/register".equals(path)) {
            handleRegister(req, resp);
        } else if ("/login".equals(path)) {
            handleLogin(req, resp);
        } else {
            writeError(resp, 404, "Unknown user endpoint");
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String bodyString = readBody(req);
        LOGGER.info("Registration request received: " + bodyString);
        Map<String, String> body = jsonMapper.fromJson(bodyString, Map.class);
        try {
            if (userService == null) {
                throw new AppException("UserService is unavailable", 500);
            }
            User user = userService.register(
                body.get("name"),
                body.get("email"),
                body.get("password"),
                body.get("phone"),
                UserRole.valueOf(body.get("role").toUpperCase())
            );
            writeJson(resp, 201, user);
        } catch (AppException e) {
            LOGGER.warning("AppException in register: " + e.getMessage());
            writeError(resp, e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.log(java.util.logging.Level.SEVERE, "Unexpected error in register", e);
            writeError(resp, 500, e.getMessage());
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String bodyString = readBody(req);
        LOGGER.info("Login request received: " + bodyString);
        Map<String, String> body = jsonMapper.fromJson(bodyString, Map.class);
        try {
            if (userService == null) {
                throw new AppException("UserService is unavailable", 500);
            }
            Optional<User> user = userService.login(body.get("email"), body.get("password"));
            if (user.isPresent()) {
                writeJson(resp, 200, user.get());
            } else {
                writeError(resp, 401, "Invalid credentials");
            }
        } catch (AppException e) {
            LOGGER.warning("AppException in login: " + e.getMessage());
            writeError(resp, e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.log(java.util.logging.Level.SEVERE, "Unexpected error in login", e);
            writeError(resp, 500, e.getMessage());
        }
    }
}
