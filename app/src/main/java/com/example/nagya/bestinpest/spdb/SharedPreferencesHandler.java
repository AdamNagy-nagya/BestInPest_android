package com.example.nagya.bestinpest.spdb;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by nagya on 06/05/2018.
 */

public class SharedPreferencesHandler {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public SharedPreferencesHandler(Activity parent){
        sharedPreferences = parent.getSharedPreferences(getClass().getPackage().getName(),Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void savePlayerID(Integer playerId){
        editor.putInt("playerId", playerId);
        editor.commit();
    }

    public void saveGameID(Integer gameId){
        editor.putInt("gameId", gameId);
        editor.commit();

    }

    public void saveRabbitMqUrl(String rabbitURL){
        editor.putString("rabbitUrl", rabbitURL);
        editor.commit();
    }

    public Integer loadPlayerID(){
            return sharedPreferences.getInt("playerId", -1);
    }

    public Integer loadGameID(){
            return sharedPreferences.getInt("gameId", -1);
    }

    public String loadRabbitMqUrl(){
        return  sharedPreferences.getString("rabbitUrl",null);
    }
}
