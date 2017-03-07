package ImplementationDAO.RolesImplDAO;

import ImplementationDAO.SuperExtd;
import InterfacesDAO.RolesDAO.GuestDAO;
import Instances.Roles.Guest;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Андрей on 28.02.2017.
 */
public class GuestImplDAO extends SuperExtd implements GuestDAO{

    @Override
    public void update(Guest model) {

    }
    @Override
    public boolean addGuest(String guestName) throws SQLException {
        String query = "Insert into users ( name, role) values(?, 3)";
        PreparedStatement preparedStatement = GetConnection().prepareStatement(query);
        preparedStatement.setString(1, guestName);
        if (preparedStatement.executeUpdate() == 1)
            return true;
        else return false;
    }
}
