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
class AdminEnterWindow {


    AdminEnterWindow(Stage adminStage){
        class CancelButton extends Button{
            private CancelButton(){
                super("Cancel");
                this.setStyle(" -fx-background-color: #d1d1d1");
                this.setOnAction((event)->new EnterWindow(adminStage));
            }
        }
        class OkeyButton extends Button{

            private OkeyButton(){
                super("Okey");
                this.setStyle(" -fx-background-color: #1e56ff; -fx-font-weight: bold;");
            }
        }
        adminStage.setTitle("Entering");
        GridPane adminLayout =new GridPane();
        Scene adminScene =new Scene(adminLayout, 300, 120);
        adminStage.setScene(adminScene);
        adminLayout.setPadding(new Insets(30, 20, 20, 20));
        adminLayout.setHgap(20);
        adminLayout.setVgap(10);
        HBox hBoxLayout=new HBox(10);
        hBoxLayout.setAlignment(Pos.BOTTOM_CENTER);

        Label passwordLabel=new Label("Enter password:");
        passwordLabel.setStyle("-fx-font-weight: bold;");

        TextField passwordField=new TextField();
        OkeyButton okeyButton=new OkeyButton();

        okeyButton.setOnAction((event) -> {
            AdminImplDAO adminImplDAO=new AdminImplDAO();
            try {
                if(adminImplDAO.CheckAdminPassword(passwordField.getText())) {
                    MainModel mainModel=new MainModel(1, 1, adminImplDAO.GetAdminPassword());
                    new WorkWindow(adminStage);
                }
                else {
                    System.out.println("Sorry, you're from plant");
                    passwordField.clear();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        CancelButton cancelButton=new CancelButton();

        hBoxLayout.getChildren().addAll(okeyButton, cancelButton);
        adminLayout.add(passwordLabel, 0,0);
        adminLayout.add(passwordField,1, 0);
        adminLayout.add(hBoxLayout, 1,2);

    }
}



