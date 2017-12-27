package controllers;

import entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

public class MainFormController {
    @FXML
    private StackPane stackPane;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Label lableIUsername;
    @FXML
    private Label labeRights;

    @FXML
    private void initialize() {
        lableIUsername.setText(User.getCurrentUser().getLogin());
        labeRights.setText(User.getCurrentUser().getUsersRights());
    }

    public MainFormController() {
        stackPane=new StackPane();

    }

    @FXML
    public void showEntriesListForm(ActionEvent actionEvent) throws IOException {
        showNewForm("/fxml/entriesListForm.fxml");
    }

    public void exitFromDataBase(ActionEvent actionEvent) throws IOException {
        javafx.stage.Window parentWindow = ((Node) actionEvent.getSource()).getScene().getWindow();
        parentWindow.hide();
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
