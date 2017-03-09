package InterfacesDAO.RolesDAO;

import Instances.Roles.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by Андрей on 28.02.2017.
 */
public interface UserDAO extends RoleDAO<User> {
    User CheckPasswordAndEmail(String Password, String Email) throws SQLException;
    boolean addUser(String userName, String userPassword, String userEmail) throws SQLException;
}
