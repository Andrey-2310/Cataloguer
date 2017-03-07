package Scene;


import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Андрей on 24.02.2017.
 */
public class CreatingWindow extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Home Cataloguer");
     //   WorkWindow workWindow=new WorkWindow(primaryStage);
        EnterWindow logInWindow=new EnterWindow(primaryStage);
        primaryStage.show();
    }
}
