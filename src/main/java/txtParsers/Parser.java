package txtParsers;

import db.DataBaseTxtHelper;
import entity.Component;
import entity.EnergyBalance;
import entity.MassBalance;
import entity.StreamsElement;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private String path = "C:\\subd\\GazPromDB\\src\\main\\resources\\chemcad_models\\Benzene Hydrogenation to Cyclohexane (CYCLOHEX)\\Benzene_Hydrogenation_to_Cyclohexane_(CYCLOHEX).txt";

    public List<MassBalance> parseMassBalance(String input) {
        String pattern = "(?<=Overall Mass Balance)([\\s\\S]*?)(?=Total)";
        Pattern p  = Pattern.compile(pattern);
        Matcher m = p.matcher(input);
        m.find();
        String[] text = m.group().split("\n");
        List<MassBalance> massBalances = new ArrayList<>();
        for(int i = 2; i < text.length-1; i++){
            text[i] = text[i].replaceAll("[\\s]{2,}", " ").replaceAll("\r", "");
            String[] compMas = text[i].split(" ");
            if(compMas.length > 4){
                StringBuilder name = new StringBuilder();
                int lenght = compMas.length;
                for(int j = 0; j < lenght-4; j++)
                    name.append(compMas[j]).append(" ");
                massBalances.add(new MassBalance(name.toString(),compMas[lenght-4],compMas[lenght-3],compMas[lenght-2], compMas[lenght-1]));
            }
        }
        return massBalances;
    }

    public List<Component> parseComponent(String input) throws SQLException {
        String pattern = "(?<=COMPONENTS)([\\s\\S]*?)(?=\r\n\r\n)";
        Pattern p  = Pattern.compile(pattern);
        Matcher m = p.matcher(input);
        m.find();
        String[] text = m.group().split("\n");
        List<Component> components = new ArrayList<>();
        for(int i = 2; i < text.length; i++){
            text[i] = text[i].replaceAll("[\\s]{2,}", " ").replaceAll("\r", "");
            String[] compMas = text[i].split(" ");
            if(compMas.length > 3){
                StringBuilder name = new StringBuilder();
                int lenght = compMas.length;
                for(int j = 3; j < lenght-1; j++)
                    name.append(compMas[j]).append(" ");
                if(compMas.length > 4) components.add(new Component(name.toString(), compMas[lenght-1], compMas[2]));
                else components.add(new Component(compMas[lenght-1], compMas[lenght-2]));
            }
        }
        return components;
    }

    public List<EnergyBalance> parseEnergyBalance(String input) {
        String pattern = "(?<=Overall Energy Balance)([\\s\\S]*?)(?=Total  )";
        Pattern p  = Pattern.compile(pattern);
        Matcher m = p.matcher(input);
        m.find();
        String[] text = m.group().split("\n");
        List<EnergyBalance> energyBalances = new ArrayList<>();
        for(int i = 2; i < text.length-1; i++){
            boolean isOutput = isOutput(text[i]);
            text[i] = text[i].replaceAll("[\\s]{2,}", " ").replaceAll("\r", "");
            String[] compMas = text[i].split(" ");
            if(compMas.length >= 3){
                StringBuilder name = new StringBuilder();
                int lenght = compMas.length;
                for(int j = 0; j < lenght-1; j++)
                    name.append(compMas[j]).append(" ");
                if(isOutput) energyBalances.add(new EnergyBalance(name.toString(),"", compMas[lenght-1]));
                else energyBalances.add(new EnergyBalance(name.toString(), compMas[lenght-1], ""));
            }
        }
        return energyBalances;
    }

    private boolean isOutput(String s){
        int k = 0;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == ' ') k++;
            else k=0;
            if(k>19) return true;
        }
        return false;
    }

    public String parseTxtReport(String path) throws IOException {
        FileInputStream in = new FileInputStream(path);
        byte[] array = new byte[in.available()];
        in.read(array);
        return new String(array);
    }

    private ArrayList<String> getStreamsComponentsName(String input){
        String pattern = "(?<=Stream Name)([\\s\\S]*?)(?=Flow rates in)";
        ArrayList<String> propsName = new ArrayList<>();
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);
        m.find();
        String[] text = m.group().trim().split("\n");
        int i = 0;
        if(!(text[0].replaceAll("[\\s]{2,}", " ").trim().split(" ").length > 1))i++;
        for(int j = i; j < text.length; j++){
            propsName.add(text[j].split("   ")[0]);
        }
        return propsName;

    }

    public List<StreamsElement> parseStreams(String input, List<Component> components) throws SQLException {
        String pattern = "(?<=FLOW SUMMARIES:)([\\s\\S]*?)(?=$)";

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);

        List<StreamsElement> elements = new ArrayList<>();
        String text = "";
        while (m.find()){
            text += m.group() + " ";
        }
        ArrayList<String> propsName = getStreamsComponentsName(text);
        for (String name :
                propsName) {
            double[] values = parseComponentsInStream(text, name);
            if(values!=null) elements.add(new StreamsElement(parseComponentsInStream(text, name), name));
        }
        for (Component component :
                components) {

            double[] values = parseComponentsInStream(text, component.getName());
            if(values!=null) elements.add(new StreamsElement(values, component.getName()));
        }
        return elements;
    }

    public double[] parseComponentsInStream(String input, String name){
        if(name.contains("*")) return null;
        String pattern = "(?<=" + name.replaceAll("[+]","") + ")([\\s\\S]*?)(?=[\\n])";
        Pattern p  = Pattern.compile(pattern);
        Matcher m = p.matcher(input);
        String text = "";
        while (m.find()){
            text += m.group() + " ";
        }
        String textNoSpace = text.replace("\n", "").replace("\r","").replaceAll("[+]","").replaceAll("[A-Z,a-z]", "").replaceAll("[\\s]{2,}", " ").trim();
        String[] textArr = textNoSpace.split(" ");
        if(textArr==null)return null;
        double[] array = new double[textArr.length];
        for (int i = 0; i < textArr.length; i++) {
            array[i] = new Double(textArr[i].replace("*", ""));
        }
        return array;
    }
}
