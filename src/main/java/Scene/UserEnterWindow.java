package Scene;

import ImplementationDAO.RolesImplDAO.UserImplDAO;
import Instances.Roles.MainModel;
import Instances.Roles.User;
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
class UserEnterWindow {
    UserEnterWindow(Stage userStage) {
        userStage.setTitle("Entering");
        GridPane userLayout = new GridPane();
        Scene userScene = new Scene(userLayout, 300, 120);
        userStage.setScene(userScene);
        HBox hBoxLayout = new HBox(10);
        hBoxLayout.setAlignment(Pos.BOTTOM_CENTER);
        Label emailLabel = new Label("Enter e-mail:");
        Label passwordLabel = new Label("Enter password:");

        emailLabel.setStyle("-fx-font-weight: bold;");
        passwordLabel.setStyle("-fx-font-weight: bold;");

        TextField enterEmail = new TextField();
        TextField enterPassword = new TextField();

        Button okeyButton = new Button("  Ok  ");
        okeyButton.setStyle(" -fx-background-color: #1e56ff; -fx-font-weight: bold;");
        okeyButton.setOnAction((event) -> {
            UserImplDAO userImplDAO = new UserImplDAO();
            try {
                User user = userImplDAO.CheckPasswordAndEmail(enterPassword.getText(), enterEmail.getText());
                if (user == null) {
                    System.out.println("Incorrect Email or Password");
                    enterEmail.clear();
                    enterPassword.clear();
                }
                else {

                    new WorkWindow(userStage);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // okeyButton.setBorder("-fx-border:10;");
        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle(" -fx-background-color: #d1d1d1");
        cancelButton.setOnAction((event) -> {
            MainModel.setRole(2);
            new EnterWindow(userStage);
        });

        Button newButton = new Button("New user");
        newButton.setStyle(" -fx-background-color: #d1d1d1");
        newButton.setOnAction((event) -> new NewUserWindow(userStage));
        hBoxLayout.getChildren().addAll(okeyButton, cancelButton);
        userLayout.add(hBoxLayout, 1, 2);
        userLayout.add(enterEmail, 1, 0);
        userLayout.add(emailLabel, 0, 0);
        userLayout.add(passwordLabel, 0, 1);
        userLayout.add(enterPassword, 1, 1);
        userLayout.add(newButton, 0, 2);
        //   userLayout.add(okeyButton,0,2);
        //   userLayout.add(cancelButton, 1, 2);
        userLayout.setVgap(10);
        userLayout.setHgap(15);
        userLayout.setPadding(new Insets(15, 10, 20, 10));
        //  guestStage.setMinHeight(120);
        //  System.out.println(guestLayout.);
        userStage.show();
    }
}
