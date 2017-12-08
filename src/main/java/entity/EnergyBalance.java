package entity;

public class EnergyBalance {
    private String name;
    private String input;

    private String output;

    public EnergyBalance(String name, String input,String output) {
        this.name = name;
        this.input = input;
        this.output = output;
    }

    public String getName() {
        return name;
    }

    public String getOutput() {
        return output;
    }

    public String getInput() {
        return input;
    }

    public String toString(){
        return this.name + "\t" + this.input + "\t" + this.output;
    }
}
