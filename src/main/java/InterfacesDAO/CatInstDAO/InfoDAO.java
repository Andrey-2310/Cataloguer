package InterfacesDAO.CatInstDAO;

import Instances.InfoSources.MainInfo;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by Андрей on 05.03.2017.
 */

/**
 * This interface describes methods which work with data from DB
 * @param <Info> - general param, it can be Book, Doc, Music, Video
 */
public interface InfoDAO<Info extends MainInfo>{
    /**
     *This method gets all items  of one category which users added to their accounts
     * @return vector of items
     * @throws SQLException
     * @throws IOException
     */
    Vector<Info> GetItem() throws SQLException, IOException;

    /**
     *This method gets all items  of one category of one user from
     * @param search
     * @return vector of items
     * @throws SQLException
     * @throws IOException
     */
    Vector<Info> GetUserItem(String search) throws SQLException, IOException;

    /**
     * This method adds new item to the user catalog from file, checks if there is required place
     * @param itemFile - file that this method is going to add
     * @return true if item is added, false if not
     * @throws SQLException
     */
    boolean SetItem(File itemFile) throws SQLException;

    /**
     * This method deletes all items from user cataloguer that suit chosen criterion
     * @param criterion - criterion of deleting
     * @throws IOException
     * @throws SQLException
     */
    void DeleteItem(String criterion) throws IOException, SQLException;

    /**
     * Extructs all items that are needed in the current moment
     * it depends on the kind of role
     * @return vector of items
     */
    Vector<Info> ExtructItems();

    /**
     * This method search the file that user decided to open
     * @param number -number of item in table, which user would like to open
     * @throws IOException
     * @throws SQLException
     * @throws InterruptedException
     */
    void OpenItem(int number) throws IOException, SQLException, InterruptedException;
}
