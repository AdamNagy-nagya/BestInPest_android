package com.example.nagya.bestinpest.Lobby;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;

import com.example.nagya.bestinpest.Junction.item.JunctionRestItem;
import com.example.nagya.bestinpest.Lobby.item.LobbyRestItem;
import com.example.nagya.bestinpest.MainMenuActivity;
import com.example.nagya.bestinpest.R;

import java.util.List;

/**
 * Created by nagya on 16/03/2018.
 */

public class LobbyEntryNoPassFragment extends DialogFragment {


    private List<JunctionRestItem> junctionRestItems;
    private ImageButton sendGPSforJunction;
    private MainMenuActivity parent;
    private LobbyRestItem lobby;

    public void setLobby(LobbyRestItem lobby){this.lobby = lobby;}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_lobby_entry_pass, null);

        sendGPSforJunction = view.findViewById(R.id.PasswordFrag_GPSButton);
        sendGPSforJunction.setImageResource(R.drawable.ic_my_location_black_24dp);
        sendGPSforJunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent.sendGPS(lobby.getId());
            }
        });
        // ArrayAdapter<JunctionRestItem> junctionRestItemArrayAdapter = new ArrayAdapter<JunctionRestItem>(getContext(),R.layout.item_pass_junction,junctionRestItems);
        //  junctionRestItemArrayAdapter.setDropDownViewResource(R.layout.item_pass_junction);

        builder.setMessage("Password")
                .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }


    public void setJunctions(List<JunctionRestItem> junctions) {
        junctionRestItems = junctions;
    }
}

