package controllers;

import db.DataBaseInsertHelper;
import db.DataBaseXmlHelper;
import entity.Component;
import entity.EnergyBalance;
import entity.MassBalance;
import entity.ModelType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import txtParsers.Parser;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Влад on 31.10.2017.
 */
public class CreateNewEntryFormCntroller {
    @FXML
    private Button mainFileChooseButton;
    @FXML
    private Label mainFilePathLabel;
    @FXML
    private ListView listView;
    @FXML
    private Button extraFilesChooseButton;
    @FXML
    private CheckBox txtCheckBox;
    @FXML
    private CheckBox dxfCheckBox;
    @FXML
    private CheckBox xmlCheckBox;
    @FXML
    private Button createButton;
    @FXML
    private ComboBox comboBox;

    private String pathToMainFile = "";
    private String pathToTXT= "";
    private String pathToDXF = "";
    private String pathToXML = "";

    private List<ModelType> types;


    @FXML
    public void initialize() throws SQLException {
        types = new DataBaseXmlHelper().selectAllModelsTypeToList();
        for(ModelType type: types)
            comboBox.getItems().add(type.getType());
    }

    public void chooseMainFile(ActionEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile!= null){

            String str=selectedFile.getPath();
            listView.getItems().add("Основной файл:");
            listView.getItems().add(str);
            txtCheckBox.setVisible(true);
            xmlCheckBox.setVisible(true);
            dxfCheckBox.setVisible(true);
            extraFilesChooseButton.setVisible(true);
        }else{
            System.out.println("Неверный файл");
        }

    }

    public void chooseExtraFiles(ActionEvent actionEvent) {
        FileChooser fileChooser=new FileChooser();
        List<File> files = fileChooser.showOpenMultipleDialog(null);

        if(files!=null){
            listView.getItems().add("Доп. файлы:");
            for(int i=0;i<files.size();i++)
                listView.getItems().add(files.get(i).getPath());
        }
        else {
            System.out.println("Файлы выбраны неверно!");
        }

    }

    public void closeForm(ActionEvent actionEvent) {
        javafx.stage.Window parentWindow = ((Node) actionEvent.getSource()).getScene().getWindow();
        parentWindow.hide();
    }

    public void insertNewModel(ActionEvent actionEvent) throws IOException, SQLException {

        for(int i = 0; i < listView.getItems().size(); i++){
            String row = ((String) listView.getItems().get(i));
            if(row.contains(File.separator)){
                if ("cc6".equals(row.substring(row.lastIndexOf('.')+1)))pathToMainFile = row;
                if ("txt".equals(row.substring(row.lastIndexOf('.')+1)))pathToTXT = row;
                if ("dxf".equals(row.substring(row.lastIndexOf('.')+1))) pathToDXF = row;
                if ("xml".equals(row.substring(row.lastIndexOf('.')+1)))pathToXML = row;
            }
        }
        int typeId = -1;
        for (ModelType type: types){
            if(comboBox.getSelectionModel().getSelectedItem().equals(type.getType())){
                typeId = type.getId();
                break;
            }
        }
        Parser txtParser = new Parser();
        String txtInput = txtParser.parseTxtReport(pathToTXT);
        List<MassBalance> massBalance = txtParser.parseMassBalance(txtInput);
        List<EnergyBalance> energyBalance = txtParser.parseEnergyBalance(txtInput);
        List<Component> components = txtParser.parseComponent(txtInput);

        DataBaseInsertHelper dataBaseInsertHelper = new DataBaseInsertHelper();

        dataBaseInsertHelper.fillDataBase(typeId, pathToXML, pathToDXF, pathToMainFile, pathToTXT);

    }
}
