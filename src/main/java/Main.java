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
    private static final ProductManager PRODUCT_MANAGER = new ProductManager();
    private static final CategoryManager CATEGORY_MANAGER = new CategoryManager();

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
            CATEGORY_MANAGER.addCategory(new Category(newCategoryName));
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
            CATEGORY_MANAGER.editCategoryById(id, newName);
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
            CATEGORY_MANAGER.deleteCategoryById(id);
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
            if ((category = CATEGORY_MANAGER.getCategoryById(categoryId)) == null) {
                System.out.println("-- Error: category by this id doesnt exist --");
                return;
            }
        } catch (SQLException e) {
            System.out.println("-- Wrong id --");
            return;
        }
        try {
            PRODUCT_MANAGER.addProduct(new Product(name, description, price, qty, category));
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
            if (PRODUCT_MANAGER.getProductById(id) == null) {
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
            if (CATEGORY_MANAGER.getCategoryById(categoryId) == null) {
                System.out.println("-- Error: category by this id doest exist --");
                return;
            }
        } catch (SQLException e) {
            System.out.println("-- Wrong id --");
        }
        try {
            PRODUCT_MANAGER.editProductById(id, name, description, price, qty, categoryId);
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
            PRODUCT_MANAGER.deleteProductById(id);
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
            sumOfProducts = PRODUCT_MANAGER.getSumOfProducts();
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
            maxPrice = PRODUCT_MANAGER.getMaxPriceOfProduct();
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
            minPrice = PRODUCT_MANAGER.getMinPriceOfProduct();
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
            averagePrice = PRODUCT_MANAGER.getAveragePriceOfProduct();
        } catch (SQLException e) {
            System.out.println("-- Error: connection is failed --");
            return;
        }
        System.out.println(averagePrice);
    }

    private static void printAllProducts() {
        try {
            List<Product> allProducts = PRODUCT_MANAGER.getAllProducts();
            for (Product product : allProducts) {
                System.out.println(product);
            }
        } catch (SQLException e) {
            System.out.println("-- Error: connection is failed --");
        }
    }

    private static void printAllCategories() {
        try {
            List<Category> allCategories = CATEGORY_MANAGER.getAllCategories();
            for (Category allCategory : allCategories) {
                System.out.println(allCategory);
            }
        } catch (SQLException e) {
            System.out.println("-- Error: connection is failed --");
        }
    }
}
