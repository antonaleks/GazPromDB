package txtParsers;

import entity.Component;
import entity.EnergyBalance;
import entity.MassBalance;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private String path = "C:\\subd\\GazPromDB\\src\\main\\resources\\chemcad_models\\Benzene Hydrogenation to Cyclohexane (CYCLOHEX)\\Benzene_Hydrogenation_to_Cyclohexane_(CYCLOHEX).txt";

    public List<MassBalance> parseMassBalance(String input) throws IOException {
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
                String name = "";
                int lenght = compMas.length;
                for(int j = 0; j < lenght-4; j++)
                    name += compMas[j] + " ";
                massBalances.add(new MassBalance(name,compMas[lenght-4],compMas[lenght-3],compMas[lenght-2], compMas[lenght-1]));
            }
        }
        return massBalances;
    }

    public List<Component> parseComponent(String input) throws IOException {
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
                String name = "";
                int lenght = compMas.length;
                for(int j = 3; j < lenght-1; j++)
                    name += compMas[j] + " ";
                if(compMas.length > 4) components.add(new Component(name, compMas[lenght-1], compMas[2]));
                else components.add(new Component(compMas[lenght-1], compMas[lenght-2]));
            }
        }
        return components;
    }

    public List<EnergyBalance> parseEnergyBalance(String input) throws IOException {
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
                String name = "";
                int lenght = compMas.length;
                for(int j = 0; j < lenght-1; j++)
                    name += compMas[j] + " ";
                if(isOutput) energyBalances.add(new EnergyBalance(name,"", compMas[lenght-1]));
                else energyBalances.add(new EnergyBalance(name, compMas[lenght-1], ""));
            }
        }
        return energyBalances;
    }

    public boolean isOutput(String s){
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
        String input = new String(array);
        return input;
    }
}
