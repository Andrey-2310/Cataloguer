package ImplementationDAO.InfoImplDAO;

import ImplementationDAO.SuperExtd;
import Instances.InfoSources.Audio;
import Instances.InfoSources.Doc;
import Instances.InfoSources.MainInfo;
import Instances.InfoSources.Video;
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
public class VideoImplDAO extends InfoImplDAO {

    @Override
    public Vector<Video> GetItem() throws SQLException, IOException {
        Vector<Video> videos = new Vector<Video>();
        String query = "Select * from video";

        // System.out.println(GetStatement().isClosed());
        Statement statement = GetConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        Date sqlDate = new Date(System.currentTimeMillis());
        while (resultSet.next()) {
            videos.addElement(new Video(resultSet.getInt("videoID"), resultSet.getString("videoName"),
                    sqlDate, resultSet.getInt("videoSize"), resultSet.getBinaryStream("videoBLOB")));
        }
        return videos;
    }

    @Override
    public Vector<Video> GetUserItem( String search) throws SQLException, IOException {
        Vector<Video> videos = new Vector<Video>();
        String query;
        if (search == null)
            query = "SELECT  video.videoID, video.videoName, video.videoSize, videolist.addingDT, video.videoBLOB FROM users," +
                    " videolist, video where ? =trim(videolist.userID) AND video.videoID=videolist.videoID" +
                    "  group by videoName;";
        else
            query = "SELECT  video.videoID, video.videoName, video.videoSize, videolist.addingDT, video.videoBLOB FROM users," +
                    " videolist, video where ? =trim(videolist.userID) AND video.videoID=videolist.videoID" +
                    " And Match(videoName) Against (? IN Boolean Mode) group by videoName;";
        PreparedStatement preparedStatement = GetConnection().prepareStatement(query);
        preparedStatement.setInt(1, MainModel.getId());
        if (search != null)
            preparedStatement.setString(2, search);
        final ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next())
            videos.addElement(new Video(resultSet.getInt("videoID"), resultSet.getString("videoName"),
                    resultSet.getDate("addingDT"), resultSet.getInt("videoSize"),
                    resultSet.getBinaryStream("videoBLOB")));
        preparedStatement.close();

        return videos;
    }

    @Override
    public void SetItem(File itemFile) throws SQLException {
        String query0 = "SELECT * FROM video WHERE videoName=?;";
        String query1 = "INSERT INTO video (videoName, videoSize, videoBLOB) VALUES(?, ?, ?);";
        String query2 = "SELECT videoID FROM video WHERE videoName=?;";
        String query3 = "INSERT INTO videolist (videoID, userID) VALUES(?, ?);";
        PreparedStatement checkingVideo = GetConnection().prepareStatement(query0);
        PreparedStatement insertIntoVideo = GetConnection().prepareStatement(query1);
        PreparedStatement selectFromVideo = GetConnection().prepareStatement(query2);
        PreparedStatement insertIntoVideoList = GetConnection().prepareStatement(query3);

        try (FileInputStream input = new FileInputStream(itemFile)) {
            //Checking Existing in table Video
            checkingVideo.setString(1, itemFile.getName());
            ResultSet posibleFile = checkingVideo.executeQuery();
            if (!posibleFile.next()) {
                //Insert into Video table Request
                insertIntoVideo.setString(1, itemFile.getName());
                insertIntoVideo.setInt(2, (int) (itemFile.length() / 1024 / 1024 + 1));
                insertIntoVideo.setBinaryStream(3, input);
                insertIntoVideo.executeUpdate();
            }
            //Select Request from Video table
            selectFromVideo.setString(1, itemFile.getName());
            ResultSet newVideoID = selectFromVideo.executeQuery();
            //Insert into VideoList table Request
            newVideoID.next();
            insertIntoVideoList.setInt(1, newVideoID.getInt("videoID"));
            insertIntoVideoList.setInt(2, MainModel.getId());
            insertIntoVideoList.executeUpdate();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("You close the window without adding a file");
        } finally {
            checkingVideo.close();
            insertIntoVideo.close();
            insertIntoVideoList.close();
            selectFromVideo.close();
        }
    }
    @Override
    public Vector<Doc> ExtructItems() {
        MainInfo.setInstNum(4);
        return super.ExtructItems();
    }
}
