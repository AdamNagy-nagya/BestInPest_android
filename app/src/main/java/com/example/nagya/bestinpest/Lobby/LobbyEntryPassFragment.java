package com.example.nagya.bestinpest.Lobby;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.nagya.bestinpest.Junction.item.JunctionRestItem;
import com.example.nagya.bestinpest.Lobby.item.LobbyRestItem;
import com.example.nagya.bestinpest.MainMenuActivity;
import com.example.nagya.bestinpest.R;
import com.example.nagya.bestinpest.network.LobbyNetwork.item.PasswordResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LobbyEntryPassFragment extends DialogFragment {


    private MainMenuActivity parent;
    private LobbyRestItem lobby;

    private LinearLayout passwordContener;
    private List<JunctionRestItem> junctionRestItems;
    private ImageButton sendGPSforJunction;
    private EditText passwordEditText;
    private EditText playerNameEditText;
    private ImageView passwordResponseImg;
    private Spinner junctionSpiner;

    public LobbyEntryPassFragment setLobbyParent(LobbyRestItem lobby, MainMenuActivity parent){
        this.lobby = lobby;
        this.parent = parent;
        return this;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_lobby_entry_pass, null);
        playerNameEditText = view.findViewById(R.id.PasswordFrag_UsernameEditText);
        sendGPSforJunction= view.findViewById(R.id.PasswordFrag_GPSButton);
        sendGPSforJunction.setImageResource(R.drawable.ic_my_location_black_24dp);
        sendGPSforJunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               parent.sendGPS(lobby.getId());
            }
        });
        passwordContener = view.findViewById(R.id.PasswordFrag_container);
        passwordEditText = view.findViewById(R.id.PasswordFrag_password);
        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!view.hasFocus()){
                    Log.d("FOCUS", passwordEditText.getText().toString());
                    parent.validatePassword(passwordEditText.getText().toString(),lobby);
                }
            }
        });
        passwordResponseImg = view.findViewById(R.id.PasswordFrag_passwordOK_img);
        junctionSpiner = view.findViewById(R.id.PasswordFrag_StartSpiner);


        builder.setMessage("Password")
                .setView(view)
                .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        parent.readyToJoinLobby(lobby,(JunctionRestItem) junctionSpiner.getSelectedItem(),playerNameEditText.getText().toString());
                    }
                })
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(lobby.getPasswordSet()){
            passwordContener.setVisibility(View.VISIBLE);
        }
        else if (!lobby.getPasswordSet()){
            passwordContener.setVisibility(View.INVISIBLE);
        }
    }

    public void setJunctions(List<JunctionRestItem> junctions){
        junctionRestItems= junctions;

         ArrayAdapter<JunctionRestItem> junctionRestItemArrayAdapter = new ArrayAdapter<JunctionRestItem>(getContext(),R.layout.item_pass_junction,junctionRestItems);
         junctionRestItemArrayAdapter.setDropDownViewResource(R.layout.item_pass_junction);
         junctionSpiner.setAdapter(junctionRestItemArrayAdapter);


    }

    public void setPasswordValidateOK(PasswordResponse response){
        if(response.isPasswordOK()){
            passwordResponseImg.setImageResource(R.drawable.ic_done_black_24dp);
            passwordEditText.setVisibility(View.INVISIBLE);
            passwordEditText.setEnabled(false);

        }
        else {
            passwordEditText.setText(null);
            passwordEditText.setHint("Wrong password");

        }
    }
}

