package controllers.unused;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Created by Влад on 31.10.2017.
 */
public class UserHistoryFormController  {
    public void showFoundChangesForm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/unused/foundChangesForm.fxml"));
        //loadModalWindow(actionEvent, "Найденные изменения", root);
    }

    public void closeForm(ActionEvent actionEvent) {
        javafx.stage.Window parentWindow = ((Node) actionEvent.getSource()).getScene().getWindow();
        parentWindow.hide();
    }
}
