package controller;

import client.TcpClient;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TcpClientController  {
    @FXML
    private Button btnConnect;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfPort;
    @FXML
    private TextArea taMessageArea;
    @FXML
    private TextField tfMessage;

    private final TcpClient m_tcpClient;

    public TcpClientController()
    {
        m_tcpClient = new TcpClient();
    }

    @FXML
    private void OnConnectClick()
    {
        if ( m_tcpClient.isConnected() )
        {
            disconnect();
        }
        else
        {
            tryConnect();
        }
    }

    private void disconnect()
    {
        updateMessageArea("Disconnecting...");
        m_tcpClient.disconnect();
        updateMessageArea( "Disconnect: Successful\n");
        btnConnect.setText("Connect");
    }
    private void tryConnect( )
    {
        boolean wasConnectionSuccessful;
        String sAddress = tfAddress.getText();
        String sPort = tfPort.getText();

        updateMessageArea("Connecting...");
        wasConnectionSuccessful = m_tcpClient.connect( sAddress, sPort );
        if ( wasConnectionSuccessful )
        {
            btnConnect.setText("Disconnect");
            updateMessageArea( "Connection: Successful");
        }
        else
        {
            updateMessageArea( "Connection: Failed\n");
            showErrorAlert( "Connecting failed", "Connecting failed" );
        }
    }
    private void showErrorAlert( String title, String message )
    {
        Alert alert = new Alert( Alert.AlertType.ERROR);
        alert.setTitle( title );
        alert.setContentText( message );
        alert.show();
    }

    @FXML
    private void OnSendMessageClick()
    {
        String sMessage = tfMessage.getText();
        m_tcpClient.sendMessage( sMessage );
        updateMessageArea( "Sent: " + sMessage );
        getResponse();
    }

    private void updateMessageArea( String message )
    {
        StringBuilder sb = new StringBuilder( "" );
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern( "HH:mm:ss" );
        LocalTime t = LocalTime.now();
        String timestamp = dtf.format( t ) + " >> ";
        sb.append( taMessageArea.getText() );
        sb.append( timestamp + message +  "\n" );
        taMessageArea.setText( sb.toString() );
    }

    private void getResponse()
    {
        String res = m_tcpClient.readResponse();
        updateMessageArea( "Response: " + res );
    }
}
