package org.example;

// Concrete implementation
public class DatabaseHandler implements OrderRepository {
    @Override
    public void save(Order order) {
        // Connect to DB and save
    }

    @Override
    public Order findById(String id) {
        // some logic
        return null;
    }
}
