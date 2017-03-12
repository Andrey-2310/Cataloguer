package Instances.InfoSources;

import java.io.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Андрей on 01.03.2017.
 */
public class Audio extends MainInfo {

    private final File audioFile= new File("audioFile.mp3");

    public Audio() {}

    public Audio(int audioID, String audioName, Timestamp audioDate, int audioSize, InputStream inputStream) throws IOException {
        this.instID = audioID;
        this.instName = audioName;
        this.instDate = audioDate;
        this.instSize = audioSize;
        FileOutputStream  outputStream=new FileOutputStream(audioFile);
        byte[] buffer=new byte[1024*1024*10];
        while(inputStream.read(buffer)>0)
            outputStream.write(buffer);
    }

    @Override
    public String toString() {
        return "Audio{" +
                "audioID=" + instID +
                ", audioName='" + instName + '\'' +
                ", audioDate=" + instDate +
                ", audioSize=" + instSize +
                ", audioFile=" + audioFile +
                '}';
    }
}
