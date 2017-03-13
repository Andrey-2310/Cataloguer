package Scene;

import ImplementationDAO.RolesImplDAO.GuestImplDAO;
import Instances.Roles.Guest;
import Instances.Roles.MainModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;


/**
 * Created by Андрей on 25.02.2017.
 */

/**
 * This class represents Enter Window for Guest
 */
class GuestEnterWindow {
    /**
     * This method is constructor for scene on Guest Enter Window
     * @param guestStage - stage for building app
     */
    GuestEnterWindow(Stage guestStage) {
        HBox box = new HBox();
        GridPane guestLayout = new GridPane();
        guestStage.setScene(new Scene(guestLayout, 300, 120));

        Label instructionLabel = new Label("Enter name and click the target:");
        TextField enterName = new TextField();
        ImageView targetImage = new ImageView(new Image("Target.png"));
        Button cancelButton = new Button("Cancel");

        instructionLabel.setStyle("-fx-font-weight: bold;");

        targetImage.setFitHeight(50);
        targetImage.setFitWidth(50);
        targetImage.setOnMouseClicked((event) -> {
            if (!enterName.getText().isEmpty()) {
                GuestImplDAO guestImplDAO=new GuestImplDAO();
                try {
                    guestImplDAO.addGuest(enterName.getText());
                    MainModel.setName(enterName.getText());
                    MainModel.setRole(3);
                    new WorkWindow(guestStage);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            else System.out.println("Enter your name");
        });

        cancelButton.setStyle(" -fx-background-color: #d1d1d1; -fx-font-weight: bold;");
        cancelButton.setMinWidth(100);
        cancelButton.setOnAction((event) -> new EnterWindow(guestStage));

        box.getChildren().add(instructionLabel);
        guestLayout.addRow(0, box);
        guestLayout.add(enterName, 0, 1);
        guestLayout.add(targetImage, 1, 1);
        guestLayout.add(cancelButton, 0, 2);

        guestLayout.setMargin(cancelButton, new Insets(0, 0, 0, 35));
        guestLayout.setMargin(instructionLabel, new Insets(10, 0, 0, 0));
        guestLayout.setHgap(5);
        guestLayout.setPadding(new Insets(10, 20, 20, 20));
        guestStage.show();
    }
}
