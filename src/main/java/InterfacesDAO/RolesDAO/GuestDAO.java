package InterfacesDAO.RolesDAO;
import Instances.Roles.Guest;

import java.sql.SQLException;

/**
 * Created by Андрей on 28.02.2017.
 */

/**
 * * This interfaces declarates realization of methods that are connected to guest
 */
public interface GuestDAO {

    /**
     *  This method helps to add new guest
     * @param guestName - name of new guest
     * @return true if guest is added, false if not
     * @throws SQLException
     */
     boolean addGuest(String guestName) throws SQLException;
}
