package com.example.nagya.bestinpest.Profile;

import com.example.nagya.bestinpest.Game.item.Player;

/**
 * Created by nagya on 22/04/2018.
 */

public class Profile {

    private Integer playerID;
    private boolean isCriminal;
    private boolean isLeader;
    private Player gameUser;
    private com.example.nagya.bestinpest.Lobby.item.Player lobbyUser;

    public Integer getPlayerID() {
        return playerID;
    }

    public void setPlayerID(Integer playerID) {
        this.playerID = playerID;
    }

    public Player getGameUser() {
        return gameUser;
    }

    public void setGameUser(Player gameUser) {
        this.gameUser = gameUser;
    }

    public com.example.nagya.bestinpest.Lobby.item.Player getLobbyUser() {
        return lobbyUser;
    }

    public void setLobbyUser(com.example.nagya.bestinpest.Lobby.item.Player lobbyUser) {
        this.lobbyUser = lobbyUser;
    }

    public boolean isCriminal() {
        return isCriminal;
    }

    public void setCriminal(boolean criminal) {
        isCriminal = criminal;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
    }
}
