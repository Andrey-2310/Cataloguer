package ImplementationDAO.InfoImplDAO;

import ImplementationDAO.SuperExtd;
import Instances.InfoSources.Audio;
import Instances.InfoSources.Book;
import Instances.InfoSources.Doc;
import Instances.InfoSources.MainInfo;
import Instances.Roles.MainModel;
import InterfacesDAO.CatInstDAO.InfoDAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Vector;

/**
 * Created by Андрей on 01.03.2017.
 */
public class AudioImplDAO extends InfoImplDAO {

    @Override
    public Vector<Audio> GetItem() throws SQLException, IOException {
        Vector<Audio> audios = new Vector<Audio>();
        String query = "Select * from audio";

        // System.out.println(GetStatement().isClosed())
        Statement statement = GetConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        Date sqlDate = new Date(System.currentTimeMillis());
        while (resultSet.next()) {
            audios.addElement(new Audio(resultSet.getInt("audioID"), resultSet.getString("audioName"),
                    sqlDate, resultSet.getInt("audioSize"), resultSet.getBinaryStream("audioBLOB")));
        }
        return audios;
    }

    @Override
    public Vector<Instances.InfoSources.Audio> GetUserItem( String search) throws SQLException, IOException {
        Vector<Instances.InfoSources.Audio> audios = new Vector<>();
        String query;
        if (search == null)
            query = "SELECT  audio.audioID, audio.audioName, audio.audioSize, audiolist.addingDT, audio.audioBLOB FROM users," +
                    " audiolist, audio where ? =trim(audiolist.userID) AND audio.audioID=audiolist.audioID group by audioName;";
        else {
            query = "SELECT  audio.audioID, audio.audioName, audio.audioSize, audiolist.addingDT, audio.audioBLOB FROM users," +
                    " audiolist, audio where ? =trim(audiolist.userID) AND audio.audioID=audiolist.audioID AND " +
                    "Match(audio.audioName) Against(?  IN Boolean Mode) group by audioName;";
        }
        PreparedStatement preparedStatement = GetConnection().prepareStatement(query);
        preparedStatement.setInt(1, MainModel.getId());
        if (search != null)
            preparedStatement.setString(2, search);
        final ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next())
            audios.addElement(new Instances.InfoSources.Audio(resultSet.getInt("audioID"), resultSet.getString("audioName"),
                    resultSet.getDate("addingDT"), resultSet.getInt("audioSize"),
                    resultSet.getBinaryStream("audioBLOB")));
        preparedStatement.close();

        return audios;
    }

    @Override
    public void SetItem(File itemFile) throws SQLException {
        String query0 = "SELECT * FROM audio WHERE audioName=?;";
        String query1 = "INSERT INTO audio (audioName, audioSize, audioBLOB) values(?, ?, ?);";
        String query2 = "SELECT audioID FROM audio WHERE audioName=?;";
        String query3 = "INSERT INTO audiolist (audioID, userID) VALUES(?, ?);";
        PreparedStatement checkingAudio = GetConnection().prepareStatement(query0);
        PreparedStatement insertIntoAudio = GetConnection().prepareStatement(query1);
        PreparedStatement selectFromAudio = GetConnection().prepareStatement(query2);
        PreparedStatement insertIntoAudioList = GetConnection().prepareStatement(query3);
        try (FileInputStream input = new FileInputStream(itemFile)) {
            checkingAudio.setString(1, itemFile.getName());
            ResultSet posibleFile = checkingAudio.executeQuery();
            if (!posibleFile.next()) {
                insertIntoAudio.setString(1, itemFile.getName());
                insertIntoAudio.setInt(2, (int) (itemFile.length() / 1024 / 1024 + 1));
                insertIntoAudio.setBinaryStream(3, input);
                insertIntoAudio.executeUpdate();
            }
            //Select Request from Audio table
            selectFromAudio.setString(1, itemFile.getName());
            ResultSet newDocID = selectFromAudio.executeQuery();
            //Insert into AudioList table Request
            newDocID.next();
            insertIntoAudioList.setInt(1, newDocID.getInt("audioID"));
            insertIntoAudioList.setInt(2, MainModel.getId());
            insertIntoAudioList.executeUpdate();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("You close the window without adding a file");
        } finally {
            insertIntoAudio.close();
            insertIntoAudioList.close();
            selectFromAudio.close();
            checkingAudio.close();
        }
    }

    @Override
    public Vector<Audio> ExtructItems() {
        MainInfo.setInstNum(3);
        return super.ExtructItems();
    }
}
