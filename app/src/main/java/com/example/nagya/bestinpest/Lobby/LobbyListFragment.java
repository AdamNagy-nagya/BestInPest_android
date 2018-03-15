package com.example.nagya.bestinpest.Lobby;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nagya.bestinpest.Lobby.item.Lobbies;
import com.example.nagya.bestinpest.Lobby.item.LobbyRestItem;
import com.example.nagya.bestinpest.MainMenuActivity;
import com.example.nagya.bestinpest.R;

import java.util.List;


public class LobbyListFragment extends DialogFragment{
    private RecyclerView recyclerView;
    private LobbyListAdapter recyclerAdapter;
    private MainMenuActivity parent;
    private Lobbies resultSerieList;


    public static LobbyListFragment newInstance(int title) {
        LobbyListFragment frag = new LobbyListFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }


    public void showDialog(MainMenuActivity activity, Lobbies list) {
        parent =  activity;

        resultSerieList = list;
        show(parent.getSupportFragmentManager(), "dialog");
    }

    private void JoinLobby(int lobbyId){
        parent.joinToThisLobby(lobbyId);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_lobby_list, null);
        recyclerView =  view.findViewById(R.id.LobbyListRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(parent));
        assert recyclerView != null;
        recyclerAdapter = new LobbyListAdapter(resultSerieList.lobbies, this);
        recyclerView.setAdapter(recyclerAdapter);
        registerForContextMenu(recyclerView);

        builder.setView(view).setTitle("Active lobbies");


        return builder.create();
    }









    public static class LobbyListAdapter extends RecyclerView.Adapter<LobbyListAdapter.ViewHolder> {


        private final List<LobbyRestItem> mValues;
        private  LobbyListFragment parent;



        public LobbyListAdapter(List<LobbyRestItem> mValues, LobbyListFragment parent){
            this.mValues= mValues;
            this.parent= parent;

        }



        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_lobbylist, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            if(mValues.get(position).getLeader() != null)
                {
                holder.LobbyLeaderTV.setText(mValues.get(position).getLeader().getName());
                }

            holder.LobbyNameTV.setText(mValues.get(position).getName());
            if(mValues.get(position).getMaxPlayerNumber() != null){
              holder.LobbyMaxPlayer.setText(mValues.get(position).getMaxPlayerNumber().toString());
            }
            if(mValues.get(position).getPlayers() != null ) {
                if( mValues.get(position).getPlayers().size()!=0){
                //TODO aktív játékosok számának kiírása
             //   holder.LobbyPlayers.setText(
                    //                   mValues.get(position)
                    //                    .getPlayers()
                    //            .size());
                }
            }

            holder.itemView.setTag(mValues.get(position));
            if(mValues.get(position).getPasswordSet()) {
                holder.LockImg.setImageResource(R.drawable.ic_lock_black_24dp);

            }

            holder.JoinBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    parent.JoinLobby(mValues.get(position).getId());
                    parent.dismiss();
                }
            });

        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            final TextView LobbyNameTV;
            final TextView LobbyLeaderTV;
            final TextView LobbyMaxPlayer;
            final TextView LobbyPlayers;
            final Button JoinBtn;
            final ImageView LockImg;



            ViewHolder(View view) {
                super(view);
                LobbyNameTV = view.findViewById(R.id.item_lobbylistLobbyNameText);
                LobbyLeaderTV = view.findViewById(R.id.item_lobbylistLobbyLeaderText);
                LobbyMaxPlayer = view.findViewById(R.id.item_lobbylistMaxPlayer);
                LobbyPlayers = view.findViewById(R.id.item_lobbylistPlayersinLobby);
                JoinBtn = view.findViewById(R.id.item_lobbylistJoinBtn);
                LockImg = view.findViewById(R.id.item_lobbylistLockImage);


            }
        }

    }
    public interface joinLobbyInterface{
        public void joinToThisLobby(int lobbyId);
    }

}
