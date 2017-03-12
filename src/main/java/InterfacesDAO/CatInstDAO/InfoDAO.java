package InterfacesDAO.CatInstDAO;

import Instances.InfoSources.MainInfo;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by Андрей on 05.03.2017.
 */
public interface InfoDAO<Info extends MainInfo>{
    Vector<Info> GetItem() throws SQLException, IOException;
    Vector<Info> GetUserItem(String search) throws SQLException, IOException;
    boolean SetItem(File itemFile) throws SQLException;
    void DeleteItem(String criterion) throws IOException, SQLException;
    Vector<Info> ExtructItems();
}
