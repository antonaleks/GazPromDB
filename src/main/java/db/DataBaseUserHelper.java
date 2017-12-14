package db;

import entity.Data;
import entity.User;
import properties.PropertiesManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseUserHelper extends DataBaseHelper {

    public boolean loginGlobalUser(String login, String password) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(SqlQueryHelper.sqlSelectUserData);
        statement.setString(1, login);
        statement.setString(2, password);
        ResultSet rs = statement.executeQuery();
        if(rs.next()) {
            User.getInstance().setLogin(rs.getString("login"));
            User.getInstance().setPassword(rs.getString("password"));
            User.getInstance().setAccess(rs.getString("access"));
            User.getInstance().setId(rs.getInt("id"));
            statement.close();
            closeAll();
            return true;
        }
        else{
            statement.close();
            closeAll();
            return false;
        }
    }
}
