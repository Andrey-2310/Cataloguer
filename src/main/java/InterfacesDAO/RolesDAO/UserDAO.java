package InterfacesDAO.RolesDAO;

import Instances.Roles.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by Андрей on 28.02.2017.
 */

/**
 * This interfaces declarates realization of methods that are connected to user
 */
public interface UserDAO  {


    /**
     * This method checks if there is user with such email and password
     * @param Password - password that this method is going to check
     * @param Email - email that this method is going to check
     * @return User if such user exists, null when there is no sych user
     * @throws SQLException
     */
    User CheckPasswordAndEmail(String Password, String Email) throws SQLException;

    /**
     * This method helps to add new user
     * @param userName - name of new user
     * @param userPassword - password of new user
     * @param userEmail - email of new user
     * @return true if user is added, false if not
     * @throws SQLException
     */
    boolean addUser(String userName, String userPassword, String userEmail) throws SQLException;
}
