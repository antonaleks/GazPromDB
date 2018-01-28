package utilities;

import properties.PropertiesManager;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnect {
    private static final String URL = "jdbc:mysql://" + PropertiesManager.getConfigProperties().getProperty("url") + PropertiesManager.getConfigProperties().getProperty("schema") + "?autoReconnect=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USER = PropertiesManager.getConfigProperties().getProperty("login");
    private static final String PASSWORD = PropertiesManager.getConfigProperties().getProperty("password");
    private Connection connection;

    public Connection connect() {
        connection = null;
        Driver driver;

        try   {
            driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
        }
        catch (SQLException e1) {
            System.out.println("Драйвер не зарегистрировался");
            return null;
        }
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (!connection.isClosed())
                System.out.println("Соединение установлено");
            return connection;
        }catch (SQLException ex) {
            System.err.println("Соединение не установлено");
            ex.printStackTrace(); // Понадобился, чтобы отловить исключения, скрытые выводом на экран предупреждения
            return connection;
        }
    }

    // disconnect database
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
