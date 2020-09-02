package com.thomas.video.retrofit;

import android.os.Build;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import static com.thomas.video.retrofit.SSLUtils.DEFAULT_TRUST_MANAGERS;

public class TLSSocketFactory extends SSLSocketFactory {


    private static final String PROTOCOL_ARRAY[];

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PROTOCOL_ARRAY = new String[]{"TLSv1", "TLSv1.1", "TLSv1.2"};
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PROTOCOL_ARRAY = new String[]{"SSLv3", "TLSv1", "TLSv1.1", "TLSv1.2"};
        } else {
            PROTOCOL_ARRAY = new String[]{"SSLv3", "TLSv1"};
        }
    }



    private static void setSupportProtocolAndCipherSuites(Socket socket) {
        if (socket instanceof SSLSocket) {
            ((SSLSocket) socket).setEnabledProtocols(PROTOCOL_ARRAY);
        }
    }


    private SSLSocketFactory delegate;

    public TLSSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{DEFAULT_TRUST_MANAGERS}, new SecureRandom());
            delegate = sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new AssertionError();
        }
    }

    public TLSSocketFactory(SSLSocketFactory factory) {
        this.delegate = factory;
    }


    @Override
    public String[] getDefaultCipherSuites() {
        return delegate.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return delegate.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
        Socket ssl = delegate.createSocket(s, host, port, autoClose);
        setSupportProtocolAndCipherSuites(ssl);
        return ssl;
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        Socket ssl = delegate.createSocket(host, port);
        setSupportProtocolAndCipherSuites(ssl);
        return ssl;
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
        Socket ssl = delegate.createSocket(host, port, localHost, localPort);
        setSupportProtocolAndCipherSuites(ssl);
        return ssl;
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        Socket ssl = delegate.createSocket(host, port);
        setSupportProtocolAndCipherSuites(ssl);
        return ssl;
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        Socket ssl = delegate.createSocket(address, port, localAddress, localPort);
        setSupportProtocolAndCipherSuites(ssl);
        return ssl;
    }

    @Override
    public Socket createSocket() throws IOException {
        Socket ssl = delegate.createSocket();
        setSupportProtocolAndCipherSuites(ssl);
        return ssl;
    }
}
