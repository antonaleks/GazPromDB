package properties;

import entity.StreamsElement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonManager {

    private static final String FILENAME= "src/main/resources/chemcad_models/";
    private static final String EXTENSION= ".json";

    public static void createJson(List<StreamsElement> list, String modelName) {
        JSONObject object = new JSONObject();
        JSONObject streams = new JSONObject();
        JSONArray streamsNoArr = new JSONArray();
        for (int i = 0; i < list.get(0).getValues().length; i++) {
            streamsNoArr.add(i+1);
        }
        streams.put("Stream No", streamsNoArr);
        for (StreamsElement element:
             list) {
            JSONArray streamsValues = new JSONArray();
            for (int i = 0; i < element.getValues().length; i++) {
                streamsValues.add(element.getValues()[i]);
            }
            streams.put(element.getName(), streamsValues);
        }
        object.put("report", streams);
        try (FileWriter writer = new FileWriter(FILENAME + modelName + "/" + modelName + EXTENSION)){
            writer.write(object.toJSONString());
            writer.flush();
            writer.close();
        } catch (IOException ex) {

        }
    }
}
