package Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Anmeldung.fxml"));
        primaryStage.setTitle("Chat Anmeldung");
        primaryStage.setScene(new Scene(root, 282, 225));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
