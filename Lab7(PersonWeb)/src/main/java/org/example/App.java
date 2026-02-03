package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.example.Person.Person;
import org.example.Person.PersonPublisher;
import org.example.Person.PersonSoapClient;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws IOException {
        // 1. Start SOAP Service
        PersonPublisher.publish();

        // 2. Start Web Interface Server (Port 8000)
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        
        // Static UI Route
        server.createContext("/", new UIHandler());
        
        // (UI calls these, which then use PersonSoapClient)
        server.createContext("/persons", new ApiHandler());

        server.setExecutor(null);
        server.start();
        System.out.println("Web UI Admin Dashboard running at http://localhost:8000");
    }

    static class UIHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = getUIHtml();
            exchange.getResponseHeaders().set("Content-Type", "text/html");
            byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }

    static class ApiHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            String response = "";
            int statusCode = 200;

            try {
                if ("GET".equalsIgnoreCase(method)) {
                    String path = exchange.getRequestURI().getPath();
                    // Check if path is /api/persons/{id}
                    if (path.startsWith("/persons/") && path.length() > "/persons/".length()) {
                        try {
                            int id = Integer.parseInt(path.substring("/persons/".length()));
                            Person person = PersonSoapClient.getAllPersons().stream()
                                    .filter(p -> p.getId() == id)
                                    .findFirst()
                                    .orElse(null);
                            
                            if (person != null) {
                                response = toJson(person);
                            } else {
                                statusCode = 404;
                                response = "{\"error\":\"Person not found\"}";
                            }
                        } catch (NumberFormatException e) {
                            statusCode = 400;
                            response = "{\"error\":\"Invalid ID format\"}";
                        }
                    } else {
                        // Default: return all persons
                        List<Person> list = PersonSoapClient.getAllPersons();
                        response = toJson(list);
                    }
                } else if ("POST".equalsIgnoreCase(method)) {
                    // Create
                    String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                    // Extremely simple parsing for demo
                    String name = getJsonVal(body, "name");
                    String email = getJsonVal(body, "email");
                    String role = getJsonVal(body, "role");
                    PersonSoapClient.createPerson(name, email, role);
                    response = "{\"status\":\"success\"}";
                } else if ("PUT".equalsIgnoreCase(method)) {
                    // Update
                    String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                    int id = Integer.parseInt(getJsonVal(body, "id"));
                    String name = getJsonVal(body, "name");
                    String email = getJsonVal(body, "email");
                    String role = getJsonVal(body, "role");
                    PersonSoapClient.updatePerson(id, name, email, role);
                    response = "{\"status\":\"success\"}";
                } else if ("DELETE".equalsIgnoreCase(method)) {
                    // Delete
                    String query = exchange.getRequestURI().getQuery();
                    int id = Integer.parseInt(query.split("=")[1]);
                    PersonSoapClient.deletePerson(id);
                    response = "{\"status\":\"success\"}";
                }
            } catch (Exception e) {
                e.printStackTrace();
                statusCode = 500;
                response = "{\"error\":\"" + e.getMessage() + "\"}";
            }

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(statusCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }

    private static String toJson(List<Person> persons) {
        return "[" + persons.stream()
                .map(App::toJson)
                .collect(Collectors.joining(",")) + "]";
    }

    private static String toJson(Person p) {
        return String.format("{\"id\":%d, \"name\":\"%s\", \"email\":\"%s\", \"role\":\"%s\"}", 
                p.getId(), p.getName(), p.getEmail(), p.getRole());
    }

    private static String getJsonVal(String json, String key) {
        // Super simple regex based extractor
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("\"" + key + "\":\"?(.*?)\"?[,}]");
        java.util.regex.Matcher m = p.matcher(json);
        return m.find() ? m.group(1).trim() : "";
    }


