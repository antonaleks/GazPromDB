package entity;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Date;

public class Model {
    private int id;
    private String type;
    private Date date;
    private String pathToXml;
    private String creator;
    private String pathToDxf;
    private String pathToSvg;
    private String pathToTxt;
    private String pathToMainFile;

    private String name;

    public Model(int id, String type, Date date, String pathToXml, String pathToTxt, String pathToSvg, String creator, String name, String pathToMainFile, String pathToDxf) throws MalformedURLException {
        this.id = id;
        this.type = type;
        this.date = date;
        this.pathToXml = pathToXml;
        this.pathToTxt = pathToTxt;
        this.pathToSvg = pathToSvg;
        this.creator = creator;
        this.name = name;
        this.pathToMainFile = pathToMainFile;
        this.pathToDxf = pathToDxf;
    }


    public String getName() {
        return name;
    }

    public String getPathToMainFile() {
        return pathToMainFile;
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

    public String getPathToSvg() {
        return pathToSvg;
    }

    public String getPathToDxf() {
        return pathToDxf;
    }

}
