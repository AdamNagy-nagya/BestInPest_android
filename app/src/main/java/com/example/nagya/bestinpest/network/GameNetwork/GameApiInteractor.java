package com.example.nagya.bestinpest.network.GameNetwork;

import android.content.Context;
import android.os.Handler;

import com.example.nagya.bestinpest.Game.item.GameObject;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nagya on 24/03/2018.
 */

public class GameApiInteractor {

    private GameApi gameApi;

    private final Context context;

    public void getGameObjectById(int gameID){
        Call<GameObject> gameObjectCall = gameApi.getGameById(gameID);
        runCallOnBackgroundThread(gameObjectCall);
    }

    public GameApiInteractor(Context context) {
        this.context = context;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(gameApi.ENDPOINT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.gameApi = retrofit.create(GameApi.class);
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
