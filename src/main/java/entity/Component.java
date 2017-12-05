package entity;

public class Component {
    private String name;
    private String formula;
    private String id;


    public Component(String name, String formula, String id) {
        this.name = name;
        this.formula = formula;
        this.id = id;
    }

    public Component(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getFormula() {
        return formula;
    }
    public String getName() {
        return name;
    }
}
