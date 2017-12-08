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

    // Идентификатор добавляемой модели
    private int modelId;

    public int getLastInsertId() {
        try {
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(SqlQueryHelper.sqlLastInsertId);
            rs.next();

            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void insertTxtEnergyBalance(String name, String input, String output, int modelId) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(SqlQueryHelper.sqlInsertTxtEnergyBalance);
        statement.setInt(1, modelId);
        statement.setString(2, name);
        statement.setString(3, input);
        statement.setString(4, output);
        statement.execute();
    }

    public void insertTxtMassBalance(String name, String input_1, String output_1, String input_2, String output_2, int modelId) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(SqlQueryHelper.sqlInsertTxtMassBalance);
        statement.setInt(1, modelId);
        statement.setString(2, name);
        statement.setString(3, input_1);
        statement.setString(4, output_1);
        statement.setString(5, input_2);
        statement.setString(6, output_2);
        statement.execute();
    }

    public void insertTxtComponent(String name, String formula, String index, int modelId) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(SqlQueryHelper.sqlInsertTxtComponents);
        statement.setInt(1, modelId);
        statement.setString(2, name);
        statement.setString(3, formula);
        statement.setString(4, index);
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
        PreparedStatement statement = conn.prepareStatement(SqlQueryHelper.sqlInsertModel);
        statement.setInt(1, typeId);
        statement.setString(2, pathToXML);
        statement.setString(3, pathToMainFile);
        statement.setString(4, pathToDXF);
        statement.setString(5, pathToTXT);
        statement.execute();
    }

    public void insertToObjects(int modelId, String className, String Name) throws SQLException {
        PreparedStatement statementObjects = conn.prepareStatement(SqlQueryHelper.sqlInsertXMLObjects);
        statementObjects.setInt(1, modelId);
        statementObjects.setString(2, className);
        statementObjects.setString(3, Name);
        statementObjects.execute();
    }

    public void insertToObject(int objectsId, String className, String name) throws SQLException {
        PreparedStatement statementObject = conn.prepareStatement(SqlQueryHelper.sqlInsertXMLObject);
        statementObject.setInt(1, objectsId);
        statementObject.setString(2, className);
        statementObject.setString(3, name);
        statementObject.execute();
    }

    public void insertToAttribute(int objectId, String name, String unit, String dataType) throws SQLException {
        PreparedStatement statementAttribute = conn.prepareStatement(SqlQueryHelper.sqlInsertXMLAttribute);
        statementAttribute.setInt(1, objectId);
        statementAttribute.setString(2, name);
        statementAttribute.setString(3, unit);
        statementAttribute.setString(4, dataType);
        statementAttribute.execute();
    }

    public void insertToData(int attributeId, String componentName, String value) throws SQLException {
        PreparedStatement statementData = conn.prepareStatement(SqlQueryHelper.sqlInsertXMLData);
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
                                attribute.getAttributeValue("DataType"));

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
