package com.example.nagya.bestinpest.Lobby;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nagya.bestinpest.Lobby.item.LobbyRestItem;
import com.example.nagya.bestinpest.Lobby.item.Player;
import com.example.nagya.bestinpest.MainMenuActivity;
import com.example.nagya.bestinpest.R;
import com.example.nagya.bestinpest.network.RabbitMq.item.InsideLobbyRabbitMqItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;


public class InsideLobbyFragment extends DialogFragment {


    private TextView LobbyLeader;
    private Button LeaveBtn;
    private Button ReadyBtn;
    private RecyclerView recyclerView;
    private Button StartGameBtn;

    private PlayerAdapter recyclerAdapter;


    private MainMenuActivity parent;
    private LobbyRestItem lobby;
    private Player myProfile;
    private boolean isLeaderViewOn;


    public static InsideLobbyFragment newInstance(int title) {
        InsideLobbyFragment frag = new InsideLobbyFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }


    public InsideLobbyFragment setLobby(MainMenuActivity activity, LobbyRestItem lobby) {
        parent =  activity;
        this.lobby = lobby;
        myProfile= lobby.getPlayers().get(lobby.getPlayers().size()-1);

        if(lobby.getLeader().getId().equals( myProfile.getId())){
            isLeaderViewOn = true;
        }
        else {
            isLeaderViewOn= false;
        }
        return this;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_inside_lobby, null);
        recyclerView =  view.findViewById(R.id.InsideLobby_PlayerListRV);
        LobbyLeader = view.findViewById(R.id.InsideLobby_lobbyleader);
        LobbyLeader.setText("Leader is "+lobby.getLeader().getName());
        LeaveBtn = view.findViewById(R.id.InsideLobby_LeaveBtn);
        ReadyBtn = view.findViewById(R.id.InsideLobby_ReadyBtn);
        StartGameBtn = view.findViewById(R.id.InsideLobby_StartGameBtn);

        LeaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getDialog().dismiss();


            }
        });
        ReadyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent.setPlayerReady(lobby,myProfile);
                myProfile.setReady(!myProfile.getReady());
            }
        });

        if(!isLeaderViewOn){
            StartGameBtn.setVisibility(View.INVISIBLE);
        }
        else{
            StartGameBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean allReady = true;
                    Log.e("gomb megnyomva","START");
                    for(Player player: lobby.getPlayers()){
                        if(!player.getReady()){
                            allReady=false;
                        }
                    }
                    if(lobby.getPlayers().size()>2 && allReady){
                        parent.startGameSend(lobby.getId());
                        Log.e("oké", "mehet");
                    }
                }
            });
        }



        recyclerView.setLayoutManager(new LinearLayoutManager(parent));
        assert recyclerView != null;
        recyclerAdapter = new PlayerAdapter(lobby, this, isLeaderViewOn);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        registerForContextMenu(recyclerView);

        builder.setView(view).setTitle(lobby.getName());


        return builder.create();
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        if(isLeaderViewOn){
            parent.deleteLobby(lobby.getId());
        }
        parent.leaveLobby(lobby,myProfile);
        EventBus.getDefault().unregister(this);
        super.onStop();
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLobbyItem(InsideLobbyRabbitMqItem insideLobbyRabbitMqItem) {

        if(insideLobbyRabbitMqItem.getType().equals("lobby-deleted")){
            getDialog().dismiss();

        }
       lobby= insideLobbyRabbitMqItem.getObject();
       Log.e("ideért",insideLobbyRabbitMqItem.getMessage());
       recyclerAdapter.update(insideLobbyRabbitMqItem.getObject());

    }


    public static class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {


        private final List<Player> mValues;
        private  InsideLobbyFragment parent;
        private LobbyRestItem lobby;
        private boolean isLeaderView;



        public PlayerAdapter(LobbyRestItem lobby, InsideLobbyFragment parent, boolean isLeaderView){
            this.mValues= lobby.getPlayers();
            this.lobby= lobby;
            this.parent= parent;
            this.isLeaderView = isLeaderView;

        }

        public void update(LobbyRestItem lobbyRestItem){
            this.lobby = lobbyRestItem;
            mValues.clear();
            mValues.addAll(lobbyRestItem.getPlayers());
            notifyDataSetChanged();


        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_insidelobby_player, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.PlayerName.setText(mValues.get(position).getName());
            holder.where.setText(mValues.get(position).getJunctionId());

            if(mValues.get(position).getReady()){
               // Log.e("READY?: ", mValues.get(position).getReady()? "TRUE": "FALSE");
                holder.ReadyImg.setImageResource(R.drawable.ic_done_black_24dp);
                holder.ReadyImg.setVisibility(View.VISIBLE);
            }
            else {
                holder.ReadyImg.setVisibility(View.INVISIBLE);
            }

            holder.itemView.setTag(mValues.get(position));



            if(mValues.get(position).getId().equals(lobby.getCriminal())){
               // holder.isMrXImg.setVisibility(View.VISIBLE);
              //  holder.isNotMrXImg.setVisibility(View.INVISIBLE);
                holder.isMrXImg.setImageResource(R.drawable.ic_person_pin_black_24dp);

            }
            else{
                holder.isMrXImg.setImageResource(R.drawable.ic_person_outline_black_24dp);
                //holder.isMrXImg.setVisibility(View.INVISIBLE);
               // holder.isNotMrXImg.setVisibility(View.VISIBLE);

            }

            if(isLeaderView){

                holder.setMrXBtn.setVisibility(View.VISIBLE);
                holder.setMrXBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        parent.parent.setPlayerCriminal(lobby,mValues.get(position));
                    }
                });


            }else {

                holder.setMrXBtn.setVisibility(View.INVISIBLE);
            }



        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }






        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView PlayerName;
            final TextView where;
            final ImageView ReadyImg;
            final ImageView isMrXImg;
            final ImageView isNotMrXImg;
            final Button setMrXBtn;




            ViewHolder(View view) {
                super(view);
                PlayerName = view.findViewById(R.id.InsideLobby_PlayerName);
                where = view.findViewById(R.id.InsideLobby_PlayerJunction);
                ReadyImg = view.findViewById(R.id.InsideLobby_PlayerReady);
                isMrXImg = view.findViewById(R.id.InsideLobby_isMrXImg);
                isNotMrXImg = view.findViewById(R.id.InsideLobby_isNotMrXImg);
                setMrXBtn = view.findViewById(R.id.InsideLobby_setMrXBtn);



            }
        }

    }


}
