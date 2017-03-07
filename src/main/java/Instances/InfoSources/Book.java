package Instances.InfoSources;

import java.io.*;
import java.sql.Blob;
import java.sql.Date;

/**
 * Created by Андрей on 01.03.2017.
 */
public class Book extends MainInfo {


    public String getBookName() {
        return instName;
    }

    public Date getBookDate() {
        return instDate;
    }

    public int getBookSize() {
        return instSize;
    }

    public Blob getBookBLOB() {
        return instBLOB;
    }
    //  private final File bookFile= new File("bookFile.pdf");


    public Book() {
    }

    public Book(int bookID, String bookName, Date bookDate, int bookSize, InputStream inputStream) throws IOException {
        this.instID = bookID;
        this.instName = bookName;
        this.instDate = bookDate;
        this.instSize = bookSize;
      //  this.bookBLOB= bookBLOB;
      /*  FileOutputStream outputStream=new FileOutputStream(bookFile);
        byte[] buffer=new byte[1024*1024*10];
        while(inputStream.read(buffer)>0)
            outputStream.write(buffer);*/
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
