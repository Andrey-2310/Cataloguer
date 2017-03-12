package ImplementationDAO.InfoImplDAO;

import ImplementationDAO.SuperExtd;
import Instances.InfoSources.Audio;
import Instances.InfoSources.MainInfo;
import Instances.Roles.MainModel;
import InterfacesDAO.CatInstDAO.InfoDAO;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public  boolean SetItem(File itemFile) throws SQLException{
        if ((int) (itemFile.length() / 1024 / 1024 + 1) > 10) {
            System.out.println("File is too big");
            return false;
        }
        String checkQuery = "Select place from users where ID=?;";
        PreparedStatement checkPlace = GetConnection().prepareStatement(checkQuery);
        checkPlace.setInt(1, MainModel.getId());
        ResultSet checkingUser = checkPlace.executeQuery();
        if (checkingUser.next()) {
            if(checkingUser.getInt(1)<(int) (itemFile.length() / 1024 / 1024 + 1)){
                System.out.println("You have"+ checkingUser.getInt(1)+" MB left, wait for tomorrow");
                return false;
            }
            String updateUserQuery="Update users set place=? where ID=?;";
            PreparedStatement updateUser=GetConnection().prepareStatement(updateUserQuery);
            updateUser.setInt(1, checkingUser.getInt(1)
                    -(int) (itemFile.length() / 1024 / 1024 + 1));
            updateUser.setInt(2, MainModel.getId());
            updateUser.executeUpdate();
        }
        return true;
    }

    @Override
    public  abstract void DeleteItem(String criterion) throws IOException, SQLException;

    @Override
    public Vector<T> ExtructItems() {
        Vector<T> itemsToShow = new Vector<>();
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