package client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.InetSocketAddress;

public class TcpClient {

    private Socket m_clientSocket;
    public TcpClient() {
    }

    public void connect( String hostname, int port )
    {
        SocketAddress address = new InetSocketAddress(hostname, port);
        m_clientSocket = new Socket();
        try
        {
            m_clientSocket.connect( address );
        } catch ( Exception e )
        {
            System.out.println( "Connection Failed" );
            System.out.println( e.getMessage() );
        };

        if ( m_clientSocket.isConnected() )
        {
            System.out.println( "Connection Successful");
        }
    }
    public boolean isConnected()
    {
        return null != m_clientSocket && m_clientSocket.isConnected() && !m_clientSocket.isClosed();
    }
    public void disconnect()
    {
        if (m_clientSocket == null || m_clientSocket.isClosed() )
        {
            System.out.println( "Socket already closed." );
            return;
        }
        try
        {
            m_clientSocket.close();
        }
        catch (Exception e)
        {
            System.out.println( "Closing Socket failed." );
            System.out.println( e.getMessage() );
        }

    }

    public void sendMessage( String sMessage )
    {
        if ( !m_clientSocket.isConnected() )
        {
            System.out.println( "Cannot send Message. No Connection to Server established.");
            return;
        }
        OutputStream outstream;
        byte[] message;
        try
        {
            outstream = m_clientSocket.getOutputStream();
            message = sMessage.getBytes();
            outstream.write( message );
        }
        catch ( Exception e )
        {
            System.out.println( "Sending Message failed." );
            System.out.println( e.getMessage() );
        }
    }

    public String readResponse()
    {
        if ( !m_clientSocket.isConnected() )
        {
            return null;
        }

        BufferedReader reader;
        InputStream instream;
        String sResponseMessage = "";
        String sLine;
        try
        {
            instream = m_clientSocket.getInputStream();
            reader = new BufferedReader( new InputStreamReader( instream ) );
            while ( (sLine = reader.readLine()) != null )
            {
                sResponseMessage += sLine;
            }

        }
        catch ( Exception e )
        {
            sResponseMessage = null;
            System.out.println( "Receiving Message failed" );
            System.out.println( e.getMessage() );
        }
        return sResponseMessage;
    }
}
