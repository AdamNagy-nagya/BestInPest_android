package com.example.nagya.bestinpest.network.RouteNetwork;

import com.example.nagya.bestinpest.network.RouteNetwork.item.JunctionRestItem;
import com.example.nagya.bestinpest.network.RouteNetwork.item.Route;
import com.example.nagya.bestinpest.network.RouteNetwork.item.Schedule;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by nagya on 05/04/2018.
 */

public interface RouteApi {

    static String ENDPOINT_URL = "https://pacific-sierra-60952.herokuapp.com";

    @GET("junctions")
    Call<List<JunctionRestItem>> getAllJunctions();

    @GET("junctions/{id}")
    Call<JunctionRestItem> getJunctionbyId(@Path("id") String junctionId);

    @GET ("routes")
    Call<List<Route>> getAllRoutes();

    @GET ("routes-between")
    Call<List<Route>> getRoutesBetween(@Query("departure") String departure, @Query("arrival") String arrival);

    @GET ("routes/{id}")
    Call<Route> getRoutebyId(@Path("id") long routeId);

    @GET ("routes/{id}/schedule")
    Call<List<Schedule>> getSchedule (@Path("id") long routeId);

}
