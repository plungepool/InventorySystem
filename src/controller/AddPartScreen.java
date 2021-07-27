package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.OutSourced;
import model.Inventory;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddPartScreen implements Initializable {
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
    public ToggleGroup sourceGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void toMainScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 300);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    public void onInhouseSelect(ActionEvent actionEvent) {
        MachineCompanyLabel.setText("Machine ID");
    }

    public void onOutsourcedSelect(ActionEvent actionEvent) {
        MachineCompanyLabel.setText("Company Name");
    }

    private int findNewPartID(int newID) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        for (Part p : allParts) {
            if (p.getId() == newID) {
                ++newID;
                findNewPartID(newID);
            }
        }
        return newID;
    }

    private boolean checkInventoryRanges(int stock, int min, int max) {
        return (stock >= min) && (stock <= max);
    }

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

    public void onSavePart(ActionEvent actionEvent) throws IOException {
        int newID = 1;
        newID = findNewPartID(newID);
        checkForInvalidIntFields();
        if (checkInventoryRanges(Integer.parseInt(InvField.getText()), Integer.parseInt(MinField.getText()), Integer.parseInt(MaxField.getText()))) {
            try {
                if (sourceGroup.getSelectedToggle() == InhouseRadio) {
                    InHouse newPart = new InHouse(newID,
                            NameField.getText(),
                            Double.parseDouble(PricecostField.getText()),
                            Integer.parseInt(InvField.getText()),
                            Integer.parseInt(MinField.getText()),
                            Integer.parseInt(MaxField.getText()),
                            Integer.parseInt(MachineCompanyField.getText()));
                    Inventory.addPart(newPart);
                }
                else if (sourceGroup.getSelectedToggle() == OutsourcedRadio) {
                    OutSourced newPart = new OutSourced(newID,
                            NameField.getText(),
                            Integer.parseInt(PricecostField.getText()),
                            Integer.parseInt(InvField.getText()),
                            Integer.parseInt(MinField.getText()),
                            Integer.parseInt(MaxField.getText()),
                            MachineCompanyField.getText());
                    Inventory.addPart(newPart);
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
