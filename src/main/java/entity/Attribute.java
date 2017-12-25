package entity;

public class Attribute {
    private int id;
    private String name;
    private String unit;
    private String dataType;

    public Attribute(int id, String name, String unit, String dataType) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.dataType = dataType;
    }

    public String getUnit() {
        return unit;
    }

    public String getName() {
        return name;
    }

    public String getDataType() {
        return dataType;
    }

    public int getId() {
        return id;
    }

    public String toString(){
        return this.name + ", " + this.unit + ", " + this.dataType;
    }
}
