package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

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

    public void toAddPartScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddPartScreen.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    public void toAddProductScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddProductScreen.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 400);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    public void toModifyPartScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ModifyPartScreen.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Modify Part");
        stage.setScene(scene);
        stage.show();
    }

    public void toModifyProductScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ModifyProductScreen.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 400);
        stage.setTitle("Modify Product");
        stage.setScene(scene);
        stage.show();
    }

}
