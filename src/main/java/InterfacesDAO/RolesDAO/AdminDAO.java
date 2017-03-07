package InterfacesDAO.RolesDAO;

import Instances.Roles.Admin;

import java.sql.SQLException;

/**
 * Created by Андрей on 28.02.2017.
 */
public interface AdminDAO extends RoleDAO<Admin> {

    public boolean CheckAdminPassword(String password) throws SQLException;
    public String GetAdminPassword() throws SQLException;
}
