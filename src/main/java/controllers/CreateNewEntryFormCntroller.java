package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.io.File;
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
}
