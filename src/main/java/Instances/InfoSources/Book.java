package Instances.InfoSources;

import java.io.*;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by Андрей on 01.03.2017.
 */
public class Book extends MainInfo {

    public Book(int bookID, String bookName, Timestamp bookDate, int bookSize, Blob blob) throws IOException {
     super(bookID, bookName, bookDate, bookSize, blob);

    }

    @Override
    public String toString() {
        return "Book{" +
                "bookID=" + instID +
                ", bookName='" + instName + '\'' +
                ", bookDate=" + instDate +
                ", bookSize=" + instSize +
                ", bookBLOB=" + instBLOB +
                '}';
    }
}
