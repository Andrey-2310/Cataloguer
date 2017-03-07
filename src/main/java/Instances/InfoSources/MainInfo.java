package Instances.InfoSources;

import java.sql.Blob;
import java.sql.Date;

/**
 * Created by Андрей on 05.03.2017.
 */
public  class MainInfo {
    int instID;
    String instName;
    Date instDate;
    int instSize;
    Blob instBLOB;
    protected static int instNum;
    public int getInstID() {
        return instID;
    }

    public static int getInstNum() {
        return instNum;
    }

    public static void setInstNum(int instNum) {
        MainInfo.instNum = instNum;
    }

    public String getInstName() {
        return instName;
    }

    public Date getInstDate() {
        return instDate;
    }

    public int getInstSize() {
        return instSize;
    }

    public Blob getInstBLOB() {
        return instBLOB;
    }
}