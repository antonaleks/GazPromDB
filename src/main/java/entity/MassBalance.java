package entity;

public class MassBalance {

    private String name;
    private String input_1;
    private String input_2;
    private String output_1;
    private String output_2;

    public MassBalance(String name, String input_1, String input_2, String output_1, String output_2) {
        this.name = name;
        this.input_1 = input_1;
        this.input_2 = input_2;
        this.output_1 = output_1;
        this.output_2 = output_2;
    }

    public String getOutput_2() {
        return output_2;
    }

    public String getOutput_1() {
        return output_1;
    }

    public String getInput_2() {
        return input_2;
    }

    public String getInput_1() {
        return input_1;
    }

    public String getName() {
        return name;
    }

    public String toString(){
        return this.name + "\t" + this.input_1 + "\t" + this.output_1 + "\t" + this.input_2 + "\t" + this.output_2;
    }
}
