package ImplementationDAO.InfoImplDAO;

import Instances.InfoSources.Audio;
import Instances.InfoSources.MainInfo;
import Instances.Roles.MainModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Vector;

/**
 * Created by Андрей on 01.03.2017.
 */
public class AudioImplDAO extends InfoImplDAO {

    @Override
    public Vector<Audio> GetItem() throws SQLException, IOException {
        Vector<Audio> audios = new Vector<>();
        String query = "Select * from audio";

        // System.out.println(GetStatement().isClosed())
        Statement statement = GetConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        Timestamp sqlTimestamp = new Timestamp(System.currentTimeMillis());
        String s = new SimpleDateFormat("MM/dd/yyyy-mm-dd hh:mm:ss").format(sqlTimestamp);
        System.out.println(s);
        while (resultSet.next()) {
            audios.addElement(new Audio(resultSet.getInt("audioID"), resultSet.getString("audioName"),
                    sqlTimestamp, resultSet.getInt("audioSize"), resultSet.getBinaryStream("audioBLOB")));
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
                    resultSet.getTimestamp("addingDT"), resultSet.getInt("audioSize"),
                    resultSet.getBinaryStream("audioBLOB")));
        preparedStatement.close();

        return audios;
    }

    @Override
    public boolean SetItem(File itemFile) throws SQLException {
        if(!super.SetItem(itemFile)) return false;
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
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException e) {
            System.out.println("You close the window without adding a file");
            return false;
        } finally {
            insertIntoAudio.close();
            insertIntoAudioList.close();
            selectFromAudio.close();
            checkingAudio.close();
        }
        return true;
    }

    @Override
    public void DeleteItem(String criterion) throws IOException, SQLException {
        Vector<Instances.InfoSources.Audio> audios=GetUserItem(criterion);
        String query="Delete  from audiolist where audioID=? and userID=?;";
        PreparedStatement preparedStatement = GetConnection().prepareStatement(query);
        preparedStatement.setInt(2,MainModel.getId());
        for(Instances.InfoSources.Audio audio: audios) {
            preparedStatement.setInt(1, audio.getInstID());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public Vector<Audio> ExtructItems() {
        MainInfo.setInstNum(3);
        return super.ExtructItems();
    }
}
