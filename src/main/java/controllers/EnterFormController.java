package controllers;

import db.DataBaseUserHelper;
import entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Влад on 29.10.2017.
 */
public class EnterFormController extends BaseController {
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;


    public void buttonEnter(ActionEvent actionEvent) throws IOException, SQLException {
        if(new DataBaseUserHelper().loginGlobalUser(loginField.getText(), passwordField.getText())) {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
            loadModalWindow(actionEvent, "Edit name", root);
        }
    }

}
