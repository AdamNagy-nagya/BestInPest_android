package com.example.nagya.bestinpest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InsideLobbyActivity extends AppCompatActivity {

    @BindView(R.id.InsideLobby_lobbyname)
    TextView InsideLobbyLobbyname;
    @BindView(R.id.InsideLobby_lobbyleader)
    TextView InsideLobbyLobbyleader;
    @BindView(R.id.InsideLobby_PlayerName)
    TextView InsideLobbyPlayerName;
    @BindView(R.id.InsideLobby_PlayerJunction)
    TextView InsideLobbyPlayerJunction;
    @BindView(R.id.InsideLobby_PlayerIsMrX)
    TextView InsideLobbyPlayerIsMrX;
    @BindView(R.id.InsideLobby_Player1LinearLayout)
    LinearLayout InsideLobbyPlayer1LinearLayout;
    @BindView(R.id.InsideLobby_LeaveBtn)
    Button InsideLobbyLeaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_lobby);
        ButterKnife.bind(this);
    }




    @OnClick(R.id.InsideLobby_LeaveBtn)
    public void onViewClicked() {
    }
}
