package InterfacesDAO.RolesDAO;

import Instances.Roles.User;

import java.sql.SQLException;

/**
 * Created by Андрей on 28.02.2017.
 */
public interface UserDAO extends RoleDAO<User> {
    public User CheckPasswordAndEmail(String Password, String Email) throws SQLException;
    public boolean addUser(String userName, String userPassword, String userEmail) throws SQLException;

}
