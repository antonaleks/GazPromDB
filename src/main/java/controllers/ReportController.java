package controllers;

import db.DataBaseTxtHelper;
import entity.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class ReportController extends BaseController {
    @FXML
    private Label lblType;
    @FXML
    private Label lblPathTxt;
    @FXML
    private Label lblId;

    @FXML
    private TableView<Component> compTable;
    @FXML
    private TableColumn<Component, String> compName;
    @FXML
    private TableColumn<Component, String> compFormula;
    @FXML
    private TableColumn<Component, String> compIndex;

    @FXML
    private TableView<EnergyBalance> energyTable;
    @FXML
    private TableColumn<EnergyBalance, String> energyName;
    @FXML
    private TableColumn<EnergyBalance, String> energyInput;
    @FXML
    private TableColumn<EnergyBalance, String> energyOutput;

    @FXML
    private TableView<MassBalance> massTable;
    @FXML
    private TableColumn<MassBalance, String> massName;
    @FXML
    private TableColumn<MassBalance, String> massInput_1;
    @FXML
    private TableColumn<MassBalance, String> massOutput_1;
    @FXML
    private TableColumn<MassBalance, String> massInput_2;
    @FXML
    private TableColumn<MassBalance, String> massOutput_2;

    @FXML
    public void initialize(Model model) throws SQLException {
        Model model1 = model;
        lblId.setText(lblId.getText()+ model1.getId());
        lblPathTxt.setText(lblPathTxt.getText()+ model1.getPathToTxt());
        lblType.setText(lblType.getText()+ model1.getType());

        ObservableList<Component> componentData = new DataBaseTxtHelper().selectComponentByModelToList(model1.getId());
        ObservableList<EnergyBalance> energyData = new DataBaseTxtHelper().selectEnergyBalanceByModelToList(model1.getId());
        ObservableList<MassBalance> massData = new DataBaseTxtHelper().selectMassBalanceByModelToList(model1.getId());

        // устанавливаем тип и значение которое должно хранится в колонке
        compName.setCellValueFactory(new PropertyValueFactory<>("name"));
        compFormula.setCellValueFactory(new PropertyValueFactory<>("formula"));
        compIndex.setCellValueFactory(new PropertyValueFactory<>("id"));

        // заполняем таблицу данными
        compTable.setItems(componentData);

        // устанавливаем тип и значение которое должно хранится в колонке
        energyName.setCellValueFactory(new PropertyValueFactory<>("name"));
        energyInput.setCellValueFactory(new PropertyValueFactory<>("input"));
        energyOutput.setCellValueFactory(new PropertyValueFactory<>("output"));

        // заполняем таблицу данными
        energyTable.setItems(energyData);

        // устанавливаем тип и значение которое должно хранится в колонке
        massName.setCellValueFactory(new PropertyValueFactory<>("name"));
        massInput_1.setCellValueFactory(new PropertyValueFactory<>("input_1"));
        massOutput_1.setCellValueFactory(new PropertyValueFactory<>("output_1"));
        massInput_2.setCellValueFactory(new PropertyValueFactory<>("input_2"));
        massOutput_2.setCellValueFactory(new PropertyValueFactory<>("output_2"));

        // заполняем таблицу данными
        massTable.setItems(massData);
    }

}
