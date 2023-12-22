package util;

public interface Commands {
    String EXIT = "0";
    String ADD_CATEGORY = "1";
    String EDIT_CATEGORY = "2";
    String DELETE_CATEGORY = "3";
    String ADD_PRODUCT = "4";
    String EDIT_PRODUCT = "5";
    String DELETE_PRODUCT = "6";
    String PRINT_SUM_OF_PRODUCTS = "7";
    String PRINT_MAX_OF_PRICE_PRODUCT = "8";
    String PRINT_MIN_OF_PRICE_PRODUCT = "9";
    String PRINT_AVG_OF_PRICE_PRODUCT = "10";

    static void printCommands() {
        System.out.println(EXIT + ": EXIT");
        System.out.println(ADD_CATEGORY + ": ADD CATEGORY");
        System.out.println(EDIT_CATEGORY + ": EDIT CATEGORY");
        System.out.println(DELETE_CATEGORY + ": DELETE CATEGORY");
        System.out.println(ADD_PRODUCT + ": ADD PRODUCT");
        System.out.println(EDIT_PRODUCT + ": EDIT PRODUCT");
        System.out.println(DELETE_PRODUCT + ": DELETE PRODUCT");
        System.out.println(PRINT_SUM_OF_PRODUCTS + ": PRINT SUM OF PRODUCTS");
        System.out.println(PRINT_MAX_OF_PRICE_PRODUCT + ": PRINT MAX OF PRICE PRODUCT");
        System.out.println(PRINT_MIN_OF_PRICE_PRODUCT + ": PRINT MIN OF PRICE PRODUCT");
        System.out.println(PRINT_AVG_OF_PRICE_PRODUCT + ": PRINT AVERAGE OF PRICE PRODUCT");
    }
}

