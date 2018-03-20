package com.example.nagya.bestinpest;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nagya.bestinpest.Junction.item.JunctionRestItem;
import com.example.nagya.bestinpest.Junction.item.JunctionsWrapper;
import com.example.nagya.bestinpest.Lobby.LobbyCreateDialog;
import com.example.nagya.bestinpest.Lobby.LobbyEntryPassFragment;
import com.example.nagya.bestinpest.Lobby.LobbyListFragment;
import com.example.nagya.bestinpest.Lobby.item.Lobbies;
import com.example.nagya.bestinpest.Lobby.item.LobbyCreatingPOST;
import com.example.nagya.bestinpest.Lobby.item.LobbyRestItem;
import com.example.nagya.bestinpest.Lobby.item.Player;
import com.example.nagya.bestinpest.network.LobbyNetwork.LobbyApiInteractor;
import com.example.nagya.bestinpest.network.LobbyNetwork.item.PasswordResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMenuActivity extends AppCompatActivity implements LobbyCreateDialog.createLobby, LobbyListFragment.joinLobbyInterface {

    @BindView(R.id.MainMenuFindLobby)
    Button MainMenuFindLobby;
    @BindView(R.id.MainMenuCreateLobby)
    Button MainMenuCreateLobby;
    @BindView(R.id.MainMenuIconImage)
    ImageView MainMenuIconImage;

    private LobbyApiInteractor lobbyApiInteractor;
    private LobbyEntryPassFragment lobbyEntryPassFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);
        lobbyApiInteractor = new LobbyApiInteractor(this);
        MainMenuIconImage.setImageResource(R.drawable.ic_timeline_black_48dp);
    }

    @OnClick({R.id.MainMenuFindLobby, R.id.MainMenuCreateLobby})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.MainMenuFindLobby:
                lobbyApiInteractor.findLobbies();

                break;
            case R.id.MainMenuCreateLobby:

                new LobbyCreateDialog().setParent(this).show(this.getSupportFragmentManager(), "dialog");
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLobbies(Lobbies lobbyRestItem) {
        new LobbyListFragment().showDialog(this, lobbyRestItem);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onJunctions(JunctionsWrapper junctionsWrapper) {
        Log.e("Junction ", " megjött a lista !");
        Log.d("Lista első: ", ""+junctionsWrapper.junctions.get(0).getName());
        lobbyEntryPassFragment.setJunctions(junctionsWrapper.junctions);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPasswordResponse(PasswordResponse passwordResponse) {
        lobbyEntryPassFragment.setPasswordValidateOK(passwordResponse);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLobbyItemToJoin(LobbyRestItem lobbyRestItem) {

        new InsideLobbyFragment().setLobby(this,lobbyRestItem).show(this.getSupportFragmentManager(),"LobbyDialog");
    }





    @Override
    public void createThisLobby(LobbyCreatingPOST lobbyCreatingPOST) {
        lobbyApiInteractor.createLobby(lobbyCreatingPOST);
        Toast.makeText(this,"Lobby created",Toast.LENGTH_LONG).show();
    }

    @Override
    public void joinToThisLobby(LobbyRestItem lobbyRestItem) {

        Toast.makeText(this, "Joining lobby " + lobbyRestItem.getId(), Toast.LENGTH_LONG).show();

        lobbyEntryPassFragment = new LobbyEntryPassFragment();
        lobbyEntryPassFragment.setLobbyParent(lobbyRestItem,this).show(this.getSupportFragmentManager(),"PassDialog");
    }

    public void readyToJoinLobby(LobbyRestItem lobby, JunctionRestItem junction, String playerName){

        lobbyApiInteractor.loginToLobby(lobby.getId(),new Player(junction.getId(), playerName));
    }

    public void sendGPS(Integer lobbyId){
        lobbyApiInteractor.getAvailableJunctions(lobbyId, 47.0,19.0);
    }

    public void validatePassword(String password, LobbyRestItem lobby){
        lobbyApiInteractor.authToLobby(lobby.getId(), password);
    }

    public void leaveLobby(LobbyRestItem lobby, Player profile){
        lobbyApiInteractor.logoutFromLobby(lobby.getId(), profile.getId());
        Toast.makeText(this,"You left the lobby",Toast.LENGTH_LONG).show();
    }

    public void setPlayerReady(LobbyRestItem lobbyRestItem, Player profile){
        lobbyApiInteractor.sendImReady(lobbyRestItem.getId(),profile.getId());
    }


}
