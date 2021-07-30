// TODO:
//         - Add Parts and Products to the Main Screen Table
//         - Add the Part Search and Product Search features to the Main Screen
//         - Work On The Add Part Screen Features
//         - Work On The Modify Part Screen Features
//         - Work On The Add Product Screen Features
//         - Work On The Modify Product Screen Features

package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.util.Objects;

/** This is the main class.*/
public class Main extends Application {

    /** Loads test data for application startup.*/
    private void addTestData() {
        Part MCU = new Part(1, "ARM Processor", 6.00, 40, 0, 450);
        Inventory.addPart(MCU);
        Part Mem = new Part(22, "256kb Flash Memory", 4.50, 82, 0, 800);
        Inventory.addPart(Mem);
        Part ROM = new Part(23, "EEPROM", 3.22, 67, 0, 675);
        Inventory.addPart(ROM);
        Part Pins = new Part(66, "GPIO Header Pins", 0.45, 1254, 0, 5000);
        Inventory.addPart(Pins);

        Product S = new Product(6, "STM 32 Discovery Board", 28.00, 22, 0, 150);
        Inventory.addProduct(S);
        Product A = new Product(23, "Arduino Uno", 12.00, 54, 0, 350);
        Inventory.addProduct(A);
    }

    /** Defines what happens on application startup.*/
    @Override
    public void start(Stage primaryStage) throws IOException {
        addTestData();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 700, 300));
        primaryStage.show();
    }

    /** Launches main.
     Javadocs path: InventorySystem/javadocs/ */
    public static void main(String[] args) {
        launch(args);
    }

}
