package entity;

public class Data {
    private int id;
    private String componentName;
    private String value;

    public Data(int id, String componentName, String value) {
        this.id = id;
        this.componentName = componentName;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getComponentName() {
        return componentName;
    }

    public int getId() {
        return id;
    }

    public String toString(){
        return this.componentName + ", " + this.value;
    }
}
