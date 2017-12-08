package entity;

import java.util.Date;

public class Model {
    private int id;
    private String type;
    private Date date;
    private String pathToXml;
    private String pathToTxt;

    public Model(int id, String type, Date date, String pathToXml, String pathToTxt) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.pathToXml = pathToXml;
        this.pathToTxt = pathToTxt;
    }

    public String getPathToXml() {
        return pathToXml;
    }

    public String getPathToTxt() {
        return pathToTxt;
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
