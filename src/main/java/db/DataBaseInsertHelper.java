package db;

import entity.Component;
import entity.EnergyBalance;
import entity.MassBalance;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import txtParsers.Parser;
import utilities.MySqlConnect;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DataBaseInsertHelper extends DataBaseHelper {
    //Все в проперти
    private final static String sqlInsertObjects = "insert into objects(model_files_id, class, name) values(?, ?, ?)";
    private final static String sqlInsertObject = "insert into object(objects_id, class, name) values(?, ?, ?)";
    private final static String sqlInsertAttribute = "insert into attribute(object_id, name, unit, dataType, description) values(?, ?, ?, ?, ?)";
    private final static String sqlInsertData = "insert into data(attribute_id, component_name, value) values(?, ?, ?)";
    private final static String sqlInsertModel = "insert into model_files(modeltype_id, pathToXML, pathToMainFile, pathToDXF, pathToTXT, date) values(?, ?, ?, ?, ?, CURRENT_DATE())";
    private final static String sqlLastId = "select last_insert_id()";

    private final static String sqlInsertTxtEnergyBalance = "insert into txt_energy_balance(name, input, output, model_files_id) values(?, ?, ?, ?)";
    private final static String sqlInsertTxtMassBalance = "insert into txt_mass_balance(name, input_1, output_1, input_2, output_2, model_files_id) values(?, ?, ?, ?, ?, ?)";
    private final static String sqlInsertTxtComponents = "insert into txt_components(name, formula, component_index, model_files_id) values(?, ?, ?, ?)";

    private int modelId;

    public int getLastInsertId() {
        try {
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(sqlLastId);
            rs.next();

            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void insertTxtEnergyBalance(String name, String input, String output, int modelId) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(sqlInsertTxtEnergyBalance);
        statement.setString(1, name);
        statement.setString(2, input);
        statement.setString(3, output);
        statement.setInt(4, modelId);
        statement.execute();
    }

    public void insertTxtMassBalance(String name, String input_1, String output_1, String input_2, String output_2, int modelId) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(sqlInsertTxtMassBalance);
        statement.setString(1, name);
        statement.setString(2, input_1);
        statement.setString(3, output_1);
        statement.setString(4, input_2);
        statement.setString(5, output_2);
        statement.setInt(6, modelId);
        statement.execute();
    }

    public void insertTxtComponent(String name, String formula, String index, int modelId) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(sqlInsertTxtComponents);
        statement.setString(1, name);
        statement.setString(2, formula);
        statement.setString(3, index);
        statement.setInt(4, modelId);
        statement.execute();
    }

    public void insertTxtReport(List<EnergyBalance> energyBalances,
                                List<MassBalance> massBalances,
                                List<Component> components) throws SQLException {
        for(EnergyBalance energy : energyBalances)
            insertTxtEnergyBalance(energy.getName(), energy.getInput(), energy.getOutput(), modelId);
        for(MassBalance mass : massBalances)
            insertTxtMassBalance(mass.getName(), mass.getInput_1(), mass.getOutput_1(), mass.getInput_2(), mass.getOutput_2(), modelId);
        for(Component component : components)
            insertTxtComponent(component.getName(), component.getFormula(), component.getId(), modelId);
    }

    public void insertToModel(int typeId, String pathToXML, String pathToDXF, String pathToMainFile, String pathToTXT) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(sqlInsertModel);
        statement.setInt(1, typeId);
        statement.setString(2, pathToXML);
        statement.setString(3, pathToMainFile);
        statement.setString(4, pathToDXF);
        statement.setString(5, pathToTXT);
        statement.execute();
    }

    public void insertToObjects(int modelId, String className, String Name) throws SQLException {
        PreparedStatement statementObjects = conn.prepareStatement(sqlInsertObjects);
        statementObjects.setInt(1, modelId);
        statementObjects.setString(2, className);
        statementObjects.setString(3, Name);
        statementObjects.execute();
    }

    public void insertToObject(int objectsId, String className, String name) throws SQLException {
        PreparedStatement statementObject = conn.prepareStatement(sqlInsertObject);
        statementObject.setInt(1, objectsId);
        statementObject.setString(2, className);
        statementObject.setString(3, name);
        statementObject.execute();
    }

    public void insertToAttribute(int objectId, String name, String unit, String dataType, String description) throws SQLException {
        PreparedStatement statementAttribute = conn.prepareStatement(sqlInsertAttribute);
        statementAttribute.setInt(1, objectId);
        statementAttribute.setString(2, name);
        statementAttribute.setString(3, unit);
        statementAttribute.setString(4, dataType);
        statementAttribute.setString(5, description);
        statementAttribute.execute();
    }

    public void insertToData(int attributeId, String componentName, String value) throws SQLException {
        PreparedStatement statementData = conn.prepareStatement(sqlInsertData);
        statementData.setInt(1, attributeId);
        statementData.setString(2, componentName);
        statementData.setString(3, value);
        statementData.execute();
    }

    public void fillDataBase(int typeId, String pathToXML, String pathToDXF, String pathToMainFile, String pathToTXT) {

        mySqlConnect = new MySqlConnect();
        conn = mySqlConnect.connect();

        try {
            //парсинг xml
            SAXBuilder saxBuilder = new SAXBuilder();
            File xmlFile = new File(pathToXML);
            Document document = saxBuilder.build(xmlFile);
            Element rootNode = document.getRootElement();

            //вставка модели
            insertToModel(typeId, xmlFile.getAbsolutePath(), pathToDXF, pathToMainFile, pathToTXT);

            //последний вставленный индекс
            modelId = getLastInsertId();

            List<Element> objectsList = rootNode.getChildren("Objects");
            for (Element objects : objectsList) {
                System.out.println("ObjectModel Name: "
                        + objects.getAttributeValue("Name"));
                //вставка ObjectModel
                insertToObjects(modelId, objects.getAttributeValue("ClassName"),
                        objects.getAttributeValue("Name"));

                //последний вставленный индекс
                int objectsId = getLastInsertId();

                List<Element> objectList = objects.getChildren("Object");
                for (Element object : objectList) {
                    System.out.println("\tObject ClassName: "
                            + object.getAttributeValue("Class") + "\tName: "
                            + object.getAttributeValue("Name"));
                    //вставка ObjectModel
                    insertToObject(objectsId, object.getAttributeValue("Class"),
                            object.getAttributeValue("Name"));

                    //последний вставленный индекс
                    int objectId = getLastInsertId();

                    List<Element> attributeList = object.getChildren("Attribute");
                    for (Element attribute : attributeList) {
                        System.out.println("\t\tAttribute Name: "
                                + attribute.getAttributeValue("Name") + "\tUnit: "
                                + attribute.getAttributeValue("Unit") + "\tDescription: "
                                + attribute.getAttributeValue("Description") + "\tDataType: "
                                + attribute.getAttributeValue("DataType"));
                        //вставка Attribute
                        insertToAttribute(objectId,
                                attribute.getAttributeValue("Name"),
                                attribute.getAttributeValue("Unit"),
                                attribute.getAttributeValue("DataType"),
                                attribute.getAttributeValue("Description"));

                        //последний вставленный индекс
                        int attributeId = getLastInsertId();

                        List<Element> dataList = attribute.getChildren("Data");
                        for (Element data : dataList) {
                            System.out.println("\t\t\tData ComponentName: "
                                    + data.getAttributeValue("ComponentName") + "\tValue: "
                                    + data.getAttributeValue("Value"));
                            //вставка Data
                            insertToData(attributeId, data.getAttributeValue("ComponentName"),
                                    data.getAttributeValue("Value"));
                        }
                    }
                }
            }
            Parser txtParser = new Parser();
            String txtInput = txtParser.parseTxtReport(pathToTXT);
            List<MassBalance> massBalance = txtParser.parseMassBalance(txtInput);
            List<EnergyBalance> energyBalance = txtParser.parseEnergyBalance(txtInput);
            List<Component> components = txtParser.parseComponent(txtInput);
            insertTxtReport(energyBalance, massBalance, components);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JDOMException ex) {
            ex.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySqlConnect.disconnect();
        }
    }
}
