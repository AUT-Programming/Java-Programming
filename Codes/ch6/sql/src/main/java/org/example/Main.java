package org.example;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        // Database connection
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:sample.db");
             Statement stmt = conn.createStatement()) {

            // Create table
            stmt.execute("CREATE TABLE IF NOT EXISTS products (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name TEXT NOT NULL, " +
                    "price REAL, " +
                    "stock INTEGER)");

            // INSERT (Create)
            stmt.executeUpdate("INSERT INTO products(name, price, stock) VALUES " +
                    "('Laptop', 999.99, 10), " +
                    "('Phone', 699.99, 20)");
            System.out.println("Added sample products");

            // SELECT (Read)
            System.out.println("\nAll products:");
            ResultSet rs = stmt.executeQuery("SELECT * FROM products");
            while (rs.next()) {
                System.out.printf("%d: %s - $%.2f (Stock: %d)%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock"));
            }

            // UPDATE
            stmt.executeUpdate("UPDATE products SET price = 899.99 WHERE name = 'Laptop'");
            System.out.println("\nUpdated Laptop price");

            // Verify update
            rs = stmt.executeQuery("SELECT name, price FROM products WHERE name = 'Laptop'");
            if (rs.next()) {
                System.out.printf("New Laptop price: $%.2f%n", rs.getDouble("price"));
            }

            // DELETE
            stmt.executeUpdate("DELETE FROM products WHERE stock < 15");
            System.out.println("\nDeleted low stock items");

            // Final state
            System.out.println("\nRemaining products:");
            rs = stmt.executeQuery("SELECT * FROM products");
            while (rs.next()) {
                System.out.printf("%d: %s - $%.2f (Stock: %d)%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock"));
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
}