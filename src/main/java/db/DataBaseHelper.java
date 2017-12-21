package db;

import utilities.MySqlConnect;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 *
 * */
class DataBaseHelper {
    MySqlConnect mySqlConnect;
    Connection conn;

    DataBaseHelper() {
        mySqlConnect = new MySqlConnect();
        conn = mySqlConnect.connect();
    }

    void closeAll() throws SQLException {
        conn.close();
        mySqlConnect.disconnect();
    }
}
