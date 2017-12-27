package controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DataBaseUserHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Влад on 29.10.2017.
 */
public class EnterFormController extends BaseController {
    @FXML
    private JFXTextField loginField;
    @FXML
    private JFXPasswordField passwordField;


    public void buttonEnter(ActionEvent actionEvent) throws IOException, SQLException {
        if(true) {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
            loadModalWindow(actionEvent, "Edit name", root);
        }
        else {
            loginField.getStyleClass().add("wrong-credentials");
            passwordField.getStyleClass().add("wrong-credentials");
        }
    }

}
