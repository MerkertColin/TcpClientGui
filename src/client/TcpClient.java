package client;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.InetSocketAddress;

public class TcpClient {

    private Socket m_clientSocket;
    public TcpClient() {
    }

    /**
     * connects to a socket
     * @param hostname address of the target
     * @param port port of the target
     * @return boolean if connection was successful
     */
    public boolean connect( String hostname, String port )
    {
        boolean isSuccessfulConnection;
        boolean wasValidationSuccessful;
        SocketAddress address;

        AddressInfo addrInfo = new AddressInfo( hostname, port );
        wasValidationSuccessful = addrInfo.validate();
        if ( !wasValidationSuccessful )
        {
            isSuccessfulConnection = false;
        }
        else
        {
            m_clientSocket = new Socket();
            address = addrInfo.getSocketAddress();
            try
            {
                m_clientSocket.connect( address );
            } catch ( Exception e )
            {
                System.out.println( e.getMessage() );
            };

            isSuccessfulConnection = m_clientSocket.isConnected();
        }

        return isSuccessfulConnection;
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

        BufferedInputStream bufInStream;
        InputStream instream;
        StringBuilder sb = new StringBuilder( "" );
        String sLine = "";
        int len;
        try
        {
            instream = m_clientSocket.getInputStream();
            bufInStream = new BufferedInputStream( instream  );
            len = bufInStream.available();
            if ( len > 0 )
            {
                byte[] byteData = new byte[len];
                bufInStream.read( byteData );
                sb.append( new String(byteData) );
            }
        }
        catch ( Exception e )
        {
            System.out.println( "Receiving Message failed" );
            System.out.println( e.getMessage() );
        }
        return sb.toString();
    }
}
