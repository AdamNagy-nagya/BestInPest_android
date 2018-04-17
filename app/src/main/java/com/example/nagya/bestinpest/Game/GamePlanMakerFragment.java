package com.example.nagya.bestinpest.Game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nagya.bestinpest.Game.item.GameObject;
import com.example.nagya.bestinpest.Game.item.Player;
import com.example.nagya.bestinpest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class GamePlanMakerFragment extends Fragment {


    @BindView(R.id.Game_planmaker_actualpositionTV)
    TextView GamePlanmakerActualpositionTV;
    @BindView(R.id.Game_planmaker_junctionstoGoSpinner)
    Spinner GamePlanmakerJunctionstoGoSpinner;
    @BindView(R.id.Game_planmaker_sendThisPlanBTN)
    Button GamePlanmakerSendThisPlanBTN;
    Unbinder unbinder;

    Player myUser;

    public GamePlanMakerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_plan_maker, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void setupData(GameObject gameObject, Integer playerId){
        for(Player player: gameObject.getPlayers()){
            if(playerId == player.getId()){
                myUser = player;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Game_planmaker_sendThisPlanBTN)
    public void onViewClicked() {
    }
}
