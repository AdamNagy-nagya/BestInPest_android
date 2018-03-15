package com.example.nagya.bestinpest.network.LobbyNetwork;

import com.example.nagya.bestinpest.Junction.item.JunctionRestItem;
import com.example.nagya.bestinpest.Lobby.item.LobbyCreatingPOST;
import com.example.nagya.bestinpest.Lobby.item.LobbyRestItem;
import com.example.nagya.bestinpest.network.LobbyNetwork.item.DeleteResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by nagya on 04/03/2018.
 */

public interface LobbyApi {

    static String ENDPOINT_URL = "https://pacific-sierra-60952.herokuapp.com";


    @GET("lobbies")
    Call<List<LobbyRestItem>> getLobbies();

    @POST("lobbies")
    Call<LobbyCreatingPOST> createLobby(@Body LobbyCreatingPOST post);

    @DELETE("lobbies/{id}")
    Call<DeleteResponse>deleteLobby(@Path("id") Integer lobbyId);

    @GET("lobbies/{id}")
    Call<LobbyRestItem> getLobbyById(@Path("id") Integer lobbyId);

    @GET("lobbies/{id}/avaible-junctions")
    Call<List<JunctionRestItem>> getFreeJunctionsNearby(@Path("id") Integer lobbyId, @Query("lat") Double lat, @Query("lon") Double lon);



}
