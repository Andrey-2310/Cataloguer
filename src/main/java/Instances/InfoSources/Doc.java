package Instances.InfoSources;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Андрей on 01.03.2017.
 */
public class Doc extends MainInfo {

    public Doc(int docID, String docName, Timestamp docDate, int docSize, Blob instBlob) {
        super(docID,docName,docDate,docSize,instBlob);

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
