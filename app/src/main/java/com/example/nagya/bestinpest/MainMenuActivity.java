package com.example.nagya.bestinpest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nagya.bestinpest.Lobby.LobbyListFragment;
import com.example.nagya.bestinpest.Lobby.item.Lobbies;
import com.example.nagya.bestinpest.Lobby.LobbyCreateDialog;
import com.example.nagya.bestinpest.Lobby.item.LobbyCreatingPOST;
import com.example.nagya.bestinpest.network.LobbyNetwork.LobbyApiInteractor;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMenuActivity extends AppCompatActivity implements LobbyCreateDialog.createLobby {

    @BindView(R.id.MainMenuFindLobby)
    Button MainMenuFindLobby;
    @BindView(R.id.MainMenuCreateLobby)
    Button MainMenuCreateLobby;
    @BindView(R.id.TestTV)
    TextView TestTV;

    private LobbyApiInteractor lobbyApiInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);
        lobbyApiInteractor = new LobbyApiInteractor(this);
    }

    @OnClick({R.id.MainMenuFindLobby, R.id.MainMenuCreateLobby})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.MainMenuFindLobby:
                lobbyApiInteractor.deleteLobby(205);
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
        new LobbyListFragment().showDialog(this,lobbyRestItem);
        TestTV.setText( lobbyRestItem.lobbies.get(0).getName());
    }

    @Override
    public void createThisLobby(LobbyCreatingPOST lobbyCreatingPOST) {
        lobbyApiInteractor.createLobby(lobbyCreatingPOST);
        TestTV.setText(lobbyCreatingPOST.getName());
    }
}
