package db;

import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import properties.PropertiesManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseXmlHelper extends DataBaseHelper {

    public List<ModelType> selectAllModelsTypeToList() throws SQLException {
        List<ModelType> modelData = new ArrayList<>();
        PreparedStatement statement = conn.prepareStatement(SqlQueryHelper.sqlSelectTypeModels);
        ResultSet rs = statement.executeQuery();
        while (rs.next())
            modelData.add(new ModelType(rs.getInt("id"), rs.getString(PropertiesManager.getSqlTableProperties().getProperty("MODEL_TYPE_TYPE_NAME"))));
        statement.close();
        closeAll();
        return modelData;
    }

    public ObservableList<Model> selectAllModelsToList() throws SQLException {
        ObservableList<Model> modelData = FXCollections.observableArrayList();
        PreparedStatement statement = conn.prepareStatement(SqlQueryHelper.sqlSelectModels);
        ResultSet rs = statement.executeQuery();
        while (rs.next())
            modelData.add(new Model(rs.getInt("id"), rs.getString("type"),
                    rs.getDate("date"), rs.getString("xml")));
        statement.close();
        closeAll();
        return modelData;
    }

    public ObservableList<Data> selectDataByAttributeToList(int attrId) throws SQLException {
        ObservableList<Data> tableListData = FXCollections.observableArrayList();
        PreparedStatement statement = conn.prepareStatement(SqlQueryHelper.sqlSelectData);
        statement.setInt(1, attrId);
        ResultSet rs = statement.executeQuery();
        while (rs.next())
            tableListData.add(new Data(rs.getInt("id"),
                    rs.getString(PropertiesManager.getSqlTableProperties().getProperty("XML_DATA_COMPONENT_NAME")),
                    rs.getString(PropertiesManager.getSqlTableProperties().getProperty("XML_DATA_VALUE"))));
        statement.close();
        closeAll();
        return tableListData;
    }

    public ObservableList<Attribute> selectAttributeByObjectToList(int objectId) throws SQLException {
        ObservableList<Attribute> attributesData = FXCollections.observableArrayList();
        PreparedStatement statement = conn.prepareStatement(SqlQueryHelper.sqlSelectAttributes);
        statement.setInt(1, objectId);
        ResultSet rs = statement.executeQuery();
        while (rs.next())
            attributesData.add(new Attribute(rs.getInt("id"),
                    rs.getString(PropertiesManager.getSqlTableProperties().getProperty("XML_ATTRIBUTE_NAME")),
                    rs.getString(PropertiesManager.getSqlTableProperties().getProperty("XML_ATTRIBUTE_UNIT")),
                    rs.getString(PropertiesManager.getSqlTableProperties().getProperty("XML_ATTRIBUTE_DATA_TYPE"))));
        statement.close();
        closeAll();
        return attributesData;
    }

    public List<TreeItem<ObjectModel>> selectAllObjectsToTree(int modelId) throws SQLException {
        List<TreeItem<ObjectModel>> listTree = new ArrayList<>();
        PreparedStatement statement = conn.prepareStatement(SqlQueryHelper.sqlSelectObjects);
        statement.setInt(1, modelId);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            TreeItem<ObjectModel> child = new TreeItem<>(new ObjectModel(rs.getString(PropertiesManager.getSqlTableProperties().getProperty("XML_OBJECTS_NAME")),
                    rs.getString(PropertiesManager.getSqlTableProperties().getProperty("XML_OBJECTS_CLASS")), rs.getInt("id")));
            PreparedStatement statementChild = conn.prepareStatement(SqlQueryHelper.sqlSelectObject);
            statementChild.setInt(1, child.getValue().getId().intValue());
            ResultSet rsChild = statementChild.executeQuery();
            List<TreeItem<ObjectModel>> listTreeChild = new ArrayList<>();
            while (rsChild.next()) {
                TreeItem<ObjectModel> childChild = new TreeItem<>(new ObjectModel("Object",
                        rsChild.getString(rs.getString(PropertiesManager.getSqlTableProperties().getProperty("XML_OBJECT_NAME"))),
                        rsChild.getString(PropertiesManager.getSqlTableProperties().getProperty("XML_OBJECT_CLASS")),
                        rsChild.getInt("id")));
                listTreeChild.add(childChild);
            }
            child.getChildren().setAll(listTreeChild);
            listTree.add(child);
            statementChild.close();
        }
        statement.close();
        closeAll();
        return listTree;
    }
}
