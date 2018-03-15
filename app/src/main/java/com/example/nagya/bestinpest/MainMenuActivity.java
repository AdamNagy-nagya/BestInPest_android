package com.example.nagya.bestinpest;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nagya.bestinpest.Lobby.LobbyCreateDialog;
import com.example.nagya.bestinpest.Lobby.LobbyListFragment;
import com.example.nagya.bestinpest.Lobby.item.Lobbies;
import com.example.nagya.bestinpest.Lobby.item.LobbyCreatingPOST;
import com.example.nagya.bestinpest.Lobby.item.LobbyRestItem;
import com.example.nagya.bestinpest.network.LobbyNetwork.LobbyApiInteractor;

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

    @Override
    public void createThisLobby(LobbyCreatingPOST lobbyCreatingPOST) {
        lobbyApiInteractor.createLobby(lobbyCreatingPOST);
        Toast.makeText(this,"Lobby created",Toast.LENGTH_LONG).show();
    }

    @Override
    public void joinToThisLobby(LobbyRestItem lobbyRestItem) {

        Toast.makeText(this, "Joining lobby " + lobbyRestItem.getId(), Toast.LENGTH_LONG).show();

        InsideLobbyFragment insideLobbyfrag = new InsideLobbyFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.MainMenuContainer, insideLobbyfrag.newInstance(lobbyRestItem));
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
