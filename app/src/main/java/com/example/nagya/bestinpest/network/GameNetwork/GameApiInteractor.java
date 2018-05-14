package com.example.nagya.bestinpest.network.GameNetwork;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.nagya.bestinpest.Game.item.GameObject;
import com.example.nagya.bestinpest.network.GameNetwork.item.CriminalStepPOST;
import com.example.nagya.bestinpest.network.GameNetwork.item.DetectiveStepPOST;
import com.example.nagya.bestinpest.network.GameNetwork.item.StepRecommendation;
import com.example.nagya.bestinpest.network.RouteNetwork.item.JunctionRestItem;
import com.example.nagya.bestinpest.network.RouteNetwork.item.JunctionsWrapper;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

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
        Log.e("E","E"+gameID);
        runCallOnBackgroundThread(gameObjectCall);
    }

    public void getAvailableJunctions(int playerId){
        Call<List<JunctionRestItem>> getAvailableJunctionsreq= gameApi.getAvailableJunctions(playerId);
        runListtAvailableJunctions(getAvailableJunctionsreq);
    }

    public void sendDetectivePlan(int gameId, DetectiveStepPOST detectiveStepPOST){
        Call<GameObject> sendDetecReq = gameApi.addDetectivePlan(gameId,detectiveStepPOST);
        runwaterver(sendDetecReq);
    }

    public void sendCriminalStep(int gameId, CriminalStepPOST criminalStepPOST){
        Call<GameObject> sendCriminalStep = gameApi.addCriminalStep(gameId,criminalStepPOST);
        runwaterver(sendCriminalStep);
    }

    public void sendDetectiveStepReact(int gameId, int planId, int playerId, String reactString){
        Call<GameObject> sendReactReq = gameApi.approvePlan(gameId,planId,playerId,reactString);
        runwaterver(sendReactReq);
    }

    public void sendDetectiveRecommedation(int gameId, StepRecommendation stepRecommendation){
        Call<GameObject> sendRecreq = gameApi.addRecommendation(gameId,stepRecommendation);
        runwaterver(sendRecreq);
    }




    public GameApiInteractor(Context context) {
        this.context = context;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(gameApi.ENDPOINT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.gameApi = retrofit.create(GameApi.class);
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



    private static <T> void runwaterver(final Call<T> call) {
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
