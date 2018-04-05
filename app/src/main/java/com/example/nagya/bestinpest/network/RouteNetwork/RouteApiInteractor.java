package com.example.nagya.bestinpest.network.RouteNetwork;

import android.content.Context;
import android.os.Handler;

import com.example.nagya.bestinpest.network.GameNetwork.GameApi;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nagya on 05/04/2018.
 */

public class RouteApiInteractor {

    private RouteApi routeApi;
    private final Context context;

    public RouteApiInteractor(Context context) {
        this.context = context;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(routeApi.ENDPOINT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.routeApi = retrofit.create(RouteApi.class);
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
