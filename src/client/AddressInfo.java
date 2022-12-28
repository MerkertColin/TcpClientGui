package client;

import org.apache.commons.validator.Validator;
import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.InetAddressValidator;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class AddressInfo
{
    private String m_sAddress;
    private String m_sPort;

    public AddressInfo( String address, String port )
    {
        m_sAddress = address;
        m_sPort = port;
    }

    /**
     * validates the address and port
     * @return -1 validation failed, 1 validation successful
     */
    public boolean validate()
    {
        return isValidPort() && isValidAddress();
    }

    private boolean isValidPort()
    {
        int nPort;
        final int MAX_PORT = 0xFFFF;
        try
        {
            nPort = Integer.parseInt( m_sPort );
        }
        catch ( NumberFormatException nfe )
        {
            return false;
        }

        if ( nPort > MAX_PORT || nPort < 0 )
        {
            return false;
        }

        return true;
    }

    private boolean isValidAddress()
    {
        String sAddress = m_sAddress.trim();
        InetAddressValidator ipValidator = new InetAddressValidator();
        DomainValidator domainValidator = DomainValidator.getInstance();

        return ipValidator.isValid( sAddress ) || domainValidator.isValid( sAddress ) || sAddress.matches( "localhost" );
    }

    public SocketAddress getSocketAddress()
    {
        return new InetSocketAddress( m_sAddress, Integer.parseInt( m_sPort ) );
    }
}
