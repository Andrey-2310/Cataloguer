package ImplementationDAO.InfoImplDAO;

import ImplementationDAO.SuperExtd;
import Instances.InfoSources.Audio;
import Instances.InfoSources.MainInfo;
import Instances.Roles.MainModel;
import InterfacesDAO.CatInstDAO.InfoDAO;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by Андрей on 07.03.2017.
 */
public abstract class InfoImplDAO<T extends MainInfo> extends SuperExtd implements InfoDAO<T> {
    @Override
    public abstract Vector<T> GetItem() throws SQLException, IOException;

    @Override
    public abstract Vector<T> GetUserItem(String search) throws SQLException, IOException;

    @Override
    public abstract void SetItem(File itemFile) throws SQLException;

    @Override
    public Vector<T> ExtructItems() {
        Vector<T> itemsToShow = new Vector<T>();
        try {
            switch (MainModel.getRole()) {
            case (2):
                itemsToShow = GetUserItem(null);
                break;
            case (1):
                if (MainModel.getId() == 1)
                    itemsToShow = GetItem();
                else itemsToShow = GetUserItem(null);
                break;
            default:
                itemsToShow = GetItem();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return itemsToShow;
    }
}