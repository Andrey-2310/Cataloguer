package ImplementationDAO.RolesImplDAO;

import ImplementationDAO.SuperExtd;
import Instances.Roles.User;
import InterfacesDAO.RolesDAO.AdminDAO;
import Instances.Roles.Admin;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * Created by Андрей on 28.02.2017.
 */
public class AdminImplDAO extends SuperExtd implements AdminDAO {

    @Override
    public boolean CheckAdminPassword(String Password) throws SQLException {
        String query = "Select password from users where role=1";
        Statement statement = GetConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        StringBuffer stringBuffer = new StringBuffer(resultSet.getString("password"));
        stringBuffer.reverse();
        return Password.equals(String.valueOf(stringBuffer));
    }

    @Override
    public String GetAdminPassword() throws SQLException {
        String query = "Select name from users where role=1";
        Statement statement = GetConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        StringBuffer stringBuffer = new StringBuffer(resultSet.getString("name"));
        return String.valueOf(stringBuffer);
    }

    @Override
    public Vector<Text[]> GetAllUsers() throws SQLException, IOException {
        Vector<Text[]> users = new Vector<>();
        String query = "Select * from users";
        Statement statement = GetConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        for(int i=0; resultSet.next(); i++, resultSet.next()) {
            Text[] user = new Text[]{new Text(String.valueOf(i+1)),
                    new Text(String.valueOf(resultSet.getInt("ID"))),
                    new Text(resultSet.getString("name")),
                    new Text(resultSet.getString("password")),
                    new Text(resultSet.getString("email")),
                    new Text(String.valueOf(resultSet.getInt("role")))};
            users.add(user);
        }

        statement.close();
        resultSet.close();
        return users;
    }

}
