package controllers;

import db.DataBaseXmlHelper;
import entity.Attribute;
import entity.Data;
import entity.Model;
import entity.ObjectModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class DataController {
    @FXML
    private Label lblAttrName;
    @FXML
    private Label lblAttrUnit;
    @FXML
    private Label lblAttrDataType;
    @FXML
    private Label lblAttrId;
    @FXML
    private Label lblType;
    @FXML
    private Label lblPathXml;
    @FXML
    private Label lblObjId;
    @FXML
    private Label lblId;
    @FXML
    private Label lblObjName;
    @FXML
    private Label lblObjClass;
    @FXML
    private TableView<Data> tableData;
    @FXML
    private TableColumn<Data, String> col1;
    @FXML
    private TableColumn<Data, String> col2;

    @FXML
    public void initialize(Model model, ObjectModel object, Attribute attribute) throws SQLException {
        Model model1 = model;
        ObjectModel object1 = object;
        lblId.setText(lblId.getText() + model1.getId());
        lblPathXml.setText(lblPathXml.getText() + model1.getPathToXml());
        lblType.setText(lblType.getText() + model1.getType());
        lblObjId.setText(lblObjId.getText() + object1.getId().intValue());
        lblObjName.setText(lblObjName.getText() + object1.getName().getValue());
        lblObjClass.setText(lblObjClass.getText() + object1.getClassName().getValue());
        lblAttrName.setText(lblAttrName.getText()+attribute.getName());
        lblAttrUnit.setText(lblAttrUnit.getText()+attribute.getUnit());
        lblAttrDataType.setText(lblAttrDataType.getText()+attribute.getDataType());
        lblAttrId.setText(lblAttrId.getText()+attribute.getId());

        ObservableList<Data> tableListData = new DataBaseXmlHelper().selectDataByAttributeToList(attribute.getId());
        // устанавливаем тип и значение которое должно хранится в колонке
        col1.setCellValueFactory(new PropertyValueFactory<Data, String>("componentName"));
        col2.setCellValueFactory(new PropertyValueFactory<Data, String>("value"));

        // заполняем таблицу данными
        tableData.setItems(tableListData);

    }





}
