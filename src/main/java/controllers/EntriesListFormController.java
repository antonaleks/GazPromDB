package controllers;

import db.DataBaseXmlHelper;
import entity.Model;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Влад on 31.10.2017.
 */
public class EntriesListFormController extends BaseController {

    @FXML
    private TextField in_1;

    @FXML
    private TextField compName;

    @FXML
    private TextField in_2;

    @FXML
    private TextField estimate;

    @FXML
    private TableView<Model> tableModel;

    @FXML
    private TableColumn<Model, Integer> col1;

    @FXML
    private TableColumn<Model, String> col2;

    @FXML
    private TableColumn<Model, Date> col3;

    @FXML
    private TableColumn<Model, String> colName;

    @FXML
    private TableColumn<Model, String> colCreator;

    @FXML
    private TableColumn<Model, String> colSchema;

    @FXML
    private void initialize() throws SQLException {

        ObservableList<Model> modelData = new DataBaseXmlHelper().selectAllModelsToList();
        // устанавливаем тип и значение которое должно хранится в колонке
        col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("type"));
        col3.setCellValueFactory(new PropertyValueFactory<>("date"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCreator.setCellValueFactory(new PropertyValueFactory<>("creator"));

        // заполняем таблицу данными
        tableModel.setItems(modelData);
    }

    public void closeForm(ActionEvent actionEvent) {
        javafx.stage.Window parentWindow = ((Node) actionEvent.getSource()).getScene().getWindow();
        parentWindow.hide();
    }

    public void showInfo(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/fxml/modelInfo.fxml"));
        Parent root = loader.load();
        ModelInfoController modelInfoController = loader.getController();
        modelInfoController.initialize(tableModel.getSelectionModel().getSelectedItem());
        loadModalWindow(actionEvent, "Параметры модели", root);
    }

    public void get_info(ActionEvent actionEvent) throws SQLException {
        ObservableList<Model> modelData = new DataBaseXmlHelper().selectAllModelsByParamsToList(
                Double.parseDouble(in_1.getText()),
                Double.parseDouble(in_2.getText()),
                Double.parseDouble(estimate.getText()),
                compName.getText());
        // устанавливаем тип и значение которое должно хранится в колонке
        col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("type"));
        col3.setCellValueFactory(new PropertyValueFactory<>("date"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCreator.setCellValueFactory(new PropertyValueFactory<>("creator"));

        // заполняем таблицу данными
        tableModel.setItems(modelData);
    }
}
