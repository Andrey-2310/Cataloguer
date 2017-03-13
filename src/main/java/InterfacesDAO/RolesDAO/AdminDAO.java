package InterfacesDAO.RolesDAO;

import Instances.Roles.Admin;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by Андрей on 28.02.2017.
 */

/**
 *  This interfaces declarates realization of methods that are connected to admin
 */
public interface AdminDAO  {

    /**
     * This method helps to check admin password
     * @param password - method checks if this param equals to admin password
     * @return true if passwords matches, false if not
     * @throws SQLException
     */
    boolean CheckAdminPassword(String password) throws SQLException;


    /**
     *  This method gets admin name
     * @return admin password
     * @throws SQLException
     */
    String GetAdminName() throws SQLException;

    /**
     * This method shows all users that visited this app and all data about them
     * @return  Vector<Text[]> where each element of vector - array of Text
     * which consists of transformed data about users
     * @throws SQLException
     * @throws IOException
     */
    Vector<Text[]> GetAllUsers() throws SQLException, IOException;


    /**
     *  This method shows all users that visited this app with chosen by admin criterioт
     * @param criterion - this criterion is chosen by admin
     * @returnVector<Text[]> where each element of vector - array of Text
     * which consists of transformed data about users
     * @throws SQLException
     */
    Vector<Text[]> SearchUsers(String criterion) throws SQLException;
    void DeleteUser(String criterion) throws SQLException;
}
