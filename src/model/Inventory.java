package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId) {
        Part foundPart = null;
        for (Part p : allParts) {
            if (p.getId() == partId) {
                foundPart = p;
            }
        }
        return foundPart;
    }

    public static Product lookupProduct(int productId) {
        Product foundProduct = null;
        for (Product p : allProducts) {
            if (p.getId() == productId) {
                foundProduct = p;
            }
        }
        return foundProduct;
    }

//    public static ObservableList<Part> lookupPart(String partName) {
//        ;
//    }

//    public static ObservableList<Product> lookupProduct(String productName) {
//        ;
//    }

//    public static void updatePart(int index, Part selectedPart) {
////        index is index in the associated part "array" not partID
//    }

//    public static void updateProduct(int index, Product newProduct) {
//        ;
//    }

    public static boolean deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
        return true;
    }

    public static boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
        return true;
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
