package com.example.loginscreendemoproject.service;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by sarabjjeet on 9/4/17.
 */

public interface ApiInterface {
@FormUrlEncoded
    @POST("login/")
    Call<ResponseBody> login(@Header("X-APP-TOKEN") String header, @Field("username") String username,
                             @Field("password") String password,@Field("device_type") String device_type,
                             @Field("device_token") String device_token);
}
