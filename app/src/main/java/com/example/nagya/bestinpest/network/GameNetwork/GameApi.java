package com.example.nagya.bestinpest.network.GameNetwork;

import com.example.nagya.bestinpest.network.GameNetwork.item.GameObjectRestItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by nagya on 24/03/2018.
 */

public interface GameApi {
    static String ENDPOINT_URL = "https://pacific-sierra-60952.herokuapp.com";

    @GET("games/{id}")
    Call<GameObjectRestItem> getGameById(@Path("id") Integer gameId);


   // @POST("games/{id}/criminal-step")
    //Call<>

    // @POST("games/{id}/criminal-step")
   // Call<>
}
