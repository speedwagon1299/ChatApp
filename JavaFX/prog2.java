import javafx.scene.*;
import javafx.stage.*;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.geometry.*;

public class prog2 extends Application
{
    public static void main(String[] args) 
    {
        launch(args);
    }
    public void start(Stage myStage)
    {
        myStage.setTitle("Multiplication Table");
        FlowPane root = new FlowPane(10,10);
        FlowPane input = new FlowPane(10,10);
        FlowPane output = new FlowPane(10,10);

        Label lbl = new Label("Enter the number: ");
        lbl.setTextFill(Color.BLACK);
        TextField tf = new TextField();
        tf.setPromptText("Enter");
        tf.setPrefColumnCount(5);
        tf.setOnAction(new EventHandler<ActionEvent>() 
        {
            public void handle(ActionEvent ae)
            {
                updateTable(output, Integer.parseInt(tf.getText()));
            }
        });
        input.getChildren().addAll(lbl,tf);
        input.setAlignment(Pos.TOP_CENTER);
        output.setAlignment(Pos.BOTTOM_CENTER);
        root.getChildren().addAll(input,output);
        Scene myScene = new Scene(root,425,200);
        myStage.setScene(myScene);
        myStage.show();
    }
    private void updateTable(FlowPane output, int num)
    {
        output.getChildren().clear();
        for(int i = 1; i <= 10; i++)
        {
            Label lbl = new Label("\n" + num + " x " + i + " = " + (num*i));
            output.getChildren().add(lbl);
        }
    }
}
