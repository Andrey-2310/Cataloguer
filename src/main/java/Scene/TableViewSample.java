package Scene;

import javafx.application.Application;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


/**
 * Created by Андрей on 07.03.2017.
 */
public class TableViewSample {

    private TableView table = new TableView();

    public TableViewSample() {
        table.setEditable(true);
        TableColumn firstNameCol = new TableColumn("First Name");
    }
}
