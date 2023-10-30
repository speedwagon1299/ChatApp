package ClientFile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) 
    {
        try 
        {
            Parent root = FXMLLoader.load(getClass().getResource("control.fxml"));
            primaryStage.setTitle("Client");
            primaryStage.setScene(new Scene(root, 478, 396));
            primaryStage.show();
        } 
        catch (Exception e) 
        {
            e.printStackTrace(); 
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}