package Instances.InfoSources;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Андрей on 01.03.2017.
 */
public class Doc extends MainInfo {

    public Doc() {}

    public Doc(int docID, String docName, Timestamp docDate, int docSize, InputStream inputStream) {
        this.instID = docID;
        this.instName = docName;
        this.instSize = docSize;
        this.instDate=docDate;
        //
    }

    public String getDocName() {
        return instName;
    }

    public void setDocName(String docName) {
        this.instName = docName;
    }

    public Timestamp getDocDate() {
        return instDate;
    }

    public void setDocDate(Timestamp docDate) {
        this.instDate = docDate;
    }

    public int getDocSize() {
        return instSize;
    }

    public void setDocSize(int docSize) {
        this.instSize = docSize;
    }

    public Blob getDocBLOB() {
        return instBLOB;
    }

    public void setDocBLOB(Blob docBLOB) {
        this.instBLOB = docBLOB;
    }

    @Override
    public String toString() {
        return "Doc{" +
                "docID=" + instID +
                ", docName='" + instName + '\'' +
                ", docDate=" + instDate +
                ", docSize=" + instSize +
                ", docBLOB=" + instBLOB +
                '}';
    }
}
