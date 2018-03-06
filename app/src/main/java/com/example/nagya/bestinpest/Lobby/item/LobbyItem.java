package com.example.nagya.bestinpest.Lobby.item;

/**
 * Created by nagya on 28/02/2018.
 */

public class LobbyItem {

    private int ID;
    private String lobbyName;
    private String password;
    private Leader lobbyLeader;

    public LobbyItem(LobbyRestItem lobbyRestItem){
        this.lobbyName= lobbyRestItem.getName();
        this.ID= lobbyRestItem.getId();
        this.password= lobbyRestItem.getPassword();
        this.lobbyLeader = lobbyRestItem.getLeader();
    }

    public Leader getLobbyLeader() {
        return lobbyLeader;
    }

    public void setLobbyLeader(Leader lobbyLeader) {
        this.lobbyLeader = lobbyLeader;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


    public String getLobbyName() {
        return lobbyName;
    }

    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
