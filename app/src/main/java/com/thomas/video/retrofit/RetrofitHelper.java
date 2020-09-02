package com.thomas.video.retrofit;

import com.thomas.video.ApiConstant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitHelper {

    private static Retrofit singleton;

    public static <T> T createApi(Class<T> clazz) {
        if (singleton == null) {
            synchronized (RetrofitHelper.class) {
                if (singleton == null) {
                    singleton = new Retrofit.Builder()
                            .baseUrl(ApiConstant.Source.HOME_URL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .client(getOkHttpClient())
                            .build();
                }
            }
        }
        return singleton.create(clazz);
    }

    private static OkHttpClient getOkHttpClient() {
        OkHttpClient client = getUnSafeOkHttpClient();
        return client;
    }

    private static OkHttpClient getUnSafeOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .callTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES)
                .connectTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES)
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .hostnameVerifier(SSLUtils.HOSTNAME_VERIFIER)
                .sslSocketFactory(new TLSSocketFactory(), SSLUtils.DEFAULT_TRUST_MANAGERS);

        return builder.build();
    }

}
