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


    public void buttonEnter(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if(new DataBaseUserHelper().loginGlobalUser(loginField.getText(), passwordField.getText())) {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
            loadModalWindow(actionEvent, "Главное меню", root);
        }
        else {
            loginField.getStyleClass().add("wrong-credentials");
            passwordField.getStyleClass().add("wrong-credentials");
        }
//        Dog chappy = new Dog(1,"chappy");
//        MyKernel32.INSTANCE.CreatePipe(pipeInRead, pipeInWrite, null, 0);
//        byte[] data = SerializationUtils.serialize(chappy);
//        byte[] size = SerializationUtils.serialize(data.length);
//        IntByReference dwWritten = new IntByReference();
//        MyKernel32.INSTANCE.WriteFile(pipeInWrite.getValue(), size, size.length, dwWritten, null);
//        MyKernel32.INSTANCE.WriteFile(pipeInWrite.getValue(), data, data.length, dwWritten, null);
//        byte[] sizeR = new byte[81];
//        byte[] r = new byte[data.length];
//        IntByReference dwRead= new IntByReference();
//        MyKernel32.INSTANCE.ReadFile(pipeInRead.getValue(), sizeR, 81, dwRead, null);
//        Integer len = SerializationUtils.deserialize(sizeR);
//        MyKernel32.INSTANCE.ReadFile(pipeInRead.getValue(), r, len, dwRead, null);
//        Dog dog = SerializationUtils.deserialize(r);
//
//        System.out.println(dog.code);

    }
}


class Dog implements Serializable{
    int code;
    String name;

    public Dog(int code, String name) {
        this.code = code;
        this.name = name;
    }
}