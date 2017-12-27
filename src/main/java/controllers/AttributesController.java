package controllers;

import db.DataBaseXmlHelper;
import entity.Attribute;
import entity.Model;
import entity.ObjectModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.sql.SQLException;

public class AttributesController extends BaseController {

    @FXML
    private Button btnData;
    @FXML
    private Label lblType;
    @FXML
    private Label lblModelName;
    @FXML
    private Label lblObjId;
    @FXML
    private Label lblId;
    @FXML
    private Label lblObjName;
    @FXML
    private Label lblObjClass;
    @FXML
    private TableView<Attribute> tableAttr;
    @FXML
    private TableColumn<Attribute, String> col1;
    @FXML
    private TableColumn<Attribute, String> col2;
    @FXML
    private TableColumn<Attribute, String> col3;

    private Model model;
    private ObjectModel object;

    @FXML
    public void initialize(Model model, ObjectModel object) throws SQLException {
        this.model = model;
        this.object = object;
        lblId.setText(lblId.getText()+this.model.getId());
        lblModelName.setText(lblModelName.getText() + this.model.getName());
        lblType.setText(lblType.getText()+this.model.getType());
        lblObjId.setText(lblObjId.getText() + this.object.getId().intValue());
        lblObjName.setText(lblObjName.getText() + this.object.getName().getValue());
        lblObjClass.setText(lblObjClass.getText() + this.object.getClassName().getValue());

        ObservableList<Attribute> attributeData = new DataBaseXmlHelper().selectAttributeByObjectToList(object.getId().intValue());

        // устанавливаем тип и значение которое должно хранится в колонке
        col1.setCellValueFactory(new PropertyValueFactory<>("name"));
        col2.setCellValueFactory(new PropertyValueFactory<>("unit"));
        col3.setCellValueFactory(new PropertyValueFactory<>("dataType"));

        // заполняем таблицу данными
        tableAttr.setItems(attributeData);

    }

    public void showData(ActionEvent actionEvent) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/fxml/data.fxml"));
        Parent root = loader.load();
        DataController dataController = loader.getController();
        dataController.initialize(this.model, this.object, tableAttr.getSelectionModel().getSelectedItem());
        loadModalWindow(actionEvent, "Data модели", root);

    }



}
