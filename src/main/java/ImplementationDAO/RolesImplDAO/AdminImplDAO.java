package ImplementationDAO.RolesImplDAO;

import ImplementationDAO.SuperExtd;
import InterfacesDAO.RolesDAO.AdminDAO;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * Created by Андрей on 28.02.2017.
 */
public class AdminImplDAO extends SuperExtd implements AdminDAO {

    @Override
    public boolean CheckAdminPassword(String Password) throws SQLException {
        String query = "Select password from users where role=1";
        Statement statement = GetConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        StringBuffer stringBuffer = new StringBuffer(resultSet.getString("password"));
        stringBuffer.reverse();
        return Password.equals(String.valueOf(stringBuffer));
    }

    @Override
    public String GetAdminName() throws SQLException {
        String query = "Select name from users where role=1";
        Statement statement = GetConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        StringBuffer stringBuffer = new StringBuffer(resultSet.getString("name"));
        return String.valueOf(stringBuffer);
    }

    @Override
    public Vector<Text[]> GetAllUsers() throws SQLException, IOException {
        Vector<Text[]> users = new Vector<>();
        String query = "Select * from users";
        Statement statement = GetConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        int i=0;
        while(resultSet.next()){
            Text[] user = new Text[]{new Text(String.valueOf(i++ +1)),
                    new Text(String.valueOf(resultSet.getInt("ID"))),
                    new Text(resultSet.getString("name")),
                    new Text(resultSet.getString("password")),
                    new Text(resultSet.getString("email")),
                    new Text(String.valueOf(resultSet.getInt("role"))),
                    new Text(String.valueOf(resultSet.getInt("place")))};
            users.add(user);
        }

        statement.close();
        resultSet.close();
        return users;
    }

    @Override
    public Vector<Text[]> SearchUsers(String criterion) throws SQLException {
        Vector<Text[]> users = new Vector<>();
        String query = "Select * from users where Match(users.name) Against(?  IN Boolean Mode);";
        PreparedStatement preparedStatement = GetConnection().prepareStatement(query);
        preparedStatement.setString(1, criterion);
        ResultSet resultSet = preparedStatement.executeQuery();
        int i=0;
        while(resultSet.next()){
            Text[] user = new Text[]{new Text(String.valueOf(i++ +1)),
                    new Text(String.valueOf(resultSet.getInt("ID"))),
                    new Text(resultSet.getString("name")),
                    new Text(resultSet.getString("password")),
                    new Text(resultSet.getString("email")),
                    new Text(String.valueOf(resultSet.getInt("role"))),
                    new Text(String.valueOf(resultSet.getInt("place")))};
            users.add(user);
        }

        preparedStatement.close();
        resultSet.close();
        return users;
    }

    @Override
    public void DeleteUser(String criterion) throws SQLException {
        String selectingQuery = "Select * from users where Match(users.name) Against(?  IN Boolean Mode);";
        String deletingFromBookslistQuery="Delete from bookslist where userID=?;";
        String deletingFromAudiolistQuery="Delete from audiolist where userID=?;";
        String deletingFromDocslistQuery="Delete from docslist where userID=?;";
        String deletingFromVideolistQuery="Delete from videolist where userID=?;";
        String deletingFromUsersQuery="Delete from users where Match(users.name) Against(?  IN Boolean Mode);";
        PreparedStatement selectingUsers = GetConnection().prepareStatement(selectingQuery);
        PreparedStatement deletingFromDocslist = GetConnection().prepareStatement(deletingFromDocslistQuery);
        PreparedStatement deletingFromBookslist = GetConnection().prepareStatement(deletingFromBookslistQuery);
        PreparedStatement deletingFromAudiolist = GetConnection().prepareStatement(deletingFromAudiolistQuery);
        PreparedStatement deletingFromVideolist = GetConnection().prepareStatement(deletingFromVideolistQuery);
        PreparedStatement deletingFromUsers=GetConnection().prepareStatement(deletingFromUsersQuery);
        selectingUsers.setString(1, criterion);
        deletingFromUsers.setString(1, criterion);
        ResultSet resultSet = selectingUsers.executeQuery();
        while(resultSet.next()){
            if(resultSet.getInt("role")==2) {
                deletingFromAudiolist.setInt(1, resultSet.getInt("ID"));
                deletingFromBookslist.setInt(1, resultSet.getInt("ID"));
                deletingFromDocslist.setInt(1, resultSet.getInt("ID"));
                deletingFromVideolist.setInt(1, resultSet.getInt("ID"));
                deletingFromAudiolist.executeUpdate();
                deletingFromBookslist.executeUpdate();
                deletingFromDocslist.executeUpdate();
                deletingFromVideolist.executeUpdate();
            }
        }
        deletingFromUsers.executeUpdate();
    }


}
