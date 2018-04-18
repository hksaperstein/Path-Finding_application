package edu.wpi.cs3733d18.teamp.ui.home;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733d18.teamp.Database.DBSystem;
import edu.wpi.cs3733d18.teamp.Exceptions.LoginInvalidException;
import edu.wpi.cs3733d18.teamp.Main;
import edu.wpi.cs3733d18.teamp.ui.map.MapScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    String userType;
    private DBSystem db = DBSystem.getInstance();
    @FXML
    JFXButton adminButton;

    @FXML
    JFXButton serviceButton;

    @FXML
    JFXButton mapButton;

    @FXML
    JFXTextField usernameTxt;

    @FXML
    JFXPasswordField passwordTxt;

    @FXML
    JFXButton loginButton;

    @FXML
    Label loginErrorLabel;

    /**
     * This function respond to clicking the either the service request
     * or admin login buttons opening the Login.fxml
     * The login.fxml then has a different login button function and title
     * depending on which one was clicked
     * @param e button press
     * @throws IOException
     */
    @FXML
    public void loginButtonOp(ActionEvent e) {
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        try {
            Main.currentUser = db.checkEmployeeLogin(username, password);
        } catch(LoginInvalidException e1) {
            //        final Timeline timeline = new Timeline();
//        timeline.setCycleCount(Timeline.INDEFINITE);
//        timeline.setAutoReverse(true);
//        final KeyValue kv = new KeyValue(loginTxt.opacityProperty(), 0);
//        final KeyFrame kf = new KeyFrame(Duration.millis(600), kv);
//        timeline.getKeyFrames().add(kf);
//        timeline.play();
            usernameTxt.setPromptText("Username");
            passwordTxt.setPromptText("Password");
            usernameTxt.clear();
            passwordTxt.clear();
            loginErrorLabel.setText("Login failed, invalid username or password");
            loginErrorLabel.setVisible(true);
            return;
        }

        if (Main.currentUser.getIsAdmin()){
            System.out.println("Logging in Admin");
            FXMLLoader loader;
            Stage stage;
            Parent root;

            loader = new FXMLLoader(getClass().getResource("/FXML/admin/AdminMenuScreen.fxml"));
            try {
                root = loader.load();
            } catch (IOException ie) {
                ie.printStackTrace();
                return;
            }
            loginButton.getScene().setRoot(root);
        } else {
            System.out.println("Logging in Employee");
            FXMLLoader loader;
            Stage stage;
            Parent root;

            loader = new FXMLLoader(getClass().getResource("/FXML/service/ServiceRequestScreen.fxml"));
            try {
                root = loader.load();
            } catch (IOException ie) {
                ie.printStackTrace();
                return;
            }
            loginButton.getScene().setRoot(root);
        }
    }


    /**
     * This method will bring the user to the main map screen when pressed
     * @param e button press
     * @return returns true if successful
     */
    @FXML
    public Boolean mapButtonOp(ActionEvent e) {
        FXMLLoader loader;
        Stage stage;
        Parent root;
        MapScreenController mapScreenController;
        Group showNodes = new Group();

        stage = (Stage) mapButton.getScene().getWindow();
        loader = new FXMLLoader(getClass().getResource("/FXML/map/MapScreen.fxml"));
        try {
            root = loader.load();
        } catch (IOException ie) {
            ie.getMessage();
            return false;
        }
        mapScreenController = loader.getController();
        mapScreenController.onStartUp();

        stage.setScene(new Scene(root, 1920, 1080));
        stage.setFullScreen(true);
        stage.show();
        return true;
    }

    @FXML
    public void aboutButtonOp() {

    }
}