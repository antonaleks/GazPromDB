package entity;

import java.util.Date;

public class Model {
    private int id;
    private String type;
    private Date date;
    private String pathToXml;
    private String creator;
    private String pathToDxf;
    private String pathToPng;
    private String pathToTxt;
    private String name;

    public Model(int id, String type, Date date, String pathToXml, String pathToTxt, String pathToPng, String creator, String name) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.pathToXml = pathToXml;
        this.pathToTxt = pathToTxt;
        this.pathToPng = pathToPng;
        this.creator = creator;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCreator() {
        return creator;
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

    public String getPathToPng() {
        return pathToPng;
    }

    public String getPathToDxf() {
        return pathToDxf;
    }

}
