package ClientFile;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javafx.scene.layout.VBox;

public class Client 
{
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Client(Socket socket)
    {
         try
        {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.out.println("Error Creating Server");
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void receiveMessageFromServer(VBox vbox)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run() 
            {
                while(socket.isConnected())
                {
                    String messageFromServer;
                    try 
                    {
                        messageFromServer = bufferedReader.readLine();
                        Controller.addLabel(messageFromServer, vbox);
                    } 
                    catch (IOException e) 
                    {
                        e.printStackTrace();
                        System.out.println("Error receiving Message from Server");
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                    }
                }
            }
            
        }).start();
    }
    
    public void sendMessageToServer(String messageToServer)
    {
        try 
        {
            bufferedWriter.write(messageToServer);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        catch (IOException e) 
        {
            e.printStackTrace();
            System.out.println("Unable to Send message to Server");
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter)
    {
        try
        {
            if(bufferedReader != null) bufferedReader.close();
            if(bufferedWriter != null) bufferedWriter.close();
            if(socket != null) socket.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.out.println("Error Shutting down");
        }
    }
}
