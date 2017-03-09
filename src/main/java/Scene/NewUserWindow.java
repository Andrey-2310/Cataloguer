package Scene;

import ImplementationDAO.RolesImplDAO.UserImplDAO;
import Instances.Roles.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.SQLException;


/**
 * Created by Андрей on 03.03.2017.
 */
class NewUserWindow {
    NewUserWindow(Stage newUserStage) {
        GridPane newUserLayout =new GridPane();
        Scene newUserScene =new Scene(newUserLayout, 300, 150);
        newUserStage.setScene(newUserScene);
        HBox hBoxLayout=new HBox(10);

        Label emailLabel= new Label("Enter e-mail:");
        Label passwordLabel=new Label("Enter password:");
        Label nameLabel =new Label("Enter name");

        emailLabel.setStyle("-fx-font-weight: bold;");
        passwordLabel.setStyle("-fx-font-weight: bold;");
        nameLabel.setStyle("-fx-font-weight: bold;");

        TextField enterEmail=new TextField();
        TextField enterPassword=new TextField();
        TextField enterName=new TextField();

        Button okeyButton=new Button("  Ok  ");
        okeyButton.setStyle(" -fx-background-color: #1e56ff; -fx-font-weight: bold;");
        okeyButton.setOnAction((event)->{
            if(!enterEmail.getText().isEmpty() &&
                    !enterPassword.getText().isEmpty() && !enterName.getText().isEmpty()) {
                UserImplDAO userImplDAO=new UserImplDAO();
                try {
                    if(userImplDAO.addUser(enterName.getText(), enterPassword.getText(), enterEmail.getText())) {
                        User user = userImplDAO.CheckPasswordAndEmail(enterPassword.getText(), enterEmail.getText());
                        new WorkWindow(newUserStage);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        });

        Button cancelButton=new Button("Cancel");
        cancelButton.setStyle(" -fx-background-color: #d1d1d1");
        cancelButton.setOnAction((event)-> new UserEnterWindow(newUserStage));

        newUserLayout.setPadding(new Insets(15));
        hBoxLayout.getChildren().addAll(okeyButton, cancelButton);
       // newUserLayout.add(hBoxLayout, 1,2);
        newUserLayout.add(enterEmail, 1, 0);
        newUserLayout.add(emailLabel, 0, 0);
        newUserLayout.add(passwordLabel, 0,1);
        newUserLayout.add(enterPassword, 1, 1);
        newUserLayout.add(enterName, 1,2);
        newUserLayout.add(nameLabel,0, 2);
       // newUserLayout.add(okeyButton, 1, 3);
        newUserLayout.add(hBoxLayout, 1,3);
        newUserLayout.setVgap(10);
        newUserLayout.setHgap(10);

    }

}
