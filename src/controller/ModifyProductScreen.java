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
import java.util.ResourceBundle;

public class ModifyProductScreen implements Initializable {
    public TextField PartSearch;
    public TextField IdField;
    public TextField NameField;
    public TextField InvField;
    public TextField PriceField;
    public TextField MaxField;
    public TextField MinField;
    public TableView<Part> PartsTable;
    public Button AddButton;
    public TableView<Part> AssociatedPartTable;
    public Button RemoveAssociatedButton;
    public Button SaveButton;
    public Button CancelButton;
    public TextField PartsSearch;
    public ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();

    public TableColumn<Object, Object> partIdColumn;
    public TableColumn<Object, Object> partNameColumn;
    public TableColumn<Object, Object> partInvColumn;
    public TableColumn<Object, Object> partPriceColumn;

    public TableColumn<Object, Object> associatedPartIdColumn;
    public TableColumn<Object, Object> associatedPartNameColumn;
    public TableColumn<Object, Object> associatedPartInvColumn;
    public TableColumn<Object, Object> associatedPartPriceColumn;

    public static Product itemToModify;

    /** Initializers for the modify product screen.
     Populates fields with values from selected product.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IdField.setText(String.valueOf(itemToModify.getId()));
        NameField.setText(itemToModify.getName());
        InvField.setText(String.valueOf(itemToModify.getStock()));
        PriceField.setText(String.valueOf(itemToModify.getPrice()));
        MinField.setText(String.valueOf(itemToModify.getMin()));
        MaxField.setText(String.valueOf(itemToModify.getMax()));

        PartsTable.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsList.addAll(itemToModify.getAllAssociatedParts());
        AssociatedPartTable.setItems(itemToModify.getAllAssociatedParts());
        associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** Transitions to the main screen.*/
    public void toMainScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 300);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
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

    /** Add associated part button handler.
     Adds selected part to associated parts list.*/
    public void onAddPartButton(ActionEvent actionEvent) {
        Part selectedPartToAdd = PartsTable.getSelectionModel().getSelectedItem();
        try {
            associatedPartsList.add(new Part(selectedPartToAdd.getId(),
                    selectedPartToAdd.getName(),
                    selectedPartToAdd.getPrice(),
                    selectedPartToAdd.getStock(),
                    selectedPartToAdd.getMin(),
                    selectedPartToAdd.getMax()));
            AssociatedPartTable.setItems(associatedPartsList);
        }
        catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error: No part selected.");
            error.setContentText("Press ok to return.");
            error.showAndWait();
        }
    }

    /** Remove associated part button handler.
     Removes selected part to associated parts list.*/
    public void onRemovePartButton(ActionEvent actionEvent) {
        Part selectedPartToRemove = AssociatedPartTable.getSelectionModel().getSelectedItem();

        if (selectedPartToRemove == null) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error: No part selected.");
            error.setContentText("Press ok to return.");
            error.showAndWait();
        }
        else {
            associatedPartsList.remove(selectedPartToRemove);
            AssociatedPartTable.setItems(associatedPartsList);
        }
    }

    /** Checks if inventory ranges are valid.
     @return Returns true if valid and false if invalid.*/
    private boolean checkInventoryRanges(int stock, int min, int max) {
        return (stock >= min) && (stock <= max);
    }

    /** Checks if valid integers are entered for stock, min, and max inventory fields.*/
    private void checkForInvalidIntFields() {
        try {
            int testInv = Integer.parseInt(InvField.getText());
            int testMin = Integer.parseInt(MinField.getText());
            int testMax = Integer.parseInt(MaxField.getText());
        }
        catch (NumberFormatException a) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error: Invalid input.");
            error.setContentText("Check values and try again.");
            error.showAndWait();
        }
    }

    /** Save product button handler.
     Creates and validates new product in inventory and returns to main screen.
     RUNTIME ERROR - Added checkForInvalidIntFields() to prevent an error when invalid integers are entered.*/
    public void onSaveProduct(ActionEvent actionEvent) {
        checkForInvalidIntFields();
        if (checkInventoryRanges(Integer.parseInt(InvField.getText()), Integer.parseInt(MinField.getText()), Integer.parseInt(MaxField.getText()))) {
            try {
                Product itemToModify = Inventory.lookupProduct(Integer.parseInt(IdField.getText()));
                itemToModify.setName(NameField.getText());
                itemToModify.setPrice(Double.parseDouble(PriceField.getText()));
                itemToModify.setStock(Integer.parseInt(InvField.getText()));
                itemToModify.setMin(Integer.parseInt(MinField.getText()));
                itemToModify.setMax(Integer.parseInt(MaxField.getText()));

                itemToModify.getAllAssociatedParts().clear();
                for (Part p : associatedPartsList) {
                    itemToModify.addAssociatedPart(p);
                }
                toMainScreen(actionEvent);
            }
            catch (Exception e) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Error: Invalid input.");
                error.setContentText("Check values and try again.");
                error.showAndWait();
            }
        }
        else {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error: Please enter valid supply ranges.");
            error.setContentText("Press ok to continue.");
            error.showAndWait();
        }
    }
}
