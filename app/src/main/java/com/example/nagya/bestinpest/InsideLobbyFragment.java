package com.example.nagya.bestinpest;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nagya.bestinpest.Lobby.item.LobbyRestItem;
import com.example.nagya.bestinpest.Lobby.item.Player;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class InsideLobbyFragment extends DialogFragment {


    private  TextView LobbyLeader;
    private  Button LeaveBtn;
    private RecyclerView recyclerView;

    private PlayerAdapter recyclerAdapter;

    private MainMenuActivity parent;
    private LobbyRestItem lobby;


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

        LeaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
                //TODO logout lobby

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(parent));
        assert recyclerView != null;
        recyclerAdapter = new PlayerAdapter(lobby.getPlayers(), this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        registerForContextMenu(recyclerView);

        builder.setView(view).setTitle(lobby.getName());


        return builder.create();
    }

    public static class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {


        private final List<Player> mValues;
        private  InsideLobbyFragment parent;



        public PlayerAdapter(List<Player> mValues, InsideLobbyFragment parent){
            this.mValues= mValues;
            this.parent= parent;

        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_insidelobby_player, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.PlayerName.setText(mValues.get(position).getName());
           // holder.IsMrX.setText(mValues.get(position).getId());
            holder.ReadyImg.setImageResource(R.drawable.ic_done_black_24dp);
            if(mValues.get(position).getReady()){
                holder.ReadyImg.setVisibility(View.VISIBLE);
            }
            else {
                holder.ReadyImg.setVisibility(View.INVISIBLE);
            }
            holder.itemView.setTag(mValues.get(position));


        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }






        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView PlayerName;
            final TextView IsMrX;
            final TextView where;
            final ImageView ReadyImg;



            ViewHolder(View view) {
                super(view);
                PlayerName = view.findViewById(R.id.InsideLobby_PlayerName);
                IsMrX= view.findViewById(R.id.InsideLobby_PlayerIsMrX);
                where = view.findViewById(R.id.InsideLobby_PlayerJunction);
                ReadyImg = view.findViewById(R.id.InsideLobby_PlayerReady);

            }
        }

    }


}
