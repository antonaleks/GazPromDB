package db;

import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseXmlHelper extends DataBaseHelper {
    private final static String sqlSelectModels = "Select model.pathToXml as xml, model.id as id, modtype.modelType " +
            "as type, model.date as date from model_files  model inner join modeltype  modtype on model.modeltype_id " +
            "= modtype.id";
    private final static String sqlSelectObjects = "Select obj.name, obj.class, obj.id from Objects obj inner join " +
            "model_files model on obj.model_files_id = model.id where model.id=?";
    private final static String sqlSelectObject = "Select obj.name, obj.class, obj.id from Object as obj inner join " +
            "objects objs on obj.objects_id=objs.id where obj.objects_id=?";
    private final static String sqlSelectAttributes = "SELECT attr.id, attr.name, attr.unit, attr.dataType " +
            "From attribute attr inner join object obj on attr.object_id = obj.id where attr.object_id = ?";
    private final static String sqlSelectData = "select dat.id, dat.component_name, dat.value from data dat inner join \n" +
            "attribute attr on dat.attribute_id=attr.id where attr.id = ?";
    private final static String sqlLastId = "select last_insert_id()";

    private final static String sqlSelectTypeModels = "Select * from modelType";

    public List<ModelType> selectAllModelsTypeToList() throws SQLException {
        List<ModelType> modelData = new ArrayList<>();
        PreparedStatement statement = conn.prepareStatement(sqlSelectTypeModels);
        ResultSet rs = statement.executeQuery();
        while (rs.next())
            modelData.add(new ModelType(rs.getInt("id"), rs.getString("modelType")));
        statement.close();
        closeAll();
        return modelData;
    }

    public ObservableList<Model> selectAllModelsToList() throws SQLException {
        ObservableList<Model> modelData = FXCollections.observableArrayList();
        PreparedStatement statement = conn.prepareStatement(sqlSelectModels);
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
        PreparedStatement statement = conn.prepareStatement(sqlSelectData);
        statement.setInt(1, attrId);
        ResultSet rs = statement.executeQuery();
        while (rs.next())
            tableListData.add(new Data(rs.getInt("id"), rs.getString("component_name"), rs.getString("value")));
        statement.close();
        closeAll();
        return tableListData;
    }

    public ObservableList<Attribute> selectAttributeByObjectToList(int objectId) throws SQLException {
        ObservableList<Attribute> attributesData = FXCollections.observableArrayList();
        PreparedStatement statement = conn.prepareStatement(sqlSelectAttributes);
        statement.setInt(1, objectId);
        ResultSet rs = statement.executeQuery();
        while (rs.next())
            attributesData.add(new Attribute(rs.getInt("id"), rs.getString("name"), rs.getString("unit"), rs.getString("dataType")));
        statement.close();
        closeAll();
        return attributesData;
    }

    public List<TreeItem<ObjectModel>> selectAllObjectsToTree(int modelId) throws SQLException {
        List<TreeItem<ObjectModel>> listTree = new ArrayList<>();
        PreparedStatement statement = conn.prepareStatement(sqlSelectObjects);
        statement.setInt(1, modelId);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            TreeItem<ObjectModel> child = new TreeItem<>(new ObjectModel(rs.getString("name"),
                    rs.getString("class"), rs.getInt("id")));
            PreparedStatement statementChild = conn.prepareStatement(sqlSelectObject);
            statementChild.setInt(1, child.getValue().getId().intValue());
            ResultSet rsChild = statementChild.executeQuery();
            List<TreeItem<ObjectModel>> listTreeChild = new ArrayList<>();
            while (rsChild.next()) {
                TreeItem<ObjectModel> childChild = new TreeItem<>(new ObjectModel("Object", rsChild.getString("name"), rsChild.getString("class"), rsChild.getInt("id")));
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
