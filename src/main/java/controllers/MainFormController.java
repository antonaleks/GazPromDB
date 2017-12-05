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
    public void showChangesListForm(ActionEvent actionEvent) throws IOException {
        showNewForm("/fxml/changesListForm.fxml");
    }



    public void showAddNewUserForm(ActionEvent actionEvent) throws IOException {
        showNewForm("/fxml/addNewUserForm.fxml");
    }



    public void showEntriesListForm(ActionEvent actionEvent) throws IOException {
        showNewForm("/fxml/entriesListForm.fxml");
    }

    public void showUsersListForm(ActionEvent actionEvent) throws IOException {
        showNewForm("/fxml/usersListForm.fxml");
    }

    public void showUserHistoryForm(ActionEvent actionEvent) throws IOException {
        showNewForm("/fxml/userHistoryForm.fxml");
    }

    public void showFindEntryForm(ActionEvent actionEvent) throws IOException {
       // showNewForm("/fxml/userHistoryForm.fxml");
    }

    public void showFindChahgesForm(ActionEvent actionEvent) throws IOException {
        //showNewForm("/fxml/userHistoryForm.fxml");
    }
    @FXML
    public void showHelpForm(ActionEvent actionEvent)  {
        try {
            showNewForm("/fxml/administratorMessageForm.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exitFromDataBase(ActionEvent actionEvent) {
    }

    @FXML
    public void showCreateEntryForm(ActionEvent actionEvent) throws IOException {
        showNewForm("/fxml/createNewEntryForm.fxml");
    }


    //Helper method, which helps do not duplicate code
    private void showNewForm(String fxmlForm) throws IOException {
        javafx.stage.Window parentWindow = mainForm.getScene().getWindow();//((Node) ((MenuItem) actionEvent.getSource()).getGraphic()).getScene().getWindow();
        Stage stage = new Stage();
        Parent newRoot = FXMLLoader.load(getClass().getResource(fxmlForm));
        stage.setResizable(true);
        stage.setScene(new Scene(newRoot));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentWindow);
        stage.show();
    }
}
