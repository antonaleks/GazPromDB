package entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ObjectModel {

    private SimpleStringProperty type;
    private SimpleStringProperty name;
    private SimpleStringProperty className;
    private SimpleIntegerProperty id;

    public ObjectModel(String type, String name, String className) {
        this.type = new SimpleStringProperty(type);
        this.name = new SimpleStringProperty(name);
        this.className = new SimpleStringProperty(className);
    }

    public ObjectModel(String name, String className) {
        this.name = new SimpleStringProperty(name);
        this.className = new SimpleStringProperty(className);
    }

    public ObjectModel(String name, String className, int id) {
        this.name = new SimpleStringProperty(name);
        this.className = new SimpleStringProperty(className);
        this.id = new SimpleIntegerProperty(id);
    }

    public ObjectModel(String type, String name, String className, int id) {
        this.type = new SimpleStringProperty(type);
        this.name = new SimpleStringProperty(name);
        this.className = new SimpleStringProperty(className);
        this.id = new SimpleIntegerProperty(id);
    }

    public SimpleStringProperty getType() {
        return type;
    }

    public SimpleStringProperty getName() {
        return name;
    }

    public SimpleStringProperty getClassName() {
        return className;
    }

    public SimpleIntegerProperty getId() {
        return id;
    }
}
