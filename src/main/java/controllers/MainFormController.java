package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFormController {
    @FXML
    private BorderPane mainForm;
    @FXML
    private MenuBar menuBar;

    public MainFormController() {
        mainForm=new BorderPane();

    }

    @FXML
    public void showEntriesListForm(ActionEvent actionEvent) throws IOException {
        //showNewForm("/fxml/entriesListForm.fxml");
        javafx.stage.Window parentWindow = mainForm.getScene().getWindow();
        Stage stage = new Stage();
        Parent newRoot = FXMLLoader.load(getClass().getResource("/fxml/schemaViewForm.fxml"));
        stage.setResizable(true);
        stage.setScene(new Scene(newRoot));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentWindow);
        stage.show();
    }

    public void exitFromDataBase(ActionEvent actionEvent) throws IOException {
        showNewForm("fxml/schemaViewForm.fxml");
    }

    public void showCreateEntryForm(ActionEvent actionEvent) throws IOException {
        showNewForm("/fxml/createNewEntryForm.fxml");
    }

    //Helper method, which helps do not duplicate code
    private void showNewForm(String fxmlForm) throws IOException {
        javafx.stage.Window parentWindow = mainForm.getScene().getWindow();
        Stage stage = new Stage();
        Parent newRoot = FXMLLoader.load(getClass().getResource(fxmlForm));
        stage.setResizable(true);
        stage.setScene(new Scene(newRoot));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentWindow);
        stage.show();
    }
}
