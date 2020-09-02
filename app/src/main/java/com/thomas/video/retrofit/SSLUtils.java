package com.thomas.video.retrofit;

import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

public class SSLUtils {
    public static final HostnameVerifier HOSTNAME_VERIFIER = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    public static final SSLSocketFactory SSL_SOCKET_FACTORY = new TLSSocketFactory();


    public static final X509TrustManager DEFAULT_TRUST_MANAGERS = new X509TrustManager() {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
            // Trust.
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
            // Trust.
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    };
}
