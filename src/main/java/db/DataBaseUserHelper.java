package db;

import entity.User;

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
            User.getCurrentUser().setLogin(rs.getString("login"));
            User.getCurrentUser().setPassword(rs.getString("password"));
            User.getCurrentUser().setAccess(rs.getString("access"));
            User.getCurrentUser().setId(rs.getInt("id"));
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
