package edu.wpi.cs3733d18.teamp.ui.admin;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733d18.teamp.Main;
import edu.wpi.cs3733d18.teamp.ui.service.ServiceRequestScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class AdminMenuController {

    @FXML
    JFXButton employeeButton;

    @FXML
    JFXButton mapManagementButton;

    @FXML
    JFXButton serviceRequestScreenButton;

    @FXML
    JFXButton backButton;

    @FXML
    public void employeeButtonOp(ActionEvent e) {
        Stage stage;
        Parent root;
        FXMLLoader loader;

        stage = (Stage) employeeButton.getScene().getWindow();
        loader = new FXMLLoader(getClass().getResource("/FXML/admin/ManageEmployeeScreen.fxml"));
        try {
            root = loader.load();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }
        employeeButton.getScene().setRoot(root);
    }

    @FXML
    public void mapManagementButtonOp(ActionEvent e) {
        Stage stage;
        Parent root;
        FXMLLoader loader;
        MapBuilderController mapBuilderController;

        stage = (Stage) mapManagementButton.getScene().getWindow();
        loader = new FXMLLoader(getClass().getResource("/FXML/admin/MapBuilder.fxml"));
        try {
            root = loader.load();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }
        mapBuilderController = loader.getController();
        mapBuilderController.startUp();
        mapManagementButton.getScene().setRoot(root);
    }

    @FXML
    public void serviceRequestScreenButtonOp(ActionEvent e) {
        Stage stage;
        Parent root;
        FXMLLoader loader;
        ServiceRequestScreen serviceRequestScreen;

        stage = (Stage) serviceRequestScreenButton.getScene().getWindow();
        loader = new FXMLLoader(getClass().getResource("/FXML/service/ServiceRequestScreen.fxml"));
        try {
            root = loader.load();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }
        serviceRequestScreen = loader.getController();
        serviceRequestScreenButton.getScene().setRoot(root);
    }

    @FXML
    public void backButtonOp(ActionEvent e) {
        Stage stage;
        Parent root;
        FXMLLoader loader;

        stage = (Stage) backButton.getScene().getWindow();
        loader = new FXMLLoader(getClass().getResource("/FXML/home/HomeScreen.fxml"));
        try {
            root = loader.load();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }
        Main.logoutCurrentUser();
        backButton.getScene().setRoot(root);
    }

    @FXML
    public void settingsButtonOp(ActionEvent e) {

    }
}
