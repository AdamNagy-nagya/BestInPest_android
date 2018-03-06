package com.example.nagya.bestinpest.network.LobbyNetwork;

import com.example.nagya.bestinpest.Lobby.item.LobbyCreatingPOST;
import com.example.nagya.bestinpest.Lobby.item.LobbyRestItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by nagya on 04/03/2018.
 */

public interface LobbyApi {

    static String ENDPOINT_URL = "https://pacific-sierra-60952.herokuapp.com";


    @GET("lobbies")
    Call<List<LobbyRestItem>> getLobbies();

    @POST("lobbies")
    Call<LobbyCreatingPOST> createLobby();



}
