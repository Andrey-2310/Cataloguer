import ImplementationDAO.InfoImplDAO.BookImplDAO;
import ImplementationDAO.InfoImplDAO.DocImplDAO;
import Instances.InfoSources.Book;

import java.io.IOException;
import java.sql.*;
import java.util.Vector;

/**
 * Created by Андрей on 23.02.2017.
 */
public class Main {
    public static void main(String[] args) throws SQLException, IOException {
            DocImplDAO DI_DAO=new DocImplDAO();
           // Vector<Doc> catalVideos=DI_DAO.getDocs();
            BookImplDAO BI_DAO= new BookImplDAO();
            Vector<Book> books=BI_DAO.GetItem();
            for(int i=0; i<books.size(); i++) {
                System.out.println(books.get(i).toString());
            }

    }
}
