package com.foodapp.api.servlet;

import com.foodapp.util.JsonMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Base class for JSON-based HttpServlets.
 */
public abstract class BaseServlet extends HttpServlet {
    protected final JsonMapper jsonMapper = JsonMapper.getInstance();

    protected void writeJson(HttpServletResponse resp, int status, Object body) throws IOException {
        resp.setStatus(status);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(jsonMapper.toJson(body));
    }

    protected void writeError(HttpServletResponse resp, int status, String message) throws IOException {
        resp.setStatus(status);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("{\"error\": \"" + message + "\"}");
    }

    protected String readBody(HttpServletRequest req) throws IOException {
        try (BufferedReader reader = req.getReader()) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }

    protected String getPathParam(HttpServletRequest req, int index) {
        String pathInfo = req.getPathInfo(); // /id or /id/sub
        if (pathInfo == null || pathInfo.equals("/")) return null;
        
        String[] parts = pathInfo.split("/");
        if (parts.length > index + 1) {
            return parts[index + 1];
        }
        return null;
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }
}
