package controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;

/**
 * Created by Влад on 31.10.2017.
 */
public class FoundChangesFormController {
    public void closeForm(ActionEvent actionEvent) {
        javafx.stage.Window parentWindow = ((Node) actionEvent.getSource()).getScene().getWindow();
        parentWindow.hide();
    }
}
