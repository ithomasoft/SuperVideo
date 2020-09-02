package com.thomas.video;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET(ApiConstant.Others.ONE_URL)
    Call<String> getSplash();

    @GET("https://www.okzyw.com/")
    Call<String> getDetail(@Query("m") String url);

    @GET("index.php")
    Call<String> getResult(@Query("m") String m);


}
