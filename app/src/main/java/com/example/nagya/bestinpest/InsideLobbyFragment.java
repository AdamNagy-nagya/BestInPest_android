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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class InsideLobbyFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
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


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public InsideLobbyFragment() {

    }

    //TODO valós lobby külön, adatok átdobása

    public static InsideLobbyFragment newInstance(String param1, String param2) {
        InsideLobbyFragment fragment = new InsideLobbyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inside_lobby, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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




    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
