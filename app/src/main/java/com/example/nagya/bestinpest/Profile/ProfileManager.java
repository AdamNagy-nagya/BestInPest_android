package com.example.nagya.bestinpest.Profile;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by nagya on 24/04/2018.
 */
/*
public class ProfileManager {
    Profile profile;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public ProfileManager(Activity parent){
        sharedPreferences = parent.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveProfileToSP(){
       editor.putInt("profileID", profile.getPlayerID());
       editor.commit();
    }

    public int getProfileId(){
        return sharedPreferences.getInt("profileID", -1);
    }

    public void setProfile(Integer profileID){

        this.profile = new Profile(profileID);
        saveProfileToSP();
    }

    public Profile getProfile() {
        if(profile ==null){
            getProfileId();
        }
        return profile;


    }
}
*/