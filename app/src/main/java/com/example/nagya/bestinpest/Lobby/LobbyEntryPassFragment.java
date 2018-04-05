package com.example.nagya.bestinpest.Lobby;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nagya.bestinpest.network.RouteNetwork.item.JunctionRestItem;
import com.example.nagya.bestinpest.Lobby.item.LobbyRestItem;
import com.example.nagya.bestinpest.MainMenuActivity;
import com.example.nagya.bestinpest.R;
import com.example.nagya.bestinpest.network.LobbyNetwork.item.PasswordResponse;

import java.util.List;

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
    private boolean passwordTyedinOk;

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
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                parent.validatePassword(passwordEditText.getText().toString(),lobby);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        passwordResponseImg = view.findViewById(R.id.PasswordFrag_passwordOK_img);
        junctionSpiner = view.findViewById(R.id.PasswordFrag_StartSpiner);



        builder.setMessage("Password")
                .setView(view)
                .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(passwordTyedinOk){
                        parent.readyToJoinLobby(lobby,(JunctionRestItem) junctionSpiner.getSelectedItem(),playerNameEditText.getText().toString());
                        }
                        else{
                            Toast.makeText(getContext(),"Invalid password, can't log in!", Toast.LENGTH_LONG);}
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
            passwordTyedinOk= false;
        }
        else if (!lobby.getPasswordSet()){
            passwordContener.setVisibility(View.INVISIBLE);
            passwordTyedinOk= true;

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
            passwordTyedinOk = true;

        }
        else {
            passwordResponseImg.setImageResource((R.drawable.ic_close_black_24dp));
            passwordTyedinOk = false;


        }
    }
}

