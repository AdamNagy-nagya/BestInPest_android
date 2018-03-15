package com.example.nagya.bestinpest.Lobby;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.example.nagya.bestinpest.Lobby.item.Leader;
import com.example.nagya.bestinpest.Lobby.item.Player;
import com.example.nagya.bestinpest.Lobby.item.LobbyCreatingPOST;
import com.example.nagya.bestinpest.MainMenuActivity;
import com.example.nagya.bestinpest.R;

/**
 * Created by nagya on 27/02/2018.
 */

public class LobbyCreateDialog  extends DialogFragment {

    EditText lobbyName;
    EditText lobbyPassword;
    EditText lobbyPlayerNumber;
    createLobby parent;

    public LobbyCreateDialog setParent(MainMenuActivity parent){
        this.parent= parent;
        return this;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_lobbycreate, null);
        lobbyName = view.findViewById(R.id.CreateLobbyNameEditText);
        lobbyPassword = view.findViewById(R.id.CreateLobbyPasswordEditText);
        lobbyPlayerNumber =view.findViewById(R.id.CreateLobbyPlayerNumber);



        builder.setMessage("Create a new lobby")
                .setView(view)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                            parent.createThisLobby(new LobbyCreatingPOST(
                                    Integer.parseInt(lobbyPlayerNumber.getText().toString()),
                                    lobbyName.getText().toString(),
                                    readPassword(),
                                    new Leader("BKK_CSF01108","Adam")));
                       }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }

    private String readPassword(){
        if (lobbyPassword.getText().length()!=0)
        return lobbyPassword.getText().toString();
        else return null;
    }

    public interface createLobby{
        public void createThisLobby(LobbyCreatingPOST lobbyCreatingPOST);
    }
}

