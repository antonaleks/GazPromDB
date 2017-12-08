package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;

import java.io.IOException;

/**
 * Created by Влад on 29.10.2017.
 */
public class EnterFormController extends BaseController {
    @FXML
    private javafx.scene.control.TextField loginField;
    @FXML
    private PasswordField passwordField;


    public void buttonEnter(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));


        loadModalWindow(actionEvent, "Edit name", root);
    }

}
