package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/** Controller for the add modify part screen.*/
public class ModifyPartScreen implements Initializable {
    public RadioButton InhouseRadio;
    public RadioButton OutsourcedRadio;
    public Label MachineCompanyLabel;
    public TextField NameField;
    public TextField InvField;
    public TextField PricecostField;
    public TextField MinField;
    public TextField MachineCompanyField;
    public TextField MaxField;
    public Button SaveButton;
    public Button CancelButton;
    public TextField IdField;
    public ToggleGroup sourceGroup;

    public static Part itemToModify;

    /** Initializers for the modify part screen.
     Populates fields with values from selected part.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IdField.setText(String.valueOf(itemToModify.getId()));
        NameField.setText(itemToModify.getName());
        InvField.setText(String.valueOf(itemToModify.getStock()));
        PricecostField.setText(String.valueOf(itemToModify.getPrice()));
        MinField.setText(String.valueOf(itemToModify.getMin()));
        MaxField.setText(String.valueOf(itemToModify.getMax()));
        if (itemToModify instanceof InHouse) {
            MachineCompanyField.setText(String.valueOf(((InHouse) itemToModify).getMachineId()));
            InhouseRadio.setSelected(true);
        }
        if (itemToModify instanceof OutSourced) {
            MachineCompanyField.setText(((OutSourced) itemToModify).getCompanyName());
            OutsourcedRadio.setSelected(true);
        }
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

    /** Changes text label to Machine ID when In-house radio button is selected.*/
    public void onInhouseSelect(ActionEvent actionEvent) {
        MachineCompanyLabel.setText("Machine ID");
    }

    /** Changes text label to Company Name when Outsourced radio button is selected.*/
    public void onOutsourcedSelect(ActionEvent actionEvent) {
        MachineCompanyLabel.setText("Company Name");
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

    /** Save part button handler.
     Updates and validates part in inventory and returns to main screen.
     RUNTIME ERROR - Added checkForInvalidIntFields() to prevent an error when invalid integers are entered.*/
    public void onSaveButton(ActionEvent actionEvent) throws IOException {
        checkForInvalidIntFields();
        if (checkInventoryRanges(Integer.parseInt(InvField.getText()), Integer.parseInt(MinField.getText()), Integer.parseInt(MaxField.getText()))) {
            try {
                Part itemToModify = Inventory.lookupPart(Integer.parseInt(IdField.getText()));
                itemToModify.setName(NameField.getText());
                itemToModify.setPrice(Double.parseDouble(PricecostField.getText()));
                itemToModify.setStock(Integer.parseInt(InvField.getText()));
                itemToModify.setMin(Integer.parseInt(MinField.getText()));
                itemToModify.setMax(Integer.parseInt(MaxField.getText()));

                if (sourceGroup.getSelectedToggle() == InhouseRadio) {
                    InHouse newPart = new InHouse(itemToModify.getId(), itemToModify.getName(), itemToModify.getPrice(),
                                                itemToModify.getStock(), itemToModify.getMin(), itemToModify.getMax(),
                                                Integer.parseInt(MachineCompanyField.getText()));
                    Inventory.addPart(newPart);
                    Inventory.deletePart(itemToModify);
                }
                else if (sourceGroup.getSelectedToggle() == OutsourcedRadio) {
                    OutSourced newPart = new OutSourced (itemToModify.getId(), itemToModify.getName(), itemToModify.getPrice(),
                                                    itemToModify.getStock(), itemToModify.getMin(), itemToModify.getMax(),
                                                    MachineCompanyField.getText());
                    Inventory.addPart(newPart);
                    Inventory.deletePart(itemToModify);
                }
                toMainScreen(actionEvent);
            }
            catch (NumberFormatException a) {
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

