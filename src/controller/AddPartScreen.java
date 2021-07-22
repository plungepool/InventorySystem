package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

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

}
