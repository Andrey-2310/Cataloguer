package Scene;


import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Андрей on 24.02.2017.
 */

/**
 * From this method app Home Cataloguer gets started
 */
public class CreatingWindow extends Application {

    /**
     * From this method app Home Cataloguer gets started
     * @param primaryStage - stage on which app is built
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Home Cataloguer");
        EnterWindow logInWindow=new EnterWindow(primaryStage);
        primaryStage.show();
    }
}
