package InterfacesDAO.RolesDAO;
import Instances.Roles.Guest;

import java.sql.SQLException;

/**
 * Created by Андрей on 28.02.2017.
 */
public interface GuestDAO extends RoleDAO<Guest> {
    public boolean addGuest(String guestName) throws SQLException;
}
