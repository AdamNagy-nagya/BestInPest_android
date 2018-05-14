package com.example.nagya.bestinpest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nagya.bestinpest.Game.item.GameObject;
import com.example.nagya.bestinpest.Lobby.InsideLobbyFragment;
import com.example.nagya.bestinpest.Lobby.LobbyCreateDialog;
import com.example.nagya.bestinpest.Lobby.LobbyEntryPassFragment;
import com.example.nagya.bestinpest.Lobby.LobbyListFragment;
import com.example.nagya.bestinpest.Lobby.item.Lobbies;
import com.example.nagya.bestinpest.Lobby.item.LobbyCreatingPOST;
import com.example.nagya.bestinpest.Lobby.item.LobbyRestItem;
import com.example.nagya.bestinpest.Lobby.item.Player;
import com.example.nagya.bestinpest.network.LobbyNetwork.LobbyApiInteractor;
import com.example.nagya.bestinpest.network.LobbyNetwork.item.CriminalSelectPOST;
import com.example.nagya.bestinpest.network.LobbyNetwork.item.PasswordResponse;
import com.example.nagya.bestinpest.network.LobbyNetwork.item.RabbitServerURIRestResponse;
import com.example.nagya.bestinpest.network.RabbitMq.InsideLobbyRabbitMq;
import com.example.nagya.bestinpest.network.RabbitMq.LobbiesRabbitMq;
import com.example.nagya.bestinpest.network.RabbitMq.item.LobbiesRabbitMqItem;
import com.example.nagya.bestinpest.network.RouteNetwork.item.JunctionRestItem;
import com.example.nagya.bestinpest.spdb.SharedPreferencesHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMenuActivity extends AppCompatActivity implements LobbyCreateDialog.createLobby, LobbyListFragment.joinLobbyInterface {


    @BindView(R.id.MainMenuIconImage)
    ImageView MainMenuIconImage;
    @BindView(R.id.MainMenuResumeGame)
    Button MainMenuResumeGame;
    @BindView(R.id.MainMenuFindLobby)
    ImageButton MainMenuFindLobby;
    @BindView(R.id.MainMenuCreateLobby)
    ImageButton MainMenuCreateLobby;

    private LobbyApiInteractor lobbyApiInteractor;
    private LobbyEntryPassFragment lobbyEntryPassFragment;
    private LobbyListFragment lobbyListFragment;
    private InsideLobbyFragment insideLobbyFragment;
    private LobbyCreateDialog lobbyCreateDialog;

    private LobbiesRabbitMq lobbiesRabbitMq;
    private InsideLobbyRabbitMq insideLobbyRabbitMq;
    private static String RabbitMqURL;

    SharedPreferencesHandler spHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);
        lobbyApiInteractor = new LobbyApiInteractor(this);
        MainMenuIconImage.setImageResource(R.drawable.logo_bestinpest);
        MainMenuIconImage.getRootView().setBackgroundColor(getResources().getColor(R.color.asphaltGray));
        MainMenuCreateLobby.setImageResource(R.drawable.newgame_logo);
        MainMenuFindLobby.setImageResource(R.drawable.joingame_logo);
        setupRabbitConnection();
        spHandler = new SharedPreferencesHandler(this);
    }

    @OnClick({R.id.MainMenuFindLobby, R.id.MainMenuCreateLobby})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.MainMenuFindLobby:
                lobbyApiInteractor.findLobbies();

                break;
            case R.id.MainMenuCreateLobby:

                lobbyCreateDialog = new LobbyCreateDialog();
                lobbyCreateDialog.setParent(this).show(this.getSupportFragmentManager(), "dialog");
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        if(spHandler.loadGameID()!=-1){
         //   startGame();
        }
    }

    @Override
    public void onStop() {

        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLobbies(Lobbies lobbyRestItem) {
        lobbyListFragment = new LobbyListFragment();
        lobbyListFragment.showDialog(this, lobbyRestItem);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPasswordResponse(PasswordResponse passwordResponse) {
        lobbyEntryPassFragment.setPasswordValidateOK(passwordResponse);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLobbyItemToJoin(LobbyRestItem lobbyRestItem) {

        if (insideLobbyRabbitMq == null) {
            insideLobbyRabbitMq = new InsideLobbyRabbitMq(RabbitMqURL, lobbyRestItem.getId());
        } else {
            insideLobbyRabbitMq.changeLobby(lobbyRestItem.getId());
        }
        if (insideLobbyFragment == null) {
            insideLobbyFragment = new InsideLobbyFragment();
        }
        insideLobbyFragment.setLobby(this, lobbyRestItem).show(this.getSupportFragmentManager(), "LobbyDialog");

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRabbitMqUrl(RabbitServerURIRestResponse rabbitServerURIRestResponse) {


        RabbitMqURL = rabbitServerURIRestResponse.getUrl();
        lobbiesRabbitMq = new LobbiesRabbitMq(rabbitServerURIRestResponse.getUrl());
        spHandler.saveRabbitMqUrl(RabbitMqURL);
        Log.d("meggvan az url", "" + rabbitServerURIRestResponse.getUrl());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLobbyListChanged(LobbiesRabbitMqItem item) {
        if (lobbyListFragment != null) {
            lobbyListFragment.refreshList(item.getObject());
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGameObject(GameObject gameObject) {

        spHandler.saveGameID(gameObject.getId());
        startGame(gameObject.getId());

    }

    public void startGame(Integer gameId){
        spHandler.saveGameID(gameId);
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }


    @Override
    public void createThisLobby(LobbyCreatingPOST lobbyCreatingPOST) {
        lobbyApiInteractor.createLobby(lobbyCreatingPOST);
        Toast.makeText(this, "Lobby created", Toast.LENGTH_LONG).show();
    }

    private void setupRabbitConnection() {
        lobbyApiInteractor.getRabbitmqRxUrl();
    }

    @Override
    public void joinToThisLobby(LobbyRestItem lobbyRestItem) {

        Toast.makeText(this, "Joining lobby " + lobbyRestItem.getId(), Toast.LENGTH_LONG).show();

        lobbyEntryPassFragment = new LobbyEntryPassFragment();
        lobbyEntryPassFragment.setLobbyParent(lobbyRestItem, this).show(this.getSupportFragmentManager(), "PassDialog");
    }

    public void readyToJoinLobby(LobbyRestItem lobby, JunctionRestItem junction, String playerName) {
        if (junction == null){
            Toast.makeText(this, R.string.toast_addallFirst,Toast.LENGTH_LONG).show();
        }else
        lobbyApiInteractor.loginToLobby(lobby.getId(), new Player(junction.getId(), playerName));
    }

    public void deleteLobby(Integer lobbyId) {
        lobbyApiInteractor.deleteLobby(lobbyId);
    }

    //TODO real GPS DATA!!

    public void sendGPS(Integer lobbyId) {
        lobbyApiInteractor.getAvailableJunctions(lobbyId, 47.0, 19.0);
    }

    public void sendGPSFreeAll() {
        lobbyApiInteractor.getFreeJunctionsNearbyForFirstPlayer(47.0, 19.0);
    }

    public void validatePassword(String password, LobbyRestItem lobby) {
        lobbyApiInteractor.authToLobby(lobby.getId(), password);
    }

    public void leaveLobby(LobbyRestItem lobby, Player profile) {
        lobbyApiInteractor.logoutFromLobby(lobby.getId(), profile.getId());
        Toast.makeText(this, "You left the lobby", Toast.LENGTH_LONG).show();
    }

    public void setPlayerReady(LobbyRestItem lobbyRestItem, Player profile) {
        lobbyApiInteractor.sendImReady(lobbyRestItem.getId(), profile.getId());
    }

    public void setPlayerCriminal(LobbyRestItem lobbyRestItem, Player player) {
        lobbyApiInteractor.setCriminal(lobbyRestItem.getId(), new CriminalSelectPOST(player.getId()));
    }


    public void startGameSend(Integer lobbyId) {
        lobbyApiInteractor.startGame(lobbyId);
    }


    @OnClick(R.id.MainMenuResumeGame)
    public void onViewClicked() {
        Intent intent = new Intent(this, GameActivity.class);
        //TODO normális értékek átadása!!!
        spHandler.savePlayerID(193);
        spHandler.saveGameID(1000);
        spHandler.saveRabbitMqUrl(RabbitMqURL);

        intent.putExtra("GameId", 1000);
        intent.putExtra("PlayerId", 193);
        intent.putExtra("RabbitMqURL", RabbitMqURL);
        startActivity(intent);

    }
}
