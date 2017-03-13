package Instances.InfoSources;

import java.io.*;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Андрей on 01.03.2017.
 */
public class Audio extends MainInfo {

    public Audio(int audioID, String audioName, Timestamp audioDate, int audioSize, Blob blob) throws IOException {
        super(audioID, audioName, audioDate, audioSize, blob);

    }

    @Override
    public String toString() {
        return "Audio{" +
                "audioID=" + instID +
                ", audioName='" + instName + '\'' +
                ", audioDate=" + instDate +
                ", audioSize=" + instSize +
                ", audioBLOB=" + instBLOB +
                '}';
    }
}