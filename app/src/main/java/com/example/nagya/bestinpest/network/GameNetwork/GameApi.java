package com.example.nagya.bestinpest.network.GameNetwork;

import com.example.nagya.bestinpest.Game.item.GameObject;
import com.example.nagya.bestinpest.network.GameNetwork.item.CriminalStepPOST;
import com.example.nagya.bestinpest.network.GameNetwork.item.DetectiveStepPOST;

import com.example.nagya.bestinpest.network.GameNetwork.item.StepRecommendation;
import com.example.nagya.bestinpest.network.RouteNetwork.item.JunctionRestItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by nagya on 24/03/2018.
 */

public interface GameApi {
    static String ENDPOINT_URL = "https://pacific-sierra-60952.herokuapp.com";

    @GET("games/{id}")
    Call<GameObject> getGameById(@Path("id") Integer gameId);

    @POST("games/{id}/criminal-step")
    Call<GameObject> addCriminalStep (@Path("id") long gameId, @Body CriminalStepPOST criminalStepPOST);

    @POST("games/{id}/detective-plan")
    Call<GameObject> addDetectivePlan (@Path("id") long gameId, @Body DetectiveStepPOST criminalStep);

    @POST("games/{id}/plans/{planId}/react")
    Call<GameObject> approvePlan (@Path("id") long gameId, @Path("planId") long planId, @Query("playerId") long playerId, @Query("reaction") String reaction);

    @POST ("games/{id}/players/{playerId}/ready")
    Call<GameObject> setPlayerReady(@Path("id") long gameId, @Path("playerId") long playerId);

    @POST ("games/{id}/recommendation")
    Call<GameObject>  addRecommendation (@Path("id") long gameId, @Body StepRecommendation recommendation);

    @DELETE ("games/{id}/recommendations/{recommendationId}")
    Call<GameObject> deleteRecommendation (@Path("id") long gameId, @Path("recommendationId") long recomondId);

    @GET ("players/{id}/available-junctions")
    Call<List<JunctionRestItem>> getAvailableJunctions(@Path("id") long playerId);

}
