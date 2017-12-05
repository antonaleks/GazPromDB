package db;

import utilities.MySqlConnect;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Этот класс надо переделать, как только появятся контроллеры
 *
 * */
public class DataBaseHelper {
    protected MySqlConnect mySqlConnect;
    protected Connection conn;

    public DataBaseHelper() {
        mySqlConnect = new MySqlConnect();
        conn = mySqlConnect.connect();
    }

    protected void closeAll() throws SQLException {
        conn.close();
        mySqlConnect.disconnect();
    }
}
