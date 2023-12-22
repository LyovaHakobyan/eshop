package manager;

import db.DBConnectionProvider;
import model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryManager {
    public static void addCategory(Category category) throws SQLException {
        String order = "INSERT INTO category (name) VALUES (?)";
        Connection connection = DBConnectionProvider.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(order, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                category.setId(generatedKeys.getInt(1));
            }
        }
    }

    public static Category getCategoryById(int id) throws SQLException {
        String order = "SELECT * FROM category WHERE id = " + id;
        Connection connection = DBConnectionProvider.getInstance().getConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(order);
            if (resultSet.next()) {
                int anInt = resultSet.getInt("id");
                String string = resultSet.getString("name");
                return new Category(anInt, string);
            }
        }
        return null;
    }

    public static void editCategoryById(int id, String newName) throws SQLException {
        if (getCategoryById(id) != null) {
            String order = "UPDATE category SET name = ? WHERE id = " + id;
            Connection connection = DBConnectionProvider.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(order)) {
                preparedStatement.setString(1, newName);
                preparedStatement.executeUpdate();
            }
        } else {
            throw new SQLException();
        }
    }

    public static void deleteCategoryById(int id) throws SQLException {
        if (getCategoryById(id) != null) {
            String order = "DELETE FROM category WHERE id = " + id;
            Connection connection = DBConnectionProvider.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(order)) {
                preparedStatement.executeUpdate();
            }
        } else {
            throw new SQLException();
        }
    }

    public static List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String order = "SELECT * FROM category";
        Connection connection = DBConnectionProvider.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(order)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                categories.add(new Category(id, name));
            }
            return categories;
        }
    }
}
