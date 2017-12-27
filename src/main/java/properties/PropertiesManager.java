package properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class PropertiesManager {

    private static Properties sqlTableProps;

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
}

