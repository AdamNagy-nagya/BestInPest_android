package com.example.nagya.bestinpest.network.RouteNetwork;

import android.content.Context;
import android.os.Handler;

import com.example.nagya.bestinpest.network.GameNetwork.GameApi;
import com.example.nagya.bestinpest.network.RouteNetwork.item.JunctionRestItem;
import com.example.nagya.bestinpest.network.RouteNetwork.item.JunctionsWrapper;
import com.example.nagya.bestinpest.network.RouteNetwork.item.Route;
import com.example.nagya.bestinpest.network.RouteNetwork.item.RouteWrapper;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nagya on 05/04/2018.
 */


public class RouteApiInteractor {

    private RouteApi routeApi;
    private final Context context;


    public void getRoutesBetween(String departure, String arrival){
        Call<List<Route>>getRoutesreq =routeApi.getRoutesBetween(departure,arrival);
        runListRoutes(getRoutesreq);
    }

    public void getJuncByID(String junctionId){
        Call<JunctionRestItem>getRoutesreq =routeApi.getJunctionbyId(junctionId);
        runCallOnBackgroundThread(getRoutesreq);
    }

    public void getJunctoins(){
        Call<List<JunctionRestItem>>req = routeApi.getAllJunctions();
        runListJunctions(req);
    }

    public void getRoutes(){
        Call<List<Route>>req = routeApi.getAllRoutes();
        runListRoutes(req);
    }
    public RouteApiInteractor(Context context) {
        this.context = context;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(routeApi.ENDPOINT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.routeApi = retrofit.create(RouteApi.class);
    }


    private static <T> void runListRoutes(final Call<List<Route>> call) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<Route> response = call.execute().body();
                    EventBus.getDefault().post( new RouteWrapper(response));
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private static <T> void runListJunctions(final Call<List<JunctionRestItem>> call) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<JunctionRestItem> response = call.execute().body();
                    EventBus.getDefault().post( new JunctionsWrapper(response));
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private static <T> void runCallOnBackgroundThread(final Call<T> call) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final T response = call.execute().body();
                    EventBus.getDefault().post(response);
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
