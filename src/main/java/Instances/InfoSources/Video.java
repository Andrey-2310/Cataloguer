package Instances.InfoSources;

import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Андрей on 01.03.2017.
 */
public class Video extends MainInfo {

    public Video() {}

    public Video(int videoID, String videoName, Timestamp videoDate, int videoSize, InputStream inputStream) {
        this.instID=videoID;
        this.instSize=videoSize;
        this.instName=videoName;
        this.instDate=videoDate;
        //
    }


    @Override
    public String toString() {
        return "Video{" +
                "videoID=" + instID +
                ", videoName='" + instName + '\'' +
                ", videoDate=" + instDate +
                ", videoSize=" + instSize +
                ", videoBLOB=" + instBLOB +
                '}';
    }
}
