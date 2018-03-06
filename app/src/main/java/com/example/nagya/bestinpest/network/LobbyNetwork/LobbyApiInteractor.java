package com.example.nagya.bestinpest.network.LobbyNetwork;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.nagya.bestinpest.Lobby.item.Lobbies;
import com.example.nagya.bestinpest.Lobby.item.LobbyRestItem;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nagya on 04/03/2018.
 */

public class LobbyApiInteractor {
    private  final  LobbyApi lobbyApi;

    private final Context context;

    public LobbyApiInteractor(Context context) {
        this.context = context;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LobbyApi.ENDPOINT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.lobbyApi = retrofit.create(LobbyApi.class);
    }

    public void findLobbies(){
        Log.i("API","find lobbies");
        Call<List<LobbyRestItem>> findLobbiesRequest = lobbyApi.getLobbies();
        runListLobbyTask(findLobbiesRequest);
    }

    public void createLobby(){
        Call<> createLobbyReq = lobbyApi.createLobby();
        runCallOnBackgroundThread(createLobbyReq);
    }


    private static <T> void runListLobbyTask(final Call<List<LobbyRestItem>> call) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<LobbyRestItem> response = call.execute().body();
                    Log.e("asdasd",response.get(0).getName());
                    EventBus.getDefault().post( new Lobbies(response));


                } catch (final Exception e) {
                    e.printStackTrace();

                    //  EventBus.getDefault().post(new NotFoundEvent());

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
                    //Log.e("LOBBY", "ASDASDASDASD");
                    EventBus.getDefault().post(response);


                } catch (final Exception e) {
                    e.printStackTrace();

                  //  EventBus.getDefault().post(new NotFoundEvent());

                }
            }
        }).start();
    }

}
