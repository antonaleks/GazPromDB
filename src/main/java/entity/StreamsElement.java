package entity;

import java.io.Serializable;

public class StreamsElement implements Serializable{
    private double[] values;
    private String name;

    public double[] getValues() {
        return values;
    }

    public void setValues(double[] values) {
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StreamsElement(double[] values, String name) {
        this.values = values;
        this.name = name;
    }
}
