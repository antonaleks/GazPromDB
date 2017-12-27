package start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/enterForm.fxml"));
        primaryStage.setTitle("Вход в систему");
        primaryStage.setScene(new Scene(root));
        /*primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(300);
        primaryStage.setMaxHeight(400);*/
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
