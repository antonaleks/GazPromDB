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

public class DataBaseTxtHelper extends DataBaseHelper {

    public ObservableList<Component> selectComponentByModelToList(int modelId) throws SQLException {
        ObservableList<Component> tableListData = FXCollections.observableArrayList();
        PreparedStatement statement = conn.prepareStatement(SqlQueryHelper.sqlSelectComponent);
        statement.setInt(1, modelId);
        ResultSet rs = statement.executeQuery();
        while (rs.next())
            tableListData.add(new Component(
                    rs.getString(PropertiesManager.getSqlTableProperties().getProperty("TXT_COMPONENTS_NAME")),
                    rs.getString(PropertiesManager.getSqlTableProperties().getProperty("TXT_COMPONENTSE_FORMULA")),
                    rs.getString(PropertiesManager.getSqlTableProperties().getProperty("TXT_COMPONENTS_COMPONENT_INDEX"))));
        statement.close();
        closeAll();
        return tableListData;
    }

    public ObservableList<EnergyBalance> selectEnergyBalanceByModelToList(int modelId) throws SQLException {
        ObservableList<EnergyBalance> tableListData = FXCollections.observableArrayList();
        PreparedStatement statement = conn.prepareStatement(SqlQueryHelper.sqlSelectEnergyBalance);
        statement.setInt(1, modelId);
        ResultSet rs = statement.executeQuery();
        while (rs.next())
            tableListData.add(new EnergyBalance(
                    rs.getString(PropertiesManager.getSqlTableProperties().getProperty("TXT_ENERGY_BALANCE_NAME")),
                    rs.getString(PropertiesManager.getSqlTableProperties().getProperty("TXT_ENERGY_BALANCE_INPUT")),
                    rs.getString(PropertiesManager.getSqlTableProperties().getProperty("TXT_ENERGY_BALANCE_OUTPUT"))));
        statement.close();
        closeAll();
        return tableListData;
    }

    public ObservableList<MassBalance> selectMassBalanceByModelToList(int modelId) throws SQLException {
        ObservableList<MassBalance> tableListData = FXCollections.observableArrayList();
        PreparedStatement statement = conn.prepareStatement(SqlQueryHelper.sqlSelectMassBalance);
        statement.setInt(1, modelId);
        ResultSet rs = statement.executeQuery();
        while (rs.next())
            tableListData.add(new MassBalance(
                    rs.getString(PropertiesManager.getSqlTableProperties().getProperty("TXT_MASS_BALANCE_NAME")),
                    rs.getString(PropertiesManager.getSqlTableProperties().getProperty("TXT_MASS_BALANCE_INPUT_1")),
                    rs.getString(PropertiesManager.getSqlTableProperties().getProperty("TXT_MASS_BALANCE_INPUT_2")),
                    rs.getString(PropertiesManager.getSqlTableProperties().getProperty("TXT_MASS_BALANCE_OUTPUT_1")),
                    rs.getString(PropertiesManager.getSqlTableProperties().getProperty("TXT_MASS_BALANCE_OUTPUT_2"))));
        statement.close();
        closeAll();
        return tableListData;
    }
}
