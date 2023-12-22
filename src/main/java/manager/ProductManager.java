package manager;

import db.DBConnectionProvider;
import model.Category;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private final CategoryManager categoryManager = new CategoryManager();
    private final Connection connection = DBConnectionProvider.getInstance().getConnection();

    public void addProduct(Product product) throws SQLException {
        String order = "INSERT INTO product (name,description,price,quantity,category_id) VALUES (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(order, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getQuantity());
            preparedStatement.setInt(5, product.getCategory().getId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getInt(1));
            }
        }
    }

    public Product getProductById(int id) throws SQLException {
        String order = "SELECT * FROM product WHERE id = " + id;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(order);
            if (resultSet.next()) {
                int anInt = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int qty = resultSet.getInt("quantity");
                int categoryId = resultSet.getInt("category_id");
                Category categoryById = categoryManager.getCategoryById(categoryId);
                return new Product(anInt, name, description, price, qty, categoryById);
            }
        }
        return null;
    }

    public void editProductById(int id, String name, String description, double price, int qty, int category_id) throws SQLException {
        if (getProductById(id) != null) {
            String order = "UPDATE product SET name = ?, description = ?, price = ?, quantity = ?, category_id = ? WHERE id = " + id;
            try (PreparedStatement preparedStatement = connection.prepareStatement(order)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, description);
                preparedStatement.setDouble(3, price);
                preparedStatement.setInt(4, qty);
                preparedStatement.setInt(5, category_id);
                preparedStatement.executeUpdate();
            }
        } else {
            throw new SQLException();
        }
    }

    public void deleteProductById(int id) throws SQLException {
        if (getProductById(id) != null) {
            String order = "DELETE FROM product WHERE id = " + id;
            try (PreparedStatement preparedStatement = connection.prepareStatement(order)) {
                preparedStatement.executeUpdate();
            }
        } else {
            throw new SQLException();
        }
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String order = "SELECT * FROM product";
        try (PreparedStatement preparedStatement = connection.prepareStatement(order)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int qty = resultSet.getInt("quantity");
                int category_id = resultSet.getInt("category_id");
                Category category = categoryManager.getCategoryById(category_id);
                products.add(new Product(id, name, description, price, qty, category));
            }
            return products;
        }
    }

    public int getSumOfProducts() throws SQLException {
        String order = "SELECT COUNT(id) AS product_count FROM product";
        try (PreparedStatement preparedStatement = connection.prepareStatement(order)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("product_count");
        }
    }

    public double getMaxPriceOfProduct() throws SQLException {
        String order = "SELECT MAX(price) AS product_max_price FROM product";
        try (PreparedStatement preparedStatement = connection.prepareStatement(order)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getDouble("product_max_price");
        }
    }

    public double getMinPriceOfProduct() throws SQLException {
        String order = "SELECT MIN(price) AS product_min_price FROM product";
        try (PreparedStatement preparedStatement = connection.prepareStatement(order)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getDouble("product_min_price");
        }
    }

    public double getAveragePriceOfProduct() throws SQLException {
        String order = "SELECT AVG(price) AS product_average_price FROM product";
        try (PreparedStatement preparedStatement = connection.prepareStatement(order)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getDouble("product_average_price");
        }
    }
}
