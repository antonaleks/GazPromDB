package db;

import entity.Component;
import entity.EnergyBalance;
import entity.MassBalance;
import entity.User;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import properties.PropertiesManager;
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

    // Строки для добавления в таблицы
    private StringBuilder sqlValuesObjects = new StringBuilder("");
    private StringBuilder sqlValuesObject = new StringBuilder("");
    private StringBuilder sqlValuesAttribute = new StringBuilder("");
    private StringBuilder sqlValuesData = new StringBuilder("");

    private int getLastInsertId() {
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

    private void insertTxtEnergyBalance(String name, String input, String output, int modelId) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(SqlQueryHelper.sqlInsertTxtEnergyBalance);
        statement.setInt(1, modelId);
        statement.setString(2, name);
        statement.setString(3, input);
        statement.setString(4, output);
        statement.execute();
    }

    private void insertTxtMassBalance(String name, String input_1, String output_1, String input_2, String output_2, int modelId) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(SqlQueryHelper.sqlInsertTxtMassBalance);
        statement.setInt(1, modelId);
        statement.setString(2, name);
        statement.setString(3, input_1);
        statement.setString(4, output_1);
        statement.setString(5, input_2);
        statement.setString(6, output_2);
        statement.execute();
    }

    private void insertTxtComponent(String name, String formula, String index, int modelId) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(SqlQueryHelper.sqlInsertTxtComponents);
        statement.setInt(1, modelId);
        statement.setString(2, name);
        statement.setString(3, formula);
        statement.setString(4, index);
        statement.execute();
    }

    private void insertTxtReport(List<EnergyBalance> energyBalances,
                                 List<MassBalance> massBalances,
                                 List<Component> components) throws SQLException {
        for(EnergyBalance energy : energyBalances)
            insertTxtEnergyBalance(energy.getName(), energy.getInput(), energy.getOutput(), modelId);
        for(MassBalance mass : massBalances)
            insertTxtMassBalance(mass.getName(), mass.getInput_1(), mass.getOutput_1(), mass.getInput_2(), mass.getOutput_2(), modelId);
        for(Component component : components)
            insertTxtComponent(component.getName(), component.getFormula(), component.getId(), modelId);
    }

    private void insertToModel(int typeId, String pathToXML, String pathToDXF, String pathToMainFile, String pathToTXT, String pathToSvg, String name, int creatorId) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(SqlQueryHelper.sqlInsertModel);
        statement.setInt(1, typeId);
        statement.setInt(2, creatorId);
        statement.setString(3, pathToXML);
        statement.setString(4, pathToMainFile);
        statement.setString(5, pathToDXF);
        statement.setString(6, pathToTXT);
        statement.setString(7, pathToSvg);
        statement.setString(8, name);
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

    private void insertAllToTable(String sql, String params) throws SQLException {
        PreparedStatement statementData = conn.prepareStatement(sql + params);
        statementData.execute();
    }

    private int getLustIdFrom(String tableName) throws SQLException {
        PreparedStatement statementData = conn.prepareStatement(String.format(SqlQueryHelper.sqlLastIdFromTableName, tableName));
        ResultSet rs =statementData.executeQuery();
        if(rs.next())
            return rs.getInt(1);
        return -1;

    }

    public void fillDataBase(int typeId, String pathToXML, String pathToDXF, String pathToMainFile, String pathToTXT, String name, String pathToSvg) {

        mySqlConnect = new MySqlConnect();
        conn = mySqlConnect.connect();

        try {
            //парсинг xml
            SAXBuilder saxBuilder = new SAXBuilder();
            File xmlFile = new File(pathToXML);
            Document document = saxBuilder.build(xmlFile);
            Element rootNode = document.getRootElement();

            //вставка модели
            insertToModel(typeId, xmlFile.getAbsolutePath(), pathToDXF, pathToMainFile, pathToTXT, pathToSvg, name, User.getCurrentUser().getId());

            //последний вставленный индекс
            modelId = getLastInsertId();
            int objectsIndex = getLustIdFrom(PropertiesManager.getSqlTableProperties().getProperty("XML_OBJECTS_TABLE_NAME"));
            int objectIndex = getLustIdFrom(PropertiesManager.getSqlTableProperties().getProperty("XML_OBJECT_TABLE_NAME"));
            int attributeIndex = getLustIdFrom(PropertiesManager.getSqlTableProperties().getProperty("XML_ATTRIBUTE_TABLE_NAME"));


            List<Element> objectsList = rootNode.getChildren("Objects");
            for (Element objects : objectsList) {
                sqlValuesObjects.append(String.format("(%s, '%s', '%s'),",modelId,
                        objects.getAttributeValue("ClassName"),
                        objects.getAttributeValue("Name")));
                objectsIndex++;

                List<Element> objectList = objects.getChildren("Object");
                for (Element object : objectList) {
                    sqlValuesObject.append(String.format("(%s, '%s', '%s'),",objectsIndex,
                            object.getAttributeValue("Class"),
                            object.getAttributeValue("Name")));
                    objectIndex++;

                    List<Element> attributeList = object.getChildren("Attribute");
                    for (Element attribute : attributeList) {
                        sqlValuesAttribute.append(String.format("(%s, '%s', '%s', '%s'),",objectIndex,
                                attribute.getAttributeValue("Name"),
                                attribute.getAttributeValue("Unit"),
                                attribute.getAttributeValue("DataType")));
                        attributeIndex++;

                        List<Element> dataList = attribute.getChildren("Data");
                        for (Element data : dataList) {
                            sqlValuesData.append(String.format("(%s, '%s', '%s'),",attributeIndex,
                                    data.getAttributeValue("ComponentName"),
                                    data.getAttributeValue("Value")));
                        }
                    }
                }
            }
            insertAllToTable(SqlQueryHelper.sqlInsertAllXMLObjects, sqlValuesObjects.substring(0, sqlValuesObjects.length()-1));
            insertAllToTable(SqlQueryHelper.sqlInsertAllXMLObject, sqlValuesObject.substring(0, sqlValuesObject.length()-1));
            insertAllToTable(SqlQueryHelper.sqlInsertAllXMLAttribute, sqlValuesAttribute.substring(0, sqlValuesAttribute.length()-1));
            insertAllToTable(SqlQueryHelper.sqlInsertAllXMLData, sqlValuesData.substring(0, sqlValuesData.length()-1));
            Parser txtParser = new Parser();
            String txtInput = txtParser.parseTxtReport(pathToTXT);
            List<MassBalance> massBalance = txtParser.parseMassBalance(txtInput);
            List<EnergyBalance> energyBalance = txtParser.parseEnergyBalance(txtInput);
            List<Component> components = txtParser.parseComponent(txtInput);
            insertTxtReport(energyBalance, massBalance, components);
        } catch (IOException | SQLException | JDOMException ex) {
            ex.printStackTrace();
        } finally {
            mySqlConnect.disconnect();
        }
    }
}
