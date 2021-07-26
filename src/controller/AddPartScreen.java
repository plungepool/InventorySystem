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
import model.Inventory;
import model.OutSourced;
import model.Part;
import model.Product;

import javax.lang.model.type.NullType;
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
    public TextField MinValue;
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

    public int findNewPartID(int newID) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        for (Part p : allParts) {
            if (p.getId() == newID) {
                ++newID;
                findNewPartID(newID);
            }
        }
        return newID;
    }

    public void onSavePart(ActionEvent actionEvent) throws IOException {
        if (sourceGroup.getSelectedToggle() == InhouseRadio) {
            int newID = 1;
            newID = findNewPartID(newID);
            InHouse newPart = new InHouse(newID,
                    NameField.getText(),
                    Double.parseDouble(PricecostField.getText()),
                    Integer.parseInt(InvField.getText()),
                    Integer.parseInt(MinValue.getText()),
                    Integer.parseInt(MaxField.getText()),
                    Integer.parseInt(MachineCompanyField.getText()));
            Inventory.addPart(newPart);
        }
        else if (sourceGroup.getSelectedToggle() == OutsourcedRadio) {
            OutSourced newPart = new OutSourced(100,
                    NameField.getText(),
                    Integer.parseInt(PricecostField.getText()),
                    Integer.parseInt(InvField.getText()),
                    Integer.parseInt(MinValue.getText()),
                    Integer.parseInt(MaxField.getText()),
                    MachineCompanyField.getText());
            Inventory.addPart(newPart);
        }
        toMainScreen(actionEvent);
    }
}
