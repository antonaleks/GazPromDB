package controllers;

import db.DataBaseTxtHelper;
import db.DataBaseXmlHelper;
import entity.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class ReportController extends BaseController {

    private Model model;
    public Label lblType;
    public Label lblPathTxt;
    public Label lblId;

    public TableView<Component> compTable;
    public TableColumn<Component, String> compName;
    public TableColumn<Component, String> compFormula;
    public TableColumn<Component, String> compIndex;

    public TableView<EnergyBalance> energyTable;
    public TableColumn<EnergyBalance, String> energyName;
    public TableColumn<EnergyBalance, String> energyInput;
    public TableColumn<EnergyBalance, String> energyOutput;

    public TableView<MassBalance> massTable;
    public TableColumn<MassBalance, String> massName;
    public TableColumn<MassBalance, String> massInput_1;
    public TableColumn<MassBalance, String> massOutput_1;
    public TableColumn<MassBalance, String> massInput_2;
    public TableColumn<MassBalance, String> massOutput_2;

    @FXML
    public void initialize(Model model) throws SQLException {
        this.model = model;
        lblId.setText(lblId.getText()+this.model.getId());
        lblPathTxt.setText(lblPathTxt.getText()+this.model.getPathToTxt());
        lblType.setText(lblType.getText()+this.model.getType());

        ObservableList<Component> componentData = new DataBaseTxtHelper().selectComponentByModelToList(this.model.getId());
        ObservableList<EnergyBalance> energyData = new DataBaseTxtHelper().selectEnergyBalanceByModelToList(this.model.getId());
        ObservableList<MassBalance> massData = new DataBaseTxtHelper().selectMassBalanceByModelToList(this.model.getId());

        // устанавливаем тип и значение которое должно хранится в колонке
        compName.setCellValueFactory(new PropertyValueFactory<Component, String>("name"));
        compFormula.setCellValueFactory(new PropertyValueFactory<Component, String>("formula"));
        compIndex.setCellValueFactory(new PropertyValueFactory<Component, String>("id"));

        // заполняем таблицу данными
        compTable.setItems(componentData);

        // устанавливаем тип и значение которое должно хранится в колонке
        energyName.setCellValueFactory(new PropertyValueFactory<EnergyBalance, String>("name"));
        energyInput.setCellValueFactory(new PropertyValueFactory<EnergyBalance, String>("input"));
        energyOutput.setCellValueFactory(new PropertyValueFactory<EnergyBalance, String>("output"));

        // заполняем таблицу данными
        energyTable.setItems(energyData);

        // устанавливаем тип и значение которое должно хранится в колонке
        massName.setCellValueFactory(new PropertyValueFactory<MassBalance, String>("name"));
        massInput_1.setCellValueFactory(new PropertyValueFactory<MassBalance, String>("input_1"));
        massOutput_1.setCellValueFactory(new PropertyValueFactory<MassBalance, String>("output_1"));
        massInput_2.setCellValueFactory(new PropertyValueFactory<MassBalance, String>("input_2"));
        massOutput_2.setCellValueFactory(new PropertyValueFactory<MassBalance, String>("output_2"));

        // заполняем таблицу данными
        massTable.setItems(massData);
    }

}
