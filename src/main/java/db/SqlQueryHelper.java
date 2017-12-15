package db;

import properties.PropertiesManager;

public class SqlQueryHelper {
    /*
    * SQL запросы на добавление записей
    * */

    // Добавление в XML таблицы
    protected final static String sqlInsertXMLObjects = String.format("INSERT INTO %s(%s, %s, %s) values(?, ?, ?)",
            PropertiesManager.getSqlTableProperties().getProperty("XML_OBJECTS_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("FOREIGN_KEY_MODEL_FILES"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_OBJECTS_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_OBJECTS_CLASS"));
    protected final static String sqlInsertXMLObject = String.format("INSERT INTO %s(%s, %s, %s) values(?, ?, ?)",
            PropertiesManager.getSqlTableProperties().getProperty("XML_OBJECT_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("FOREIGN_KEY_XML_OBJECTS"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_OBJECT_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_OBJECT_CLASS"));
    protected final static String sqlInsertXMLAttribute = String.format("INSERT INTO %s(%s, %s, %s, %s) values(?, ?, ?, ?)",
            PropertiesManager.getSqlTableProperties().getProperty("XML_ATTRIBUTE_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("FOREIGN_KEY_XML_OBJECT"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_ATTRIBUTE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_ATTRIBUTE_UNIT"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_ATTRIBUTE_DATA_TYPE"));
    protected final static String sqlInsertXMLData = String.format("INSERT INTO %s(%s, %s, %s) values(?, ?, ?)",
            PropertiesManager.getSqlTableProperties().getProperty("XML_DATA_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("FOREIGN_KEY_XML_ATTRIBUTE"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_DATA_COMPONENT_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_DATA_VALUE"));

    protected final static String sqlInsertAllXMLObjects = String.format("INSERT INTO %s(%s, %s, %s) values",
            PropertiesManager.getSqlTableProperties().getProperty("XML_OBJECTS_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("FOREIGN_KEY_MODEL_FILES"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_OBJECTS_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_OBJECTS_CLASS"));
    protected final static String sqlInsertAllXMLObject = String.format("INSERT INTO %s(%s, %s, %s) values",
            PropertiesManager.getSqlTableProperties().getProperty("XML_OBJECT_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("FOREIGN_KEY_XML_OBJECTS"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_OBJECT_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_OBJECT_CLASS"));
    protected final static String sqlInsertAllXMLAttribute = String.format("INSERT INTO %s(%s, %s, %s, %s) values",
            PropertiesManager.getSqlTableProperties().getProperty("XML_ATTRIBUTE_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("FOREIGN_KEY_XML_OBJECT"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_ATTRIBUTE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_ATTRIBUTE_UNIT"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_ATTRIBUTE_DATA_TYPE"));
    protected final static String sqlInsertAllXMLData = String.format("INSERT INTO %s(%s, %s, %s) values",
            PropertiesManager.getSqlTableProperties().getProperty("XML_DATA_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("FOREIGN_KEY_XML_ATTRIBUTE"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_DATA_COMPONENT_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_DATA_VALUE"));

    // Добавление в TXT таблицы
    protected final static String sqlInsertTxtEnergyBalance = String.format("INSERT INTO %s(%s, %s, %s, %s) values(?, ?, ?, ?)",
            PropertiesManager.getSqlTableProperties().getProperty("TXT_ENERGY_BALANCE_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("FOREIGN_KEY_MODEL_FILES"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_ENERGY_BALANCE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_ENERGY_BALANCE_INPUT"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_ENERGY_BALANCE_OUTPUT"));
    protected final static String sqlInsertTxtMassBalance = String.format("INSERT INTO %s(%s, %s, %s, %s, %s, %s) values(?, ?, ?, ?, ?, ?)",
            PropertiesManager.getSqlTableProperties().getProperty("TXT_MASS_BALANCE_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("FOREIGN_KEY_MODEL_FILES"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_MASS_BALANCE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_MASS_BALANCE_INPUT_1"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_MASS_BALANCE_OUTPUT_1"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_MASS_BALANCE_INPUT_2"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_MASS_BALANCE_OUTPUT_2"));
    protected final static String sqlInsertTxtComponents = String.format("INSERT INTO %s(%s, %s, %s, %s) values(?, ?, ?, ?)",
            PropertiesManager.getSqlTableProperties().getProperty("TXT_COMPONENTS_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("FOREIGN_KEY_MODEL_FILES"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_COMPONENTS_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_COMPONENTSE_FORMULA"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_COMPONENTS_COMPONENT_INDEX"));

    // Добавление в файловые таблицы
    protected final static String sqlInsertModel = String.format("INSERT INTO %s(%s, %s, %s, %s, %s, %s, %s, %s, %s) values(?, ?, ?, ?, ?, ?, CURRENT_DATE(), ?, ?)",
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_FILES_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("FOREIGN_KEY_MODEL_TYPE"),
            PropertiesManager.getSqlTableProperties().getProperty("FOREIGN_KEY_USER"),
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_FILES_PATH_TO_XML"),
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_FILES_PATH_TO_MAIN_FILE"),
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_FILES_PATH_TO_DXF"),
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_FILES_PATH_TO_TXT"),
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_FILES_CREATION_DATE"),
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_FILES_PATH_TO_PNG"),
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_FILES_MODEL_NAME"));

    // Вспомогательные запросы
    protected final static String sqlLastInsertId = "SELECT last_insert_id()";
    protected final static String sqlLastIdFromTableName = "SELECT MAX(`id`) FROM %s";

    // Запросы выборки данных из XML файла
    protected final static String sqlSelectObjects = String.format("SELECT obj.%s, obj.%s, obj.id FROM %s obj INNER JOIN " +
            "%s model on obj.%s = model.id where model.id=?",
            PropertiesManager.getSqlTableProperties().getProperty("XML_OBJECTS_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_OBJECTS_CLASS"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_OBJECTS_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_FILES_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("FOREIGN_KEY_MODEL_FILES"));
    protected final static String sqlSelectObject = String.format("SELECT obj.%s, obj.%s, obj.id FROM %s as obj INNER JOIN " +
            "%s objs on obj.%s=objs.id where objs.id=?",
            PropertiesManager.getSqlTableProperties().getProperty("XML_OBJECT_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_OBJECT_CLASS"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_OBJECT_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_OBJECTS_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("FOREIGN_KEY_XML_OBJECTS"));
    protected final static String sqlSelectAttributes = String.format("SELECT attr.id, attr.%s, attr.%s, attr.%s " +
            "FROM %s attr INNER JOIN %s obj on attr.%s = obj.id where obj.id = ?",
            PropertiesManager.getSqlTableProperties().getProperty("XML_ATTRIBUTE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_ATTRIBUTE_UNIT"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_ATTRIBUTE_DATA_TYPE"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_ATTRIBUTE_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_OBJECT_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("FOREIGN_KEY_XML_OBJECT"));
    protected final static String sqlSelectData = String.format("SELECT dat.id, dat.%s, dat.%s FROM %s dat INNER JOIN " +
            "%s attr on dat.%s=attr.id where attr.id = ?",
            PropertiesManager.getSqlTableProperties().getProperty("XML_DATA_COMPONENT_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_DATA_VALUE"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_DATA_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("XML_ATTRIBUTE_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("FOREIGN_KEY_XML_ATTRIBUTE"));

    // Запросы выборки из TXT отчета
    protected final static String sqlSelectComponent = String.format("SELECT comp.%s, comp.%s, comp.%s FROM %s comp INNER JOIN " +
                    "%s model on comp.%s=model.id where model.id = ?",
            PropertiesManager.getSqlTableProperties().getProperty("TXT_COMPONENTS_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_COMPONENTSE_FORMULA"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_COMPONENTS_COMPONENT_INDEX"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_COMPONENTS_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_FILES_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("FOREIGN_KEY_MODEL_FILES"));
    protected final static String sqlSelectEnergyBalance = String.format("SELECT energy.%s, energy.%s, energy.%s FROM %s energy INNER JOIN " +
                    "%s model on energy.%s=model.id where model.id = ?",
            PropertiesManager.getSqlTableProperties().getProperty("TXT_ENERGY_BALANCE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_ENERGY_BALANCE_INPUT"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_ENERGY_BALANCE_OUTPUT"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_ENERGY_BALANCE_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_FILES_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("FOREIGN_KEY_MODEL_FILES"));
    protected final static String sqlSelectMassBalance = String.format("SELECT mass.%s, mass.%s, mass.%s, mass.%s, mass.%s FROM %s mass INNER JOIN " +
                    "%s model on mass.%s=model.id where model.id = ?",
            PropertiesManager.getSqlTableProperties().getProperty("TXT_MASS_BALANCE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_MASS_BALANCE_INPUT_1"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_MASS_BALANCE_OUTPUT_1"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_MASS_BALANCE_INPUT_2"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_MASS_BALANCE_OUTPUT_2"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_MASS_BALANCE_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_FILES_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("FOREIGN_KEY_MODEL_FILES"));

    // Запросы из таблицы с файлами и типом
    protected final static String sqlSelectModels = String.format("SELECT model.%s as name, model.%s as png, " +
                    "model.%s as xml, model.%s as txt, model.id as id, modtype.%s as type, users.%s as creator," +
                    " model.%s as date FROM %s  model INNER JOIN %s  modtype on model.%s = modtype.id " +
                    "INNER JOIN %s as users on model.%s=users.id",
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_FILES_MODEL_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_FILES_PATH_TO_PNG"),
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_FILES_PATH_TO_XML"),
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_FILES_PATH_TO_TXT"),
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_TYPE_TYPE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("USERS_LOGIN"),
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_FILES_CREATION_DATE"),
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_FILES_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_TYPE_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("FOREIGN_KEY_MODEL_TYPE"),
            PropertiesManager.getSqlTableProperties().getProperty("USERS_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("FOREIGN_KEY_USER"));
    protected final static String sqlSelectModelsByParams = String.format("SELECT model.%s as name, model.%s as png, " +
                    "model.%s as xml, model.%s as txt, model.id as id, modtype.%s as type, users.%s as creator, " +
                    "model.%s as date FROM %s  model " +
                    "INNER JOIN %s  modtype on model.%s = modtype.id " +
                    "INNER JOIN %s as users on model.%s=users.id " +
                    "INNER JOIN %s as txt_mas on txt_mas.%s = model.id " +
                    "WHERE CAST(txt_mas.%s as DECIMAL(10,2)) >= ? - ? AND CAST(txt_mas.%s as DECIMAL(10,2))  <= ? + ? AND " +
                    "CAST(txt_mas.%s as DECIMAL(10,2)) >= ? - ? AND CAST(txt_mas.%s as DECIMAL(10,2)) <= ? + ? AND txt_mas.%s = ?",
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_FILES_MODEL_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_FILES_PATH_TO_PNG"),
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_FILES_PATH_TO_XML"),
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_FILES_PATH_TO_TXT"),
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_TYPE_TYPE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("USERS_LOGIN"),
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_FILES_CREATION_DATE"),
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_FILES_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_TYPE_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("FOREIGN_KEY_MODEL_TYPE"),
            PropertiesManager.getSqlTableProperties().getProperty("USERS_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("FOREIGN_KEY_USER"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_MASS_BALANCE_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("FOREIGN_KEY_MODEL_FILES"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_MASS_BALANCE_INPUT_1"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_MASS_BALANCE_INPUT_1"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_MASS_BALANCE_INPUT_2"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_MASS_BALANCE_INPUT_2"),
            PropertiesManager.getSqlTableProperties().getProperty("TXT_MASS_BALANCE_NAME"));
    protected final static String sqlSelectTypeModels = String.format("SELECT * FROM %s",
            PropertiesManager.getSqlTableProperties().getProperty("MODEL_TYPE_TABLE_NAME"));

    protected final static String sqlSelectUserData = String.format("SELECT user.%s as login, user.%s as password, user.id," +
                    "user.%s as access FROM %s user WHERE user.%s = ? AND user.%s = ?",
            PropertiesManager.getSqlTableProperties().getProperty("USERS_LOGIN"),
            PropertiesManager.getSqlTableProperties().getProperty("USERS_PASSWORD"),
            PropertiesManager.getSqlTableProperties().getProperty("USERS_ACCESS"),
            PropertiesManager.getSqlTableProperties().getProperty("USERS_TABLE_NAME"),
            PropertiesManager.getSqlTableProperties().getProperty("USERS_LOGIN"),
            PropertiesManager.getSqlTableProperties().getProperty("USERS_PASSWORD"));
}