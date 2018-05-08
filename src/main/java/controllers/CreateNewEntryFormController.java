package controllers;

//import converters.Converter;

import com.jfoenix.controls.*;
import com.sun.jna.ptr.IntByReference;
import converters.Converter;
import db.DataBaseInsertHelper;
import db.DataBaseXmlHelper;
import entity.*;
import enums.Access;
import enums.SynchronizationMode;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.apache.commons.lang3.SerializationUtils;
import properties.JsonManager;
import txtParsers.Parser;
import winApi.ApiHelper;
import winApi.MyKernel32;
import winApi.threads.CreateJson;
import winApi.threads.FillDataBase;
import winApi.threads.ParseFiles;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Влад on 31.10.2017.
 */
public class CreateNewEntryFormController {
    public RadioButton rbEvent;
    public RadioButton rbMutex;
    public RadioButton rbSemaphore;
    public ListView<String> textLogList;
    @FXML
    private JFXButton mainFileChooseButton;
    @FXML
    private JFXListView listView;
    @FXML
    private JFXButton extraFilesChooseButton;
    @FXML
    private JFXCheckBox txtCheckBox;
    @FXML
    private JFXCheckBox dxfCheckBox;
    @FXML
    private JFXCheckBox xmlCheckBox;
    @FXML
    private JFXButton createButton;
    @FXML
    private JFXButton closeButton;
    @FXML
    private JFXComboBox comboBox;
    @FXML
    private JFXSpinner loading;

    private static SynchronizationMode mode;

    private static String pathToMainFile = "";
    private static String pathToTXT = "";
    private static String pathToDXF = "";
    private static String pathToXML = "";
    private static String pathToSVG = "";
    private static String name = "";
    private static int typeId;
    private static String log;

    public static SynchronizationMode getMode() {
        return mode;
    }

    public static int getTypeId() {
        return typeId;
    }

    public static void setPathToSVG(String pathToSVG) {
        CreateNewEntryFormController.pathToSVG = pathToSVG;
    }

    public static String getPathToMainFile() {
        return pathToMainFile;
    }

    public static String getPathToTXT() {
        return pathToTXT;
    }

    public static String getPathToDXF() {
        return pathToDXF;
    }

    public static String getPathToXML() {
        return pathToXML;
    }

    public static String getPathToSVG() {
        return pathToSVG;
    }

    public static String getName() {
        return name;
    }

    private static List<StreamsElement> streamsElements;

    public static void setStreamsElements(List<StreamsElement> streamsElements) {
        CreateNewEntryFormController.streamsElements = streamsElements;
    }

    public static List<StreamsElement> getStreamsElements() {
        return streamsElements;
    }

    private List<ModelType> types;

    public static void saveLog(String text){
        System.out.println(text);
        log += text + "\n";
    }

