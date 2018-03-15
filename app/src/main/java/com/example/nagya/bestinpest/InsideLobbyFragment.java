package com.example.nagya.bestinpest;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nagya.bestinpest.Lobby.item.LobbyRestItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class InsideLobbyFragment extends Fragment {


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
    Unbinder unbinder;

    private static LobbyRestItem joinedLobby;



    public InsideLobbyFragment() {

    }



    public static InsideLobbyFragment newInstance(LobbyRestItem lobbyRestItem) {
        InsideLobbyFragment fragment = new InsideLobbyFragment();
        joinedLobby = lobbyRestItem;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inside_lobby, container, false);
        unbinder = ButterKnife.bind(this, view);
        if(joinedLobby != null){
            InsideLobbyLobbyname.setText(joinedLobby.getName());
            InsideLobbyLobbyleader.setText(joinedLobby.getLeader().getName());
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
           }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.InsideLobby_LeaveBtn)
    public void onViewClicked() {
        getActivity().getSupportFragmentManager().popBackStack();
    }





}
