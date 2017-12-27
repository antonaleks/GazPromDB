package controllers;

import com.jfoenix.controls.JFXButton;
import entity.Model;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.*;
import java.net.MalformedURLException;
import java.sql.SQLException;

/**
 * Created by Влад on 31.10.2017.
 */
public class ModelInfoController extends BaseController {
    @FXML
    public JFXButton btnParams;
    @FXML
    public ListView filesList;
    @FXML
    private Label modelID;
    @FXML
    private Label modelName;
    @FXML
    public JFXButton btnExit;
    @FXML
    private Label lblCreated;
    @FXML
    private Label lblType;

    private Model model;

    @FXML
    public void initialize(Model model) throws MalformedURLException{
        this.model = model;
        modelID.setText(modelID.getText()+this.model.getId());
        lblCreated.setText("Создана пользователем: " + this.model.getCreator() + " дата: " + this.model.getDate());
        lblType.setText(lblType.getText()+this.model.getType());
        modelName.setText(modelName.getText()+this.model.getName());
        filesList.getItems().add("Основной файл:");
        filesList.getItems().add(this.model.getPathToMainFile());
        filesList.getItems().add("Доп. файлы:");
        filesList.getItems().add(this.model.getPathToXml());
        filesList.getItems().add(this.model.getPathToTxt());
        filesList.getItems().add(this.model.getPathToDxf());
    }


    public void closeForm(ActionEvent actionEvent) {
        javafx.stage.Window parentWindow = ((Node) actionEvent.getSource()).getScene().getWindow();
        parentWindow.hide();
    }

    public void showParams(ActionEvent actionEvent) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/fxml/objGroup.fxml"));
        Parent root = loader.load();
        ObjGroupController objGroupController = loader.getController();
        objGroupController.initialize(this.model);
        loadModalWindow(actionEvent, "Обзор xml файла", root);
    }

    public void showReport(ActionEvent actionEvent) throws IOException, SQLException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/fxml/reportForm.fxml"));
        Parent root = loader.load();
        ReportController reportController = loader.getController();
        reportController.initialize(this.model);
        loadModalWindow(actionEvent, "Данные из txt отчета", root);
    }

    public void showSchema(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/fxml/schemaViewForm.fxml"));
        Parent root = loader.load();
        SchemaViewController schemaViewController = loader.getController();
        schemaViewController.initialize(this.model.getPathToSvg());
        loadModalWindow(actionEvent, "Схема модели", root);
    }
}
