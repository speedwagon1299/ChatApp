package ServerFile;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;
import Exceptions.MessageLengthExceededException;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.paint.Color;
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

    @FXML
    private Label Error;

    private Server server;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        try
        {
            server = new Server(new ServerSocket(1234));   //Connect to Server
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.out.println("Error Creating Server");
        }

        past_messages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldvalue, Number newvalue)
            {
                main_scroll.setVvalue((Double) newvalue);       //Scroll till bottom automatically when message added
            }
        });

        server.recieveMessageFromClient(past_messages);  //Thread which waits for incoming messages
        
        send_button.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent ae)
            {
                String messageToSend = text_message.getText();      //Retrieve message from TextFieldBox
                if(!messageToSend.isEmpty())
                {
                    try
                    {
                        if(messageToSend.length()>255)
                        {
                            throw new MessageLengthExceededException(messageToSend.length());
                        }
                        else
                        {
                            Error.setText("");
                        }
                    }
                    catch(MessageLengthExceededException e)
                    {
                        Error.setText(e.toString());
                        Error.setTextFill(Color.RED);
                        return;
                    }
                    HBox hbox = new HBox();         //Create message bubble after being sent
                    hbox.setAlignment(Pos.CENTER_RIGHT);
                    hbox.setPadding(new Insets(5,5,5,10));      //t,r,b,l

                    Text text = new Text(messageToSend);        //Converting to Text format
                    TextFlow textFlow = new TextFlow(text);     //Wrapping functionality to make long texts readable
                    

                    textFlow.setStyle("-fx-background-color: rgb(15,125,242); -fx-background-radius: 20px;");


                    textFlow.setPadding(new Insets(5,10,5,10));     //t,r,b,l
                    hbox.getChildren().add(textFlow);       //Add entered text to textbox
                    past_messages.getChildren().add(hbox);   //Add textbox to the vbox

                    server.sendMessageToClient(messageToSend);
                    text_message.clear();   //Clear textbox after sending message
                }
            }
        });
    }
    public static void addLabel(String messageFromClient, VBox vbox)
    {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(5,5,5,10));      //t,r,b,l

        Text text = new Text(messageFromClient);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(233,233,235);" +    //Default white text colour
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