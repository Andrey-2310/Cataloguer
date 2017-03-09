package InterfacesDAO.RolesDAO;

import Instances.Roles.Admin;
import Instances.Roles.User;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by Андрей on 28.02.2017.
 */
public interface AdminDAO extends RoleDAO<Admin> {

    boolean CheckAdminPassword(String password) throws SQLException;
    String GetAdminPassword() throws SQLException;
    Vector<Text[]> GetAllUsers() throws SQLException, IOException;
}
