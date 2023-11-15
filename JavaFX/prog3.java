import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

public class prog3 extends Application
{
    public static void main(String args[])
    {
        launch(args);
    }
    public void start(Stage myStage)
    {
        myStage.setTitle("Credentials");
        GridPane root = new GridPane();
        Label Wel = new Label("Welcome");
        Wel.setTextFill(Color.BLACK);
        Label user = new Label("Username");
        user.setTextFill(Color.GRAY);
        Label pass = new Label("Password");
        pass.setTextFill(Color.GRAY);
        TextField uf = new TextField();
        uf.setPromptText("Enter Username");
        PasswordField pf = new PasswordField();
        pf.setPromptText("Enter Password");
        Label output = new Label();
        Button sign = new Button("Sign in");
        // uf.setOnAction(new EventHandler<ActionEvent>()
        // {
        //     public void handle(ActionEvent ae) {}
        // });
        // pf.setOnAction(new EventHandler<ActionEvent>()
        // {
        //     public void handle(ActionEvent ae) {}
        // });
        sign.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent ae)
            {
                if(uf.getText() != "" && pf.getText() != "")
                output.setText("Welcome " + uf.getText());
            }
        });
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10,10,10,30));
        root.add(Wel,0,0);
        root.add(user,0,1);
        root.add(pass,0,2);
        root.add(uf,1,1);
        root.add(pf,1,2);
        root.add(output,1,3);
        root.add(sign,2,3);
        Scene myScene = new Scene(root,350,175);
        myStage.setScene(myScene);
        myStage.show();
    }
}