package com.example.nagya.bestinpest.Game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nagya.bestinpest.Game.item.GameObject;
import com.example.nagya.bestinpest.Game.item.Player;
import com.example.nagya.bestinpest.R;
import com.example.nagya.bestinpest.network.GameNetwork.GameApiInteractor;
import com.example.nagya.bestinpest.network.GameNetwork.item.DetectiveStepPOST;
import com.example.nagya.bestinpest.network.RouteNetwork.RouteApiInteractor;
import com.example.nagya.bestinpest.network.RouteNetwork.item.JunctionRestItem;
import com.example.nagya.bestinpest.network.RouteNetwork.item.JunctionsWrapper;
import com.example.nagya.bestinpest.network.RouteNetwork.item.Route;
import com.example.nagya.bestinpest.network.RouteNetwork.item.RouteWrapper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    GameApiInteractor gameApiInteractor;
    RouteApiInteractor routeApiInteractor;
    @BindView(R.id.Game_planmaker_routeSpiner)
    Spinner RouteSpiner;
    @BindView(R.id.Test_Player_plan_spinner)
    Spinner TestPlayerPlanSpinner;

    private GameObject gameObject;

    public GamePlanMakerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_plan_maker, container, false);
        unbinder = ButterKnife.bind(this, view);
        gameApiInteractor = new GameApiInteractor(getContext());
        routeApiInteractor = new RouteApiInteractor(getContext());
        GamePlanmakerJunctionstoGoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                JunctionRestItem junctionRestItem = (JunctionRestItem) GamePlanmakerJunctionstoGoSpinner.getSelectedItem();
                routeApiInteractor.getRoutesBetween(myUser.getJunctionId(), junctionRestItem.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    public void setupData(GameObject gameObject, Integer playerId) {
        this.gameObject = gameObject;
        for (Player player : gameObject.getPlayers()) {
            if (playerId.equals(player.getId())) {
                myUser = player;
            }
        }



        // GamePlanmakerActualpositionTV.setText(myUser.getJunctionId());
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        ArrayAdapter<Player> testPlayer = new ArrayAdapter<Player>(getContext(), R.layout.item_pass_junction, gameObject.getPlayers());
        testPlayer.setDropDownViewResource(R.layout.item_pass_junction);
        TestPlayerPlanSpinner.setAdapter(testPlayer);
        TestPlayerPlanSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Player seleectedPlayer = (Player) TestPlayerPlanSpinner.getSelectedItem();
                myUser= seleectedPlayer;

                gameApiInteractor.getAvailableJunctions(myUser.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //gameApiInteractor.getAvailableJunctions(myUser.getId());
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onJunctionsWrapper(JunctionsWrapper junctionsWrapper) {

        ArrayAdapter<JunctionRestItem> junctionRestItemArrayAdapter = new ArrayAdapter<JunctionRestItem>(getContext(), R.layout.item_pass_junction, junctionsWrapper.junctions);
        junctionRestItemArrayAdapter.setDropDownViewResource(R.layout.item_pass_junction);
        GamePlanmakerJunctionstoGoSpinner.setAdapter(junctionRestItemArrayAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRouteWrapper(RouteWrapper routeWrapper) {

        ArrayAdapter<Route> junctionRestItemArrayAdapter = new ArrayAdapter<Route>(getContext(), R.layout.item_pass_junction, routeWrapper.routes);
        junctionRestItemArrayAdapter.setDropDownViewResource(R.layout.item_pass_junction);
        RouteSpiner.setAdapter(junctionRestItemArrayAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Game_planmaker_sendThisPlanBTN)
    public void onViewClicked() {
        if (GamePlanmakerJunctionstoGoSpinner.getSelectedItem() != null && RouteSpiner.getSelectedItem() != null) {
            JunctionRestItem arrivalJunc = (JunctionRestItem) GamePlanmakerJunctionstoGoSpinner.getSelectedItem();
            Route planedRoute = (Route) RouteSpiner.getSelectedItem();
            gameApiInteractor.sendDetectivePlan(gameObject.getId(), new DetectiveStepPOST(arrivalJunc.getId(), myUser.getJunctionId(), myUser.getId(), planedRoute.getId()));
            getActivity().getSupportFragmentManager().popBackStack();

        }
    }
}
