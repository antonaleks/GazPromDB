package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Created by Влад on 29.10.2017.
 */
public class ChangesListFormController extends BaseController {
    public void closeForm(ActionEvent actionEvent) {
        javafx.stage.Window parentWindow = ((Node) actionEvent.getSource()).getScene().getWindow();
        parentWindow.hide();
    }

    public void showFindChangesForm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/findChangesForm.fxml"));
        loadModalWindow(actionEvent, "Поиск изменений", root);
    }
}
