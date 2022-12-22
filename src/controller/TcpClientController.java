package controller;

import client.TcpClient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TcpClientController  {
    @FXML
    private Button btnConnect;
    @FXML
    private TextField tfIP;
    @FXML
    private TextField tfPort;
    @FXML
    private TextArea taMessageArea;
    @FXML
    private TextField tfMessage;

    private TcpClient m_tcpClient;

    public TcpClientController()
    {
        m_tcpClient = new TcpClient();
    }

    @FXML
    private void OnConnectClick()
    {
        String sIp = tfIP.getText();
        String sPort = tfPort.getText();

        if ( m_tcpClient.isConnected() )
        {
            m_tcpClient.disconnect();
            btnConnect.setText("Connect");
            tfIP.setEditable(true);
            tfPort.setEditable(true);
        }
        else
        {
            m_tcpClient.connect( sIp, Integer.parseInt( sPort ) );
            btnConnect.setText("Disconnect");
            tfIP.setEditable(false);
            tfPort.setEditable(false);
        }
    }

    @FXML
    private void OnSendMessageClick()
    {
        String sMessage = tfMessage.getText();
        StringBuilder sb = new StringBuilder("");
        sb.append( taMessageArea.getText() );
        m_tcpClient.sendMessage( sMessage );
        sb.append( "Sent: " + sMessage + "\n" );
        taMessageArea.setText( sb.toString() );
    }

}
