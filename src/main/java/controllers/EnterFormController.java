package controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import db.DataBaseUserHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.apache.commons.lang3.SerializationUtils;
import winApi.MyKernel32;
import winApi.threads.CheckFolder;

import java.io.*;
import java.sql.SQLException;

/**
 * Created by Влад on 29.10.2017.
 */
public class EnterFormController extends BaseController {
    @FXML
    private JFXTextField loginField;
    @FXML
    private JFXPasswordField passwordField;


    public void buttonEnter(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException, InterruptedException {
        if(new DataBaseUserHelper().loginGlobalUser(loginField.getText(), passwordField.getText())) {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
            loadModalWindow(actionEvent, "Главное меню", root);
        }
        else {
            loginField.getStyleClass().add("wrong-credentials");
            passwordField.getStyleClass().add("wrong-credentials");
        }

    }
}
