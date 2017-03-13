package ImplementationDAO.RolesImplDAO;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Андрей on 13.03.2017.
 */
class AdminImplDAOTest {
    @Test
    void checkAdminPassword() throws SQLException {
        AdminImplDAO adminImplDAO=new AdminImplDAO();
assertEquals(true, adminImplDAO.CheckAdminPassword("andreyka"));
assertEquals(false, adminImplDAO.CheckAdminPassword("qwerty"));
    }

    @Test
    void getAdminName() throws SQLException {
        AdminImplDAO adminImplDAO=new AdminImplDAO();
assertEquals("Andrey", adminImplDAO.GetAdminName());
    }

}