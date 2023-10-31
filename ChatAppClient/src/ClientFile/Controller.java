package ClientFile;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Controller implements Initializable
{
    @FXML
    private Button send_button;

    @FXML
    private TextField text_message;

    @FXML
    private ScrollPane main_scroll;

    @FXML
    private VBox past_messages;

    @FXML
    private Label top;

    private Client client;
    
    @Override
    public void initialize(URL loc, ResourceBundle resources) 
    {
        try
        {
            client = new Client(new Socket("localhost",1234));
            System.out.println("Connected to Server");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Unable to Connect to Server");
        }
        past_messages.heightProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldval, Number newval)
            {
                main_scroll.setVvalue((Double) newval);
            }
        });

        client.receiveMessageFromServer(past_messages);

        send_button.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent ae) 
            {
                String messageToSend = text_message.getText();
                if(!messageToSend.isEmpty())
                {
                    HBox hbox = new HBox();
                    hbox.setAlignment(Pos.CENTER_RIGHT);
                    hbox.setPadding(new Insets(5,5,5,10));  //t,r,b,l
                    
                    Text text = new Text(messageToSend);        //Converting to Text format
                    TextFlow textFlow = new TextFlow(text);     //Wrapping functionality to make long texts readable

                    textFlow.setStyle(//"-fx-text-fill: rgb(239,242,255);" + 
                                      "-fx-background_color: rgb(15,125,242);" +
                                      "-fx-background-radius: 20px;");

                    textFlow.setPadding(new Insets(5,10,5,10));     //t,r,b,l
                    // text.setFill(Color.color(0.934,0.945,0.96));

                    hbox.getChildren().add(textFlow);       //Add entered text to textbox
                    past_messages.getChildren().add(hbox);   //Add textbox to the vbox

                    client.sendMessageToServer(messageToSend);
                    text_message.clear();   //Clear textbox after sending message
                }
            }
            
        });
    }

    public static void addLabel(String messageFromServer, VBox vbox)
    {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(5,5,5,10));      //t,r,b,l

        Text text = new Text(messageFromServer);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background_color: rgb(233,233,235);" +    //Default black text colour
                          "-fx-background-radius: 20px;");
        textFlow.setPadding(new Insets(5,10,5,10));
        hbox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() 
        {
            @Override
            public void run()
            {
                vbox.getChildren().add(hbox);
            }
        });
    }
}