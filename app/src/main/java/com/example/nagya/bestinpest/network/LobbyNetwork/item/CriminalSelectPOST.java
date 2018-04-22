package com.example.nagya.bestinpest.network.LobbyNetwork.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nagya on 22/04/2018.
 */

public class CriminalSelectPOST {


    @SerializedName("id")
    @Expose
    private Integer id;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public CriminalSelectPOST(Integer id){
      this.id= id;
    }
}
