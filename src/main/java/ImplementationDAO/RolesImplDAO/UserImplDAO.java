package ImplementationDAO.RolesImplDAO;

import ImplementationDAO.SuperExtd;
import InterfacesDAO.RolesDAO.UserDAO;
import Instances.Roles.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Андрей on 28.02.2017.
 */
public class UserImplDAO extends SuperExtd implements UserDAO {


    @Override
    public void update(User model) {

    }

    @Override
    public User CheckPasswordAndEmail(String Password, String Email) throws SQLException {
        String query = "Select * from users where password=? AND email=?";
        PreparedStatement preparedStatement = GetConnection().prepareStatement(query);
        StringBuffer bufferPassword=new StringBuffer(Password);
        bufferPassword.reverse();
        preparedStatement.setString(1, String.valueOf(bufferPassword));
        preparedStatement.setString(2, Email);
        ResultSet resultSet = preparedStatement.executeQuery();
        // resultSet.next();
        if (!resultSet.next()) return null;
        else {
            bufferPassword.reverse();
            return new User(resultSet.getInt("ID"), resultSet.getInt("role"),
                    Email, resultSet.getString("name"),Password);
        }
    }

    @Override
    public boolean addUser(String userName, String userPassword, String userEmail) throws SQLException {
        String query = "Insert into users (email, name, password, role) values(?, ?, ?, 2)";
        PreparedStatement preparedStatement = GetConnection().prepareStatement(query);
        preparedStatement.setString(1, userEmail);
        preparedStatement.setString(2, userName);
        StringBuffer bufferPassword=new StringBuffer(userPassword);
        bufferPassword.reverse();
        preparedStatement.setString(3, String.valueOf(bufferPassword));
        if (preparedStatement.executeUpdate() == 1)
            return true;
        else return false;
    }
}
