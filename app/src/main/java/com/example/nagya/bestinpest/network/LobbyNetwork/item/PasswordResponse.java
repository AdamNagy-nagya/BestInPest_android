package com.example.nagya.bestinpest.network.LobbyNetwork.item;

/**
 * Created by nagya on 16/03/2018.
 */

public class PasswordResponse {
    public boolean isPasswordOK() {
        return passwordOK;
    }

    public void setPasswordOK(boolean passwordOK) {
        this.passwordOK = passwordOK;
    }

    private boolean passwordOK;

    public PasswordResponse(boolean e){
        this.passwordOK= e;
    }

}
