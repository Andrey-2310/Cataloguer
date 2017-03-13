package Scene;

import ImplementationDAO.RolesImplDAO.AdminImplDAO;
import Instances.Roles.MainModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Created by Андрей on 25.02.2017.
 */

/**
 * class which illustrates EnterWindow for admin
 */
class AdminEnterWindow {

    /**
     * This method is constructor for EnterWindow for admin
     * @param adminStage - stage on which this method will create Scene
     */
    AdminEnterWindow(Stage adminStage) {

        Label passwordLabel = new Label("Enter password:");
        passwordLabel.setStyle("-fx-font-weight: bold;");
        TextField passwordField = new TextField();
        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle(" -fx-background-color: #d1d1d1");
        cancelButton.setOnAction((event) -> new EnterWindow(adminStage));
        Button okeyButton = new Button("Okey");
        okeyButton.setStyle(" -fx-background-color: #1e56ff; -fx-font-weight: bold;");
        okeyButton.setOnAction((event) -> {
            AdminImplDAO adminImplDAO = new AdminImplDAO();
            try {
                if (adminImplDAO.CheckAdminPassword(passwordField.getText())) {
                    MainModel mainModel = new MainModel(1, 1, adminImplDAO.GetAdminName());
                    new WorkWindow(adminStage);
                } else {
                    System.out.println("The password is incorrect");
                    passwordField.clear();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        adminStage.setTitle("Entering");
        GridPane adminLayout = new GridPane();
        Scene adminScene = new Scene(adminLayout, 300, 120);
        adminStage.setScene(adminScene);
        adminLayout.setPadding(new Insets(30, 20, 20, 20));
        adminLayout.setHgap(20);
        adminLayout.setVgap(10);
        HBox hBoxLayout = new HBox(10);
        hBoxLayout.setAlignment(Pos.BOTTOM_CENTER);

        hBoxLayout.getChildren().addAll(okeyButton, cancelButton);
        adminLayout.add(passwordLabel, 0, 0);
        adminLayout.add(passwordField, 1, 0);
        adminLayout.add(hBoxLayout, 1, 2);

    }
}



