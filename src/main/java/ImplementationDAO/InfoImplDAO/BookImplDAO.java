package ImplementationDAO.InfoImplDAO;

import Instances.InfoSources.Book;
import ImplementationDAO.SuperExtd;
import Instances.InfoSources.Doc;
import Instances.InfoSources.MainInfo;
import Instances.Roles.MainModel;
import InterfacesDAO.CatInstDAO.InfoDAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.util.*;


/**
 * Created by Андрей on 01.03.2017.
 */
public class BookImplDAO extends InfoImplDAO {

    @Override
    public Vector<Book> GetItem() throws SQLException, IOException {
        Vector<Book> books = new Vector<Book>();
        String query = "Select * from books";
        Statement statement = GetConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        Date sqlDate = new Date(System.currentTimeMillis());
        while (resultSet.next()) {
            books.addElement(new Book(resultSet.getInt("bookID"), resultSet.getString("bookName"),
                    sqlDate, resultSet.getInt("bookSize"),
                    resultSet.getBinaryStream("bookBLOB")));
        }
        statement.close();
        resultSet.close();
        return books;
    }

    @Override
    public Vector<Book> GetUserItem( String search) throws SQLException, IOException {
        Vector<Book> books = new Vector<Book>();
        String query;
        if (search == null)
            query = "SELECT  books.bookID, books.bookName, books.bookSize, bookslist.addingDT, books.bookBLOB FROM users," +
                    " bookslist, books where ? =trim(bookslist.userID) AND books.bookID=bookslist.bookID group by bookName;";
        else
            query = "SELECT  books.bookID, books.bookName, books.bookSize, bookslist.addingDT, books.bookBLOB FROM users," +
                    " bookslist, books where ? =trim(bookslist.userID) AND books.bookID=bookslist.bookID " +
                    " AND MATCH(books.bookName) AGAINST(?  IN Boolean Mode) group by bookName;";
        PreparedStatement preparedStatement = GetConnection().prepareStatement(query);
        preparedStatement.setInt(1, MainModel.getId());
        if (search != null)
            preparedStatement.setString(2, search);
        final ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next())
            books.addElement(new Book(resultSet.getInt("bookID"), resultSet.getString("bookName"),
                    resultSet.getDate("addingDT"), resultSet.getInt("bookSize"),
                    resultSet.getBinaryStream("bookBLOB")));
        preparedStatement.close();

        return books;
    }

    @Override
    public void SetItem(File itemFile) throws SQLException {
        String query0 = "SELECT * FROM books WHERE bookName=?;";
        String query1 = "INSERT INTO books (bookName, bookSize, bookBLOB) values(?, ?, ?);";
        String query2 = "SELECT bookID FROM books WHERE bookName=?;";
        String query3 = "INSERT INTO bookslist (bookID, userID) VALUES(?, ?);";
        PreparedStatement checkingBook = GetConnection().prepareStatement(query0);
        PreparedStatement insertIntoBook = GetConnection().prepareStatement(query1);
        PreparedStatement selectFromBook = GetConnection().prepareStatement(query2);
        PreparedStatement insertIntoBooksList = GetConnection().prepareStatement(query3);
        try (FileInputStream input = new FileInputStream(itemFile)) {
            checkingBook.setString(1, itemFile.getName());
            ResultSet posibleFile = checkingBook.executeQuery();
            if (!posibleFile.next()) {
                insertIntoBook.setString(1, itemFile.getName());
                insertIntoBook.setInt(2, (int) (itemFile.length() / 1024 / 1024 + 1));
                insertIntoBook.setBinaryStream(3, input);
                insertIntoBook.executeUpdate();
            }
            //Select Request from Video table
            selectFromBook.setString(1, itemFile.getName());
            ResultSet newVideoID = selectFromBook.executeQuery();
            //Insert into VideoList table Request
            newVideoID.next();
            insertIntoBooksList.setInt(1, newVideoID.getInt("bookID"));
            insertIntoBooksList.setInt(2, MainModel.getId());
            insertIntoBooksList.executeUpdate();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("You close the window without adding a file");
        } finally {
            checkingBook.close();
            insertIntoBook.close();
            insertIntoBooksList.close();
            selectFromBook.close();
        }
    }

    @Override
    public Vector<Book> ExtructItems() {
        MainInfo.setInstNum(2);
        return super.ExtructItems();
    }
}
