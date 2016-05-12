package com.password.kg.passwordbook.rest;

import com.password.kg.passwordbook.dto.DataDto;
import com.password.kg.passwordbook.model.AccessToken;
import com.password.kg.passwordbook.model.SignUpUser;
import com.password.kg.passwordbook.model.SyncData;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by Nurs on 27.11.2015.
 */
public class ApiUrls {
    public interface Authorization{

        @FormUrlEncoded
        @POST("/api/token")
        Call<AccessToken> getAccessToken(
                @Field("grant_type") String grant_type,
                @Field("username") String username,
                @Field("password") String password
        );

        @POST("/api/account/register")
        Call<SignUpUser> signUp(@Body SignUpUser signUpUser);

    }

    public interface DataSync {

        @GET("/api/api/syncs")
        Call<DataDto> getSyncData();

        @POST("/api/syncs")
        Call<DataDto> postSyncData();
    }

}
