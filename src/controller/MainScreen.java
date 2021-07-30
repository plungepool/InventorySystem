package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller for the main program screen.
 FUTURE ENHANCEMENT - Generalize search functions and call from this class instead of duplicating into other controllers.
 FUTURE ENHANCEMENT - Add toMainScreen() function to this class to call from instead of duplicating in other controllers.*/
public class MainScreen implements Initializable {
    public TableView<Part> PartsTable;
    public TextField PartsSearch;
    public Button AddPartButton;
    public Button ModifyPartButton;
    public Button DeletePartButton;
    public TableView<Product> ProductsTable;
    public TextField ProductsSearch;
    public Button AddProductButton;
    public Button ModifyPartButton1;
    public Button DeletePartButton1;
    public Button ExitButton;

    public TableColumn<Object, Object> partIdColumn;
    public TableColumn<Object, Object> partNameColumn;
    public TableColumn<Object, Object> partInvColumn;
    public TableColumn<Object, Object> partPriceColumn;

    public TableColumn<Object, Object> prodIdColumn;
    public TableColumn<Object, Object> prodNameColumn;
    public TableColumn<Object, Object> prodInvColumn;
    public TableColumn<Object, Object> prodPriceColumn;

    /** Initializers for the main screen.
     Populates part and product tables with all items from inventory.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PartsTable.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        ProductsTable.setItems(Inventory.getAllProducts());
        prodIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** Transitions to the add part screen.*/
    public void toAddPartScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddPartScreen.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /** Transitions to the add product screen.*/
    public void toAddProductScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddProductScreen.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 400);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /** Transitions to the modify part screen.
     Passes needed information to screen controller from highlighted part.*/
    public void toModifyPartScreen(ActionEvent actionEvent) throws IOException {
        try {
            ModifyPartScreen.itemToModify = PartsTable.getSelectionModel().getSelectedItem();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ModifyPartScreen.fxml")));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 600, 400);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error: No part selected.");
            error.setContentText("Press ok to return.");
            error.showAndWait();
        }
    }

    /** Transitions to the modify product screen.
     Passes needed information to screen controller from highlighted part.*/
    public void toModifyProductScreen(ActionEvent actionEvent) throws IOException {
        try {
            ModifyProductScreen.itemToModify = ProductsTable.getSelectionModel().getSelectedItem();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ModifyProductScreen.fxml")));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 700, 400);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error: No product selected.");
            error.setContentText("Press ok to return.");
            error.showAndWait();
        }
    }

    /** Handler for parts search function.
     Attempts to search for parts by both ID and name.
     RUNTIME ERROR - "No matching parts found" pop-up error for if ID integer can not be parsed.*/
    public void getPartResultsHandler(ActionEvent actionEvent) {
        String q = PartsSearch.getText();
        ObservableList<Part> parts = searchByPartName(q);
        if(parts.size() == 0) {
            try {
                int id = Integer.parseInt(q);
                Part p = getPartWithId(id);
                if (p != null) {
                    parts.add(p);
                }
                else {
                    Alert error = new Alert(Alert.AlertType.INFORMATION);
                    error.setTitle("Part search");
                    error.setHeaderText("No matching parts found.");
                    error.setContentText("Press ok to continue.");
                    error.showAndWait();
                }
            }
            catch (NumberFormatException e) {
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setTitle("Part search");
                error.setHeaderText("No matching parts found.");
                error.setContentText("Press ok to continue.");
                error.showAndWait();
            }
        }
        PartsTable.setItems(parts);
    }

    /** Search function for part names.
     For use within the search results handler.
     @param partialName String or partial string to search for.*/
    private ObservableList<Part> searchByPartName(String partialName) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();
        for(Part p : allParts) {
            if(p.getName().contains(partialName)){
                namedParts.add(p);
            }
        }
        return namedParts;
    }

    /** Search function for part IDs.
     For use within the search results handler.
     @param id Part ID to attempt to find.*/
    private Part getPartWithId(int id){
        ObservableList<Part> allParts = Inventory.getAllParts();
        for(Part p : allParts) {
            if(p.getId() == id){
                return p;
            }
        }
        return null;
    }

    /** Handler for product search function.
     Attempts to search for products by both ID and name.
     RUNTIME ERROR - "No matching products found" pop-up error for if ID integer can not be parsed.*/
    public void getProductResultsHandler(ActionEvent actionEvent) {
        String q = ProductsSearch.getText();
        ObservableList<Product> products = searchByProductName(q);
        if(products.size() == 0) {
            try {
                int id = Integer.parseInt(q);
                Product p = getProductWithId(id);
                if (p != null) {
                    products.add(p);
                }
                else {
                    Alert error = new Alert(Alert.AlertType.INFORMATION);
                    error.setTitle("Product search");
                    error.setHeaderText("No matching products found.");
                    error.setContentText("Press ok to continue.");
                    error.showAndWait();
                }
            }
            catch (NumberFormatException e) {
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setTitle("Product search");
                error.setHeaderText("No matching products found.");
                error.setContentText("Press ok to continue.");
                error.showAndWait();
            }
        }
        ProductsTable.setItems(products);
    }

    /** Search function for product names.
     For use within the search results handler.
     @param partialName String or partial string to search for.*/
    private ObservableList<Product> searchByProductName(String partialName) {
        ObservableList<Product> namedProds = FXCollections.observableArrayList();
        ObservableList<Product> allProds = Inventory.getAllProducts();
        for(Product p : allProds) {
            if(p.getName().contains(partialName)){
                namedProds.add(p);
            }
        }
        return namedProds;
    }

    /** Search function for product IDs.
     For use within the search results handler.
     @param id Product ID to attempt to find.*/
    private Product getProductWithId(int id){
        ObservableList<Product> allProds = Inventory.getAllProducts();
        for(Product p : allProds) {
            if(p.getId() == id){
                return p;
            }
        }
        return null;
    }

    /** Delete part button handler.
     Attempts to delete selected part.*/
    public void onDeletePartButton(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Part");
        alert.setHeaderText("Are you sure you want to delete this part?");
        alert.setContentText("Press ok to confirm.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                Part itemToDelete = PartsTable.getSelectionModel().getSelectedItem();
                Inventory.deletePart(itemToDelete);
                PartsSearch.setText("");
                getPartResultsHandler(actionEvent);
            }
            catch (Exception e) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Error: Part could not be deleted.");
                error.setContentText("Press ok to continue.");
                error.showAndWait();
            }
        }
    }

    /** Delete product button handler.
     Attempts to delete selected product. Delete is prevented if product has associated parts.*/
    public void onDeleteProductButton(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Product");
        alert.setHeaderText("Are you sure you want to remove this product?");
        alert.setContentText("Press ok to confirm.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Product productToDelete = ProductsTable.getSelectionModel().getSelectedItem();
            if (productToDelete.getAllAssociatedParts().size() >= 1) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Error: Products with associated parts can not be deleted.");
                error.setContentText("Press ok to return.");
                error.showAndWait();
            }
            else {
                try {
                    Product itemToDelete = ProductsTable.getSelectionModel().getSelectedItem();
                    Inventory.deleteProduct(itemToDelete);
                    ProductsSearch.setText("");
                    getProductResultsHandler(actionEvent);
                }
                catch (Exception e) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Error");
                    error.setHeaderText("Error: Product could not be deleted.");
                    error.setContentText("Press ok to continue.");
                    error.showAndWait();
                }
            }
        }
    }

    /** Exit button handler.
     Exits program.*/
    public void onExitButton(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Press ok to exit the program.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}
