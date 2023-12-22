import manager.CategoryManager;
import manager.ProductManager;
import model.Category;
import model.Product;
import util.Commands;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static util.Commands.*;


public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        boolean process = true;
        while (process) {
            Commands.printCommands();
            String choice = SCANNER.nextLine();
            switch (choice) {
                case EXIT:
                    System.out.println("-- Process is finished --");
                    process = false;
                    break;
                case ADD_CATEGORY:
                    addCategory();
                    break;
                case EDIT_CATEGORY:
                    editCategory();
                    break;
                case DELETE_CATEGORY:
                    deleteCategory();
                    break;
                case ADD_PRODUCT:
                    addProduct();
                    break;
                case EDIT_PRODUCT:
                    editProduct();
                    break;
                case DELETE_PRODUCT:
                    deleteProduct();
                    break;
                case PRINT_SUM_OF_PRODUCTS:
                    printSumOfProducts();
                    break;
                case PRINT_MAX_OF_PRICE_PRODUCT:
                    printMaxOfPriceProduct();
                    break;
                case PRINT_MIN_OF_PRICE_PRODUCT:
                    printMinOfPriceProduct();
                    break;
                case PRINT_AVG_OF_PRICE_PRODUCT:
                    printAverageOfPriceProduct();
                    break;
                default:
                    System.out.println("-- Wrong command --");
                    break;
            }
        }
    }

    private static void addCategory() {
        System.out.println("Name...");
        String newCategoryName = SCANNER.nextLine();
        try {
            CategoryManager.addCategory(new Category(newCategoryName));
        } catch (SQLException e) {
            System.out.println("-- Error: category by this name already exists! --");
            return;
        }
        System.out.println("-- Completed --");
    }

    private static void editCategory() {
        printAllCategories();
        System.out.println("Id of the category, you want to edit...");
        String idStr = SCANNER.nextLine();
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            System.out.println("-- Wrong id --");
            return;
        }
        System.out.println("New name...");
        String newName = SCANNER.nextLine();
        try {
            CategoryManager.editCategoryById(id, newName);
        } catch (SQLException e) {
            System.out.println("-- Error: category by this id doesnt exist --");
            return;
        }
        System.out.println("-- Completed --");
    }

    private static void deleteCategory() {
        printAllCategories();
        System.out.println("Id of the category, you want to delete...");
        String idStr = SCANNER.nextLine();
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            System.out.println("-- Wrong id --");
            return;
        }
        try {
            CategoryManager.deleteCategoryById(id);
        } catch (SQLException e) {
            System.out.println("-- Error: category by this id doesnt exist --");
            return;
        }
        System.out.println("-- Completed --");
    }

    private static void addProduct() {
        System.out.println("Name...");
        String name = SCANNER.nextLine();
        System.out.println("Description...");
        String description = SCANNER.nextLine();
        System.out.println("Price...");
        String priceStr = SCANNER.nextLine();
        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            System.out.println("-- Wrong price --");
            return;
        }
        System.out.println("Quantity...");
        String qtyStr = SCANNER.nextLine();
        int qty;
        try {
            qty = Integer.parseInt(qtyStr);
            if (qty < 0) {
                System.out.println("-- Wrong quantity --");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("-- Wrong quantity --");
            return;
        }
        printAllCategories();
        System.out.println("Category id...");
        String idStr = SCANNER.nextLine();
        int categoryId;
        try {
            categoryId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            System.out.println("-- Wrong id --");
            return;
        }
        Category category;
        try {
            if ((category = CategoryManager.getCategoryById(categoryId)) == null) {
                System.out.println("-- Error: category by this id doesnt exist --");
                return;
            }
        } catch (SQLException e) {
            System.out.println("-- Wrong id --");
            return;
        }
        try {
            ProductManager.addProduct(new Product(name, description, price, qty, category));
        } catch (SQLException e) {
            System.out.println("-- Error: connection is failed --");
            return;
        }
        System.out.println("-- Completed --");
    }

    private static void editProduct() {
        printAllProducts();
        System.out.println("Id of the product, you want to edit...");
        String idStr = SCANNER.nextLine();
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            System.out.println("-- Wrong id --");
            return;
        }
        try {
            if (ProductManager.getProductById(id) == null) {
                System.out.println("-- Error: product by this id doest exist --");
                return;
            }
        } catch (SQLException e) {
            System.out.println("-- Error: connection is failed --");
        }
        System.out.println("New name...");
        String name = SCANNER.nextLine();
        System.out.println("New description...");
        String description = SCANNER.nextLine();
        System.out.println("New price...");
        String priceStr = SCANNER.nextLine();
        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            System.out.println("-- Wrong price --");
            return;
        }
        System.out.println("New quantity...");
        String qtyStr = SCANNER.nextLine();
        int qty;
        try {
            qty = Integer.parseInt(qtyStr);
            if (qty < 0) {
                System.out.println("-- Wrong quantity --");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("-- Wrong quantity --");
            return;
        }
        printAllCategories();
        System.out.println("New category id...");
        String categoryIdStr = SCANNER.nextLine();
        int categoryId;
        try {
            categoryId = Integer.parseInt(categoryIdStr);
        } catch (NumberFormatException e) {
            System.out.println("-- Wrong id --");
            return;
        }
        try {
            if (CategoryManager.getCategoryById(categoryId) == null) {
                System.out.println("-- Error: category by this id doest exist --");
                return;
            }
        } catch (SQLException e) {
            System.out.println("-- Wrong id --");
        }
        try {
            ProductManager.editProductById(id, name, description, price, qty, categoryId);
        } catch (SQLException e) {
            System.out.println("-- Error: connection is failed --");
        }
        System.out.println("-- Completed --");
    }

    private static void deleteProduct() {
        printAllProducts();
        System.out.println("Id of the product, you want to delete...");
        String idStr = SCANNER.nextLine();
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            System.out.println("-- Wrong id --");
            return;
        }
        try {
            ProductManager.deleteProductById(id);
        } catch (SQLException e) {
            System.out.println("-- Error: product by this id doest exist --");
            return;
        }
        System.out.println("-- Completed --");
    }

    private static void printSumOfProducts() {
        System.out.println("The sum of products is...");
        int sumOfProducts;
        try {
            sumOfProducts = ProductManager.getSumOfProducts();
        } catch (SQLException e) {
            System.out.println("-- Error: connection is failed --");
            return;
        }
        System.out.println(sumOfProducts);
    }

    private static void printMaxOfPriceProduct() {
        System.out.println("The max price of products is...");
        double maxPrice;
        try {
            maxPrice = ProductManager.getMaxPriceOfProduct();
        } catch (SQLException e) {
            System.out.println("-- Error: connection is failed --");
            return;
        }
        System.out.println(maxPrice);
    }

    private static void printMinOfPriceProduct() {
        System.out.println("The min price of products is...");
        double minPrice;
        try {
            minPrice = ProductManager.getMinPriceOfProduct();
        } catch (SQLException e) {
            System.out.println("-- Error: connection is failed --");
            return;
        }
        System.out.println(minPrice);
    }

    private static void printAverageOfPriceProduct() {
        System.out.println("The average price of products is...");
        double averagePrice;
        try {
            averagePrice = ProductManager.getAveragePriceOfProduct();
        } catch (SQLException e) {
            System.out.println("-- Error: connection is failed --");
            return;
        }
        System.out.println(averagePrice);
    }

    private static void printAllProducts() {
        try {
            List<Product> allProducts = ProductManager.getAllProducts();
            for (Product product : allProducts) {
                System.out.println(product);
            }
        } catch (SQLException e) {
            System.out.println("-- Error: connection is failed --");
        }
    }

    private static void printAllCategories() {
        try {
            List<Category> allCategories = CategoryManager.getAllCategories();
            for (Category allCategory : allCategories) {
                System.out.println(allCategory);
            }
        } catch (SQLException e) {
            System.out.println("-- Error: connection is failed --");
        }
    }
}