    @FXML
    public void initialize() throws SQLException {
        types = new DataBaseXmlHelper().selectAllModelsTypeToList();
        for (ModelType type : types)
            comboBox.getItems().add(type.getType());
        comboBox.setButtonCell(new ListCell() {

            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (!(item == null)) {
                    setStyle("-fx-text-fill: #FFFF8D");
                    setText(item.toString());
                }
            }

        });
    }

    public void chooseMainFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {

            String str = selectedFile.getPath();
            listView.getItems().add("Основной файл:");
            listView.getItems().add(str);
            txtCheckBox.setVisible(true);
            xmlCheckBox.setVisible(true);
            dxfCheckBox.setVisible(true);
            extraFilesChooseButton.setVisible(true);
        } else {
            System.out.println("Неверный файл");
        }
    }

    public void chooseExtraFiles(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        List<File> files = fileChooser.showOpenMultipleDialog(null);

        if (files != null) {
            listView.getItems().add("Доп. файлы:");
            for (File file : files) listView.getItems().add(file.getPath());
        } else {
            System.out.println("Файлы выбраны неверно!");
        }

    }

    public void closeForm(ActionEvent actionEvent) {
        javafx.stage.Window parentWindow = ((Node) actionEvent.getSource()).getScene().getWindow();
        parentWindow.hide();
    }

    public void insertNewModel(ActionEvent actionEvent) throws IOException, SQLException {
        log = "";
        listView.getItems().removeAll();
        Task task = new javafx.concurrent.Task<Void>() {

            @Override
            protected Void call() throws Exception {

                if(rbEvent.isSelected())
                    mode = SynchronizationMode.Event;
                else if(rbMutex.isSelected())
                    mode = SynchronizationMode.Mutex;
                else if(rbSemaphore.isSelected())
                    mode = SynchronizationMode.Semaphore;
                ParseFiles parseFiles = new ParseFiles();
                CreateJson createJson = new CreateJson();
                FillDataBase fillDataBase = new FillDataBase();

                ApiHelper.buildThreads(parseFiles, createJson, fillDataBase);

                return null;

            }


            @Override
            protected void succeeded() {
                loading.setVisible(false);
                createButton.setDisable(false);
                extraFilesChooseButton.setDisable(false);
                mainFileChooseButton.setDisable(false);
                closeButton.setDisable(false);
                String[] mas = log.split("\n");
                for (String message: mas) {
                    textLogList.getItems().add(message);
                }
                Alert alertComplete = new Alert(Alert.AlertType.INFORMATION);
                alertComplete.setTitle("Информация о выполнении задачи");
                alertComplete.setHeaderText("Внесение в базу данных новой записи");
                alertComplete.setContentText("Новая запись была успешно создана");
                alertComplete.showAndWait();
            }

            @Override
            protected void failed() {
                loading.setVisible(false);
                createButton.setDisable(false);
                extraFilesChooseButton.setDisable(false);
                mainFileChooseButton.setDisable(false);
                closeButton.setDisable(false);
            }

        };
        if (Access.checkAccess(User.getCurrentUser().getAccess(), Access.Right.WRITE)) {
            try {
                if ("cc6".equals(((String) listView.getItems().get(1)).substring(((String) listView.getItems().get(1)).lastIndexOf('.') + 1))) {
                    pathToMainFile = ((String) listView.getItems().get(1));
                    name = new File(pathToMainFile).getName().split("\\.")[0];
                } else throw new IllegalArgumentException();
                int count = 0b000;
                for (int i = 3; i < listView.getItems().size(); i++) {
                    String row = ((String) listView.getItems().get(i));
                    if (row.contains(File.separator)) {
                        if ("txt".equals(row.substring(row.lastIndexOf('.') + 1))) {
                            pathToTXT = row;
                            count |= 0b001;
                        }
                        if ("dxf".equals(row.substring(row.lastIndexOf('.') + 1))) {
                            pathToDXF = row;
                            count |= 0b010;
                        }
                        if ("xml".equals(row.substring(row.lastIndexOf('.') + 1))) {
                            pathToXML = row;
                            count |= 0b100;
                        }
                    }
                }

                if (count != 0b111) throw new IllegalArgumentException();

                typeId = -1;
                for (ModelType type : types) {
                    if (comboBox.getSelectionModel().getSelectedItem().equals(type.getType())) {
                        typeId = type.getId();
                        break;
                    }
                }
                Thread thread = new Thread(task, "My Task");
                thread.setDaemon(true);
                loading.setVisible(true);
                createButton.setDisable(true);
                extraFilesChooseButton.setDisable(true);
                mainFileChooseButton.setDisable(true);
                closeButton.setDisable(true);
                thread.start();
            } catch (IllegalArgumentException e) {
                Alert alertComplete = new Alert(Alert.AlertType.ERROR);
                alertComplete.setTitle("Информация о выполнении задачи");
                alertComplete.setHeaderText("Внесение в базу данных новой записи");
                alertComplete.setContentText("Ошибка! Неверный формат файла");
                alertComplete.showAndWait();
            }
        } else {
            Alert alertComplete = new Alert(Alert.AlertType.ERROR);
            alertComplete.setTitle("Информация о выполнении задачи");
            alertComplete.setHeaderText("Внесение в базу данных новой записи");
            alertComplete.setContentText("Ошибка! Вы не имеете прав на создание новой записи");
            alertComplete.showAndWait();
        }

    }
}
