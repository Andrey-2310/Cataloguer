package ImplementationDAO.InfoImplDAO;

import ImplementationDAO.SuperExtd;
import Instances.InfoSources.Audio;
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
public class DocImplDAO extends InfoImplDAO {


    @Override
    public Vector<Doc> GetItem() throws SQLException, IOException {
        Vector<Doc> docs = new Vector<Doc>();
        String query = "Select * from docs";

        // System.out.println(GetStatement().isClosed());
        Statement statement = GetConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        Date sqlDate = new Date(System.currentTimeMillis());
        while (resultSet.next()) {
            docs.addElement(new Doc(resultSet.getInt("docID"), resultSet.getString("docName"),
                    sqlDate, resultSet.getInt("docSize"), resultSet.getBinaryStream("docBLOB")));
        }
        return docs;
    }

    @Override
    public Vector<Doc> GetUserItem( String search) throws SQLException, IOException {
        Vector<Doc> docs = new Vector<Doc>();
        String query;
        if (search == null)
            query = "SELECT  docs.docID, docs.docName, docs.docSize, docslist.addingDT,docs.docBLOB FROM users," +
                    " docslist, docs where ? =trim(docslist.userID) AND docs.docID=docslist.docsID group by docName;";
        else query ="SELECT  docs.docID, docs.docName, docs.docSize, docslist.addingDT,docs.docBLOB FROM users," +
                " docslist, docs where ? =trim(docslist.userID) AND docs.docID=docslist.docsID AND Match(docs.docName)" +
                " AGAINST (?  IN Boolean Mode) group by docName;";
        PreparedStatement preparedStatement = GetConnection().prepareStatement(query);
        preparedStatement.setInt(1, MainModel.getId());
        if (search != null)
            preparedStatement.setString(2, search);
        final ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next())
            docs.addElement(new Doc(resultSet.getInt("docID"), resultSet.getString("docName"),
                    resultSet.getDate("addingDT"), resultSet.getInt("docSize"),
                    resultSet.getBinaryStream("docBLOB")));
        preparedStatement.close();

        return docs;
    }

    @Override
    public void SetItem(File itemFile) throws SQLException {
        String query0 = "SELECT * FROM docs WHERE docName=?;";
        String query1 = "INSERT INTO docs (docName, docSize, docBLOB) values(?, ?, ?);";
        String query2 = "SELECT docID FROM docs WHERE docName=?;";
        String query3 = "INSERT INTO docslist (docsID, userID) VALUES(?, ?);";
        PreparedStatement checkingDoc = GetConnection().prepareStatement(query0);
        PreparedStatement insertIntoDoc = GetConnection().prepareStatement(query1);
        PreparedStatement selectFromDoc = GetConnection().prepareStatement(query2);
        PreparedStatement insertIntoDocList = GetConnection().prepareStatement(query3);
        try (FileInputStream input = new FileInputStream(itemFile)) {
            checkingDoc.setString(1, itemFile.getName());
            ResultSet posibleFile = checkingDoc.executeQuery();
            if (!posibleFile.next()) {
                insertIntoDoc.setString(1, itemFile.getName());
                insertIntoDoc.setInt(2, (int) (itemFile.length() / 1024 / 1024 + 1));
                insertIntoDoc.setBinaryStream(3, input);
                insertIntoDoc.executeUpdate();
            }
            //Select Request from Doc table
            selectFromDoc.setString(1, itemFile.getName());
            ResultSet newDocID = selectFromDoc.executeQuery();
            //Insert into DocList table Request
            newDocID.next();
            insertIntoDocList.setInt(1, newDocID.getInt("docID"));
            insertIntoDocList.setInt(2, MainModel.getId());
            insertIntoDocList.executeUpdate();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("You close the window without adding a file");
        } finally {
            checkingDoc.close();
            insertIntoDoc.close();
            insertIntoDocList.close();
            selectFromDoc.close();
        }
    }

    @Override
    public Vector<Doc> ExtructItems() {
        MainInfo.setInstNum(1);
       return super.ExtructItems();
    }

}