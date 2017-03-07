package Scene;

import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.Scene;



/**
 * Created by Андрей on 25.02.2017.
 */
class EnterWindow {
   EnterWindow(Stage appStage){
       BorderPane enterLayout=new BorderPane();
       GridPane logLayout=new GridPane();
       enterLayout.setPadding(new Insets(20, 15, 20, 15));
       logLayout.setPadding(new Insets(20, 10, 20, 10));
       logLayout.setPrefWidth(enterLayout.getPrefWidth());
//logLayout.setStyle("-fx-background-color: #00FF00");
//enterLayout.setStyle("-fx-background-color: #0000FF");

       Label welcomeLabel=new Label("Welcome!");
       welcomeLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
       welcomeLabel.setPadding(new Insets(0, 70, 0, 80));
//welcomeLabel.setStyle("-fx-background-color: #FF0000");


       Button admin=new Button("Admin");
       Button user=new Button("User");
       Button guest=new Button("Guest");
       logLayout.addColumn(0, admin);
       logLayout.addColumn(1, user);
       logLayout.addColumn(2,guest);
       admin.setMinSize(70, 30);
       user.setMinSize(70, 30);
       guest.setMinSize(70, 30);
       logLayout.setHgap(20);
       enterLayout.setTop(welcomeLabel);
       enterLayout.setCenter(logLayout);
       admin.setOnAction((Event)->{
           AdminEnterWindow q=new AdminEnterWindow(appStage);
       } );
       user.setOnAction((Event)->{
           UserEnterWindow g=new UserEnterWindow(appStage);
       });
       guest.setOnAction((Event)->{
           GuestEnterWindow q=new GuestEnterWindow(appStage);
       });
      // logLayout.getChildren().addAll(admin, user, guest);
       appStage.setScene(new Scene(enterLayout, 300, 120));
       appStage.setMinHeight(120);
       appStage.setMinWidth(300);
    }
}
