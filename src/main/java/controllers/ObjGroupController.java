package controllers;

import db.DataBaseXmlHelper;
import entity.Model;
import entity.ObjectModel;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;

public class ObjGroupController extends BaseController {

    public Label lblId;
    public Label lblType;
    public Label lblPathXml;
    private Model model;
    @FXML
    private TreeTableView<ObjectModel> treeTable;

    @FXML
    private TreeTableColumn<ObjectModel, String> col1;

    @FXML
    private TreeTableColumn<ObjectModel, String> col2;

    @FXML
    private TreeTableColumn<ObjectModel, String> col3;

    @FXML
    public void initialize(Model model) throws SQLException {
        this.model = model;
        lblId.setText(lblId.getText()+model.getId());
        lblPathXml.setText(lblPathXml.getText()+model.getPathToXml());
        lblType.setText(lblType.getText()+model.getType());

        TreeItem<ObjectModel> root = new TreeItem<>(new ObjectModel("Objects", "", ""));
        root.getChildren().setAll(new DataBaseXmlHelper().selectAllObjectsToTree(model.getId()));

        col1.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ObjectModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ObjectModel, String> param) {
                return param.getValue().getValue().getType();
            }
        });
        col2.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ObjectModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ObjectModel, String> param) {
                return param.getValue().getValue().getName();
            }
        });
        col3.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ObjectModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ObjectModel, String> param) {
                return param.getValue().getValue().getClassName();
            }
        });
        treeTable.setRoot(root);
    }

    public void showAttributes(ActionEvent actionEvent) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/fxml/attributes.fxml"));
        Parent root = loader.load();
        AttributesController attributesController = loader.getController();
        ObjectModel obj = treeTable.getTreeItem(treeTable.getSelectionModel().getFocusedIndex()).getValue();
        attributesController.initialize(this.model, obj);
        loadModalWindow(actionEvent, "Атрибуты выбранного объекта", root);
    }

}
