package properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class PropertiesManager {

    private static Properties sqlTableProps;
    private static Properties configProps;

    public static Properties getSqlTableProperties() {
        if (sqlTableProps == null){
            sqlTableProps = new Properties();
            try {
                String pathToSqlTableProperties = "src/main/resources/propeties/mySqlTables.properties";
                sqlTableProps.load(new FileInputStream(new File(pathToSqlTableProperties).getAbsolutePath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sqlTableProps;
        }
        else return sqlTableProps;
    }

    public static Properties getConfigProperties() {
        if (configProps == null){
            configProps = new Properties();
            try {
                String pathToSqlTableProperties = "src/main/resources/propeties/config.properties";
                configProps.load(new FileInputStream(new File(pathToSqlTableProperties).getAbsolutePath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return configProps;
        }
        else return configProps;
    }
}

