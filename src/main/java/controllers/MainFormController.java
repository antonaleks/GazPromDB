package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFormController {
    @FXML
    private StackPane stackPane;
    @FXML
    private MenuBar menuBar;

    public MainFormController() {
        stackPane=new StackPane();

    }

    @FXML
    public void showEntriesListForm(ActionEvent actionEvent) throws IOException {
        showNewForm("/fxml/entriesListForm.fxml");
    }

    public void exitFromDataBase(ActionEvent actionEvent) throws IOException {
        showNewForm("fxml/schemaViewForm.fxml");
    }

    public void showCreateEntryForm(ActionEvent actionEvent) throws IOException {
        showNewForm("/fxml/createNewEntryForm.fxml");
    }

    //Helper method, which helps do not duplicate code
    private void showNewForm(String fxmlForm) throws IOException {
        javafx.stage.Window parentWindow = stackPane.getScene().getWindow();
        Stage stage = new Stage();
        Parent newRoot = FXMLLoader.load(getClass().getResource(fxmlForm));
        stage.setResizable(true);
        stage.setScene(new Scene(newRoot));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentWindow);
        stage.show();
    }
}
