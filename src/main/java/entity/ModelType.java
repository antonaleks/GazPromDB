package entity;

import java.util.Date;

public class ModelType {
    private int id;
    private String type;

    public ModelType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }
}
