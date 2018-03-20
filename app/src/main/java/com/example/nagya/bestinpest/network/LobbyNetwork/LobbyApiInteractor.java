package com.example.nagya.bestinpest.network.LobbyNetwork;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.nagya.bestinpest.Junction.item.JunctionRestItem;
import com.example.nagya.bestinpest.Junction.item.JunctionsWrapper;
import com.example.nagya.bestinpest.Lobby.item.Lobbies;
import com.example.nagya.bestinpest.Lobby.item.LobbyCreatingPOST;
import com.example.nagya.bestinpest.Lobby.item.LobbyRestItem;
import com.example.nagya.bestinpest.Lobby.item.Player;
import com.example.nagya.bestinpest.network.LobbyNetwork.item.DeleteResponse;
import com.example.nagya.bestinpest.network.LobbyNetwork.item.PasswordResponse;

import org.greenrobot.eventbus.EventBus;

import java.net.URLEncoder;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
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

    public void createLobby(LobbyCreatingPOST creatingPOST){
        Call<LobbyRestItem> createLobbyReq = lobbyApi.createLobby(creatingPOST);

        runCallOnBackgroundThread(createLobbyReq);
    }

    public void deleteLobby(Integer lobbyId) {
        Call<DeleteResponse> deleteLobbyReq= lobbyApi.deleteLobby(lobbyId);
        runCallOnBackgroundThread(deleteLobbyReq);
    }

    public void getAvailableJunctions(Integer lobbyId, Double lat,Double lon){
        Call<List<JunctionRestItem>> getAvaReq = lobbyApi.getFreeJunctionsNearby(lobbyId,lat,lon);
        runListtAvailableJunctions(getAvaReq);
    }

    public void authToLobby(Integer lobbyId, String password){
        Call<LobbyRestItem> loginPassReq = lobbyApi.authToLobby(lobbyId, URLEncoder.encode(password));
        Log.e("ASDASDASDSA","MOST MEGy");

        runsetPassIfOk(loginPassReq);


    }

    public void loginToLobby(Integer lobbyId, Player player){
        Call<LobbyRestItem> loginLobby = lobbyApi.addPlayerToLobby(lobbyId,player);
        runCallOnBackgroundThread(loginLobby);

    }

    public void logoutFromLobby(Integer lobbyId, Integer playerId){
        Call<LobbyRestItem> logoutlobby = lobbyApi.deletePlayer(lobbyId,playerId);
        wateverResponse(logoutlobby);
    }

    public void sendImReady(Integer lobbyId, Integer playerId){
        Call<Player> imReadyReq = lobbyApi.setPlayerReady(lobbyId,playerId);
        wateverResponse(imReadyReq);
    }

    private static <T> void wateverResponse(final Call<T> call) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    call.execute();

                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static <T> void runsetPassIfOk(final Call<LobbyRestItem> call) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    int codeInt= call.execute().code();
                    PasswordResponse response= new PasswordResponse(codeInt==200);

                        EventBus.getDefault().post( response);

                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private static <T> void runListtAvailableJunctions(final Call<List<JunctionRestItem>> call) {
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

    private static <T> void runListLobbyTask(final Call<List<LobbyRestItem>> call) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<LobbyRestItem> response = call.execute().body();
                    EventBus.getDefault().post( new Lobbies(response));
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
