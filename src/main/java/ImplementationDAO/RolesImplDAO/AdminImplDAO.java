package ImplementationDAO.RolesImplDAO;

import ImplementationDAO.SuperExtd;
import InterfacesDAO.RolesDAO.AdminDAO;
import Instances.Roles.Admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Андрей on 28.02.2017.
 */
public class AdminImplDAO extends SuperExtd implements AdminDAO {
    @Override
    public void update(Admin model) {

    }

    @Override
    public boolean CheckAdminPassword(String Password) throws SQLException {
        String query="Select password from users where role=1";
        Statement statement=GetConnection().createStatement();
        ResultSet resultSet=statement.executeQuery(query);
        resultSet.next();
        StringBuffer stringBuffer=new StringBuffer(resultSet.getString("password"));
        stringBuffer.reverse();
        return Password.equals(String.valueOf(stringBuffer));
    }

    @Override
    public String GetAdminPassword() throws SQLException {
        String query="Select name from users where role=1";
        Statement statement=GetConnection().createStatement();
        ResultSet resultSet=statement.executeQuery(query);
        resultSet.next();
        StringBuffer stringBuffer=new StringBuffer(resultSet.getString("name"));
        return String.valueOf(stringBuffer);
    }
}
