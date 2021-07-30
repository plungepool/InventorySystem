package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class represents available inventory.*/
public class Inventory {
    /** List of all available parts.*/
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();

    /** Lisr of all available products.*/
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Adds new part type to inventory.
     @param newPart New part to be added.*/
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /** Adds new product type to inventory.
     @param newProduct New product to be added.*/
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /** Searches for part based on its ID.
     @param partId The ID of part to be looked up.
     @return The matching part, if any.*/
    public static Part lookupPart(int partId) {
        Part foundPart = null;
        for (Part p : allParts) {
            if (p.getId() == partId) {
                foundPart = p;
            }
        }
        return foundPart;
    }

    /** Searches for product based on its ID.
     @param productId The ID of product to be looked up.
     @return The matching product, if any.*/
    public static Product lookupProduct(int productId) {
        Product foundProduct = null;
        for (Product p : allProducts) {
            if (p.getId() == productId) {
                foundProduct = p;
            }
        }
        return foundProduct;
    }

    /** Method for looking up part by name.*/
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();
        for(Part p : allParts) {
            if(p.getName().contains(partName)){
                namedParts.add(p);
            }
        }
        return namedParts;
    }

    /** Method for looking up product by name.*/
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> namedProds = FXCollections.observableArrayList();
        ObservableList<Product> allProds = Inventory.getAllProducts();
        for(Product p : allProds) {
            if(p.getName().contains(productName)){
                namedProds.add(p);
            }
        }
        return namedProds;
    }

    /** Method for updating part in inventory.*/
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /** Method for updating up product in inventory.*/
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /** Deletes part from inventory.
     @param selectedPart Part to be deleted.*/
    public static boolean deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
        return true;
    }

    /** Deletes product from inventory.
     @param selectedProduct Product to be deleted.*/
    public static boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
        return true;
    }

    /** Returns list of all parts.*/
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** Returns lists of all products.*/
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
