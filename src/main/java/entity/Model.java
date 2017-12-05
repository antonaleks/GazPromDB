package entity;

import java.util.Date;

public class Model {
    private int id;
    private String type;
    private Date date;
    private String pathToXml;

    public Model(int id, String type, Date date, String pathToXml) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.pathToXml = pathToXml;
    }

    public String getPathToXml() {
        return pathToXml;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }
}
