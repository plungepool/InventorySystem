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

    private boolean checkInventoryValidity(int stock, int min, int max) {
        return (stock >= min) && (stock <= max);
    }

    public void onSaveButton(ActionEvent actionEvent) throws IOException {
        if (checkInventoryValidity(Integer.parseInt(InvField.getText()), Integer.parseInt(MinField.getText()), Integer.parseInt(MaxField.getText()))) {
            Part itemToModify = Inventory.lookupPart(Integer.parseInt(IdField.getText()));
            itemToModify.setName(NameField.getText());
            itemToModify.setPrice(Double.parseDouble(PricecostField.getText()));
            itemToModify.setStock(Integer.parseInt(InvField.getText()));
            itemToModify.setMin(Integer.parseInt(MinField.getText()));
            itemToModify.setMax(Integer.parseInt(MaxField.getText()));

            if (itemToModify instanceof InHouse) {
                ((InHouse) itemToModify).setMachineId(Integer.parseInt(MachineCompanyField.getText()));
            }
            if (itemToModify instanceof OutSourced) {
                ((OutSourced) itemToModify).setCompanyName(MachineCompanyField.getText());
            }
        }

        //in order to change outsourced/inhouse need to destroy old item and make new one seems like

        else {
            //inventory invalid
        }
        toMainScreen(actionEvent);
    }
}