private static String getUIHtml() {
    return "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "    <title>University Person Management</title>\n" +
            "    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\n" +
            "    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\n" +
            "    <link href=\"https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600&display=swap\" rel=\"stylesheet\">\n" +
            "    <style>\n" +
            "        :root {\n" +
            "            --primary: #E60023; /* Pinterest Red */\n" +
            "            --primary-hover: #ad081b;\n" +
            "            --bg: #f5f5f5;\n" +
            "            --card-bg: #ffffff;\n" +
            "            --text: #111111;\n" +
            "            --text-muted: #767676;\n" +
            "            --border: #e2e2e2;\n" +
            "            --input-focus: #efefef;\n" +
            "        }\n" +
            "        * { box-sizing: border-box; margin: 0; padding: 0; }\n" +
            "        body {\n" +
            "            font-family: 'Inter', -apple-system, BlinkMacSystemFont, sans-serif;\n" +
            "            background-color: var(--bg);\n" +
            "            color: var(--text);\n" +
            "            padding: 40px 20px;\n" +
            "            display: flex;\n" +
            "            flex-direction: column;\n" +
            "            align-items: center;\n" +
            "            min-height: 100vh;\n" +
            "            -webkit-font-smoothing: antialiased;\n" +
            "        }\n" +
            "        .container {\n" +
            "            width: 100%;\n" +
            "            max-width: 900px;\n" +
            "        }\n" +
            "        h1 {\n" +
            "            font-size: 1.75rem;\n" +
            "            margin-bottom: 32px;\n" +
            "            font-weight: 600;\n" +
            "            text-align: center;\n" +
            "            letter-spacing: -0.5px;\n" +
            "        }\n" +
            "        .card {\n" +
            "            background: var(--card-bg);\n" +
            "            border-radius: 16px;\n" +
            "            padding: 24px;\n" +
            "            box-shadow: 0 1px 20px 0 rgba(0,0,0,0.05);\n" +
            "            margin-bottom: 24px;\n" +
            "        }\n" +
            "        .form-grid {\n" +
            "            display: grid;\n" +
            "            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));\n" +
            "            gap: 16px;\n" +
            "            margin-bottom: 24px;\n" +
            "        }\n" +
            "        .input-group {\n" +
            "            display: flex;\n" +
            "            flex-direction: column;\n" +
            "            gap: 6px;\n" +
            "        }\n" +
            "        label {\n" +
            "            font-size: 0.85rem;\n" +
            "            color: var(--text);\n" +
            "            font-weight: 500;\n" +
            "            padding-left: 4px;\n" +
            "        }\n" +
            "        input, select {\n" +
            "            border: 2px solid var(--border);\n" +
            "            border-radius: 12px;\n" +
            "            padding: 12px 14px;\n" +
            "            font-size: 1rem;\n" +
            "            transition: all 0.2s ease;\n" +
            "            background-color: white;\n" +
            "        }\n" +
            "        input:focus, select:focus {\n" +
            "            border-color: #a5a5a5;\n" +
            "            outline: none;\n" +
            "            background-color: var(--input-focus);\n" +
            "        }\n" +
            "        .btn {\n" +
            "            background: var(--primary);\n" +
            "            color: white;\n" +
            "            border: none;\n" +
            "            padding: 12px 24px;\n" +
            "            border-radius: 24px;\n" +
            "            font-weight: 600;\n" +
            "            cursor: pointer;\n" +
            "            transition: transform 0.1s active, background 0.2s;\n" +
            "            font-size: 0.95rem;\n" +
            "        }\n" +
            "        .btn:hover { background: var(--primary-hover); }\n" +
            "        .btn:active { transform: scale(0.97); }\n" +
            "        \n" +
            "        .btn-secondary {\n" +
            "            background: #efefef;\n" +
            "            color: var(--text);\n" +
            "        }\n" +
            "        .btn-secondary:hover { background: #e2e2e2; }\n" +
            "\n" +
            "        table {\n" +
            "            width: 100%;\n" +
            "            border-collapse: collapse;\n" +
            "        }\n" +
            "        th {\n" +
            "            text-align: left;\n" +
            "            padding: 12px 16px;\n" +
            "            color: var(--text-muted);\n" +
            "            font-size: 0.85rem;\n" +
            "            text-transform: uppercase;\n" +
            "            letter-spacing: 0.5px;\n" +
            "            border-bottom: 1px solid var(--border);\n" +
            "        }\n" +
            "        td {\n" +
            "            padding: 16px;\n" +
            "            border-bottom: 1px solid var(--border);\n" +
            "            font-size: 0.95rem;\n" +
            "        }\n" +
            "        .badge {\n" +
            "            padding: 6px 12px;\n" +
            "            border-radius: 16px;\n" +
            "            font-size: 0.8rem;\n" +
            "            font-weight: 500;\n" +
            "            background: #f0f0f0;\n" +
            "            color: #333;\n" +
            "        }\n" +
            "        .actions {\n" +
            "            display: flex;\n" +
            "            gap: 16px;\n" +
            "        }\n" +
            "        .edit-btn { color: #0070e0; cursor: pointer; background:none; border:none; font-weight: 600; }\n" +
            "        .delete-btn { color: var(--primary); cursor: pointer; background:none; border:none; font-weight: 600; }\n" +
            "        .edit-btn:hover, .delete-btn:hover { text-decoration: underline; }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <div class=\"container\">\n" +
            "        <h1>University Management</h1>\n" +
            "        \n" +
            "        <div class=\"card\">\n" +
            "            <div class=\"form-grid\">\n" +
            "                <input type=\"hidden\" id=\"personId\">\n" +
            "                <div class=\"input-group\">\n" +
            "                    <label>Full Name</label>\n" +
            "                    <input type=\"text\" id=\"name\" placeholder=\"Enter name\">\n" +
            "                </div>\n" +
            "                <div class=\"input-group\">\n" +
            "                    <label>Email Address</label>\n" +
            "                    <input type=\"email\" id=\"email\" placeholder=\"email@domain.com\">\n" +
            "                </div>\n" +
            "                <div class=\"input-group\">\n" +
            "                    <label>Role</label>\n" +
            "                    <select id=\"role\">\n" +
            "                        <option value=\"Student\">Student</option>\n" +
            "                        <option value=\"Professor\">Professor</option>\n" +
            "                        <option value=\"Administrator\">Administrator</option>\n" +
            "                        <option value=\"Staff\">Staff</option>\n" +
            "                    </select>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "            <div style=\"display:flex; gap:12px\">\n" +
            "                <button class=\"btn\" onclick=\"savePerson()\" id=\"saveBtn\">Add Person</button>\n" +
            "                <button class=\"btn btn-secondary\" onclick=\"resetForm()\">Clear</button>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "\n" +
            "        <div class=\"card\" style=\"padding: 8px 0;\">\n" +
            "            <table>\n" +
            "                <thead>\n" +
            "                    <tr>\n" +
            "                        <th style=\"padding-left: 24px;\">ID</th>\n" +
            "                        <th>Name</th>\n" +
            "                        <th>Email</th>\n" +
            "                        <th>Role</th>\n" +
            "                        <th style=\"padding-right: 24px;\">Actions</th>\n" +
            "                    </tr>\n" +
            "                </thead>\n" +
            "                <tbody id=\"personList\">\n" +
            "                    \n" +
            "                </tbody>\n" +
            "            </table>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "\n" +
            "    <script>\n" +
            "        async function loadPersons() {\n" +
            "            const res = await fetch('/api/persons');\n" +
            "            const data = await res.json();\n" +
            "            const tbody = document.getElementById('personList');\n" +
            "            tbody.innerHTML = data.map(p => `\n" +
            "                <tr>\n" +
            "                    <td style=\"padding-left: 24px; color: var(--text-muted)\">#${p.id}</td>\n" +
            "                    <td style=\"font-weight:600\">${p.name}</td>\n" +
            "                    <td>${p.email}</td>\n" +
            "                    <td><span class=\"badge\">${p.role}</span></td>\n" +
            "                    <td style=\"padding-right: 24px;\">\n" +
            "                        <div class=\"actions\">\n" +
            "                            <button class=\"edit-btn\" onclick='editPerson(${JSON.stringify(p)})'>Edit</button>\n" +
            "                            <button class=\"delete-btn\" onclick=\"deletePerson(${p.id})\">Delete</button>\n" +
            "                        </div>\n" +
            "                    </td>\n" +
            "                </tr>\n" +
            "            `).join('');\n" +
            "        }\n" +
            "\n" +
            "        async function savePerson() {\n" +
            "            const id = document.getElementById('personId').value;\n" +
            "            const person = {\n" +
            "                name: document.getElementById('name').value,\n" +
            "                email: document.getElementById('email').value,\n" +
            "                role: document.getElementById('role').value\n" +
            "            };\n" +
            "            \n" +
            "            if (id) {\n" +
            "                person.id = id;\n" +
            "                await fetch('/api/persons', {\n" +
            "                    method: 'PUT',\n" +
            "                    body: JSON.stringify(person)\n" +
            "                });\n" +
            "            } else {\n" +
            "                await fetch('/api/persons', {\n" +
            "                    method: 'POST',\n" +
            "                    body: JSON.stringify(person)\n" +
            "                });\n" +
            "            }\n" +
            "            \n" +
            "            resetForm();\n" +
            "            loadPersons();\n" +
            "        }\n" +
            "\n" +
            "        async function deletePerson(id) {\n" +
            "            if (confirm('Are you sure you want to remove this record?')) {\n" +
            "                await fetch(`/api/persons?id=${id}`, { method: 'DELETE' });\n" +
            "                loadPersons();\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "        function editPerson(p) {\n" +
            "            document.getElementById('personId').value = p.id;\n" +
            "            document.getElementById('name').value = p.name;\n" +
            "            document.getElementById('email').value = p.email;\n" +
            "            document.getElementById('role').value = p.role;\n" +
            "            document.getElementById('saveBtn').innerText = 'Update Person';\n" +
            "        }\n" +
            "\n" +
            "        function resetForm() {\n" +
            "            document.getElementById('personId').value = '';\n" +
            "            document.getElementById('name').value = '';\n" +
            "            document.getElementById('email').value = '';\n" +
            "            document.getElementById('role').value = 'Student';\n" +
            "            document.getElementById('saveBtn').innerText = 'Add Person';\n" +
            "        }\n" +
            "\n" +
            "        loadPersons();\n" +
            "    </script>\n" +
            "</body>\n" +
            "</html>";
}
}
