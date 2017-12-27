package controllers.unused;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Created by Влад on 30.10.2017.
 */
public class FindChangesFormController {
    public void closeForm(ActionEvent actionEvent) {
        javafx.stage.Window parentWindow = ((Node) actionEvent.getSource()).getScene().getWindow();
        parentWindow.hide();
    }

    public void showFoundChagesForm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/unused/foundChangesForm.fxml"));
        //loadModalWindow(actionEvent, "Найденные изменения", root);
    }
}
