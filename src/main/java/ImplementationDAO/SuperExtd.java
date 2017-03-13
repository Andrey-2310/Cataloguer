package ImplementationDAO;

import Instances.InfoSources.MainInfo;
import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;

/**
 * Created by Андрей on 28.02.2017.
 */


/**
 * This class collects all methods that are required during the work of app
 */
public class SuperExtd {
    private static Connection connection;
    private static final String UserName = "root";
    private static final String Password = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/cataloguer?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimeCode=false&serverTimezone=UTC";

    /**
     * This method gets connection of the DB
     * @return Connection
     */
    protected Connection GetConnection() {
        try {
            Driver myDriver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(myDriver);
            connection = DriverManager.getConnection(URL, UserName, Password);
            //  statement=connection.createStatement();
        } catch (SQLException e) {
            System.err.println("Что-то пошло не так");
        }
        return connection;
    }

    /**
     * This method chooses the file which user wants to add
     * @param fileChooseStage - stage for window, in which user will choose the file
     * @return File -file for adding
     * @throws NullPointerException
     */
    public static File ChooseFile(Stage fileChooseStage) throws NullPointerException {
        File file = null;
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose File");
            fileChooser.setInitialDirectory(new File("C:\\Users\\Андрей\\Downloads"));
            switch (MainInfo.getInstNum()) {

            case 1:
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Doc", "*.txt", "*.doc", "*.docx"));
            case 2:
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Book", "*.pdf", "*.DjVu"));
            case 3:
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio", "*.mp3", "*.mid"));
            case 4:
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Video", "*.mp4", "*.avi", "*.mov"));
            }
                /* if(item.equals("All"))fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Doc", "*.txt", "*.doc", "*.docx"),
                    new FileChooser.ExtensionFilter("Book", "*.pdf", "*.DjVu"),
                    new FileChooser.ExtensionFilter("Audio", "*.mp3", "*.mid"),
                    new FileChooser.ExtensionFilter("Video", "*.mp4", "*.avi", "*.mov"));*/
            file = fileChooser.showOpenDialog(fileChooseStage);
            System.out.println(file.getName() + " " + (file.length() / 1024 / 1024 + 1));
        } catch (NullPointerException ex) {
            System.out.println("You close the window without adding a file");
        }
        return file;
    }

    /**
     * This method helps to open file from directory
     * @param fileName - name of file, which will be opened
     * @return true if file opens, false if not
     * @throws IOException
     */
    public static boolean OpenFile(String fileName) throws IOException {
        //text file, should be opening in default text editor
        if(fileName==null) return false;
        File file = new File(fileName);

        //first check if Desktop is supported by Platform or not
        if (!Desktop.isDesktopSupported()) {
            System.out.println("Desktop is not supported");
            return false;
        }
        Desktop desktop = Desktop.getDesktop();
        if (file.exists()) {
            desktop.open(file);
            return true;
        } else {
            System.out.println("File doesn't exist, let's save it");
            return false;
        }
    }
}
