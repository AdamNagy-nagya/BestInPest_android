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
import com.example.nagya.bestinpest.network.GameNetwork.item.StepRecommendation;
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
public class DetectiveRecommendationMakeFragment extends Fragment {


    @BindView(R.id.Game_recommendation_actualpositionTV)
    TextView ActualpositionTV;
    @BindView(R.id.Game_recommendation_junctionstoGoSpinner)
    Spinner JunctionstoGoSpinner;
    @BindView(R.id.Game_recommendation_routeSpiner)
    Spinner RouteSpiner;
    @BindView(R.id.Game_recommendation_sendThisPlanBTN)
    Button SendThisBtn;
    Unbinder unbinder;


    Player myUser;
    Integer myRealPlayerId;
    GameApiInteractor gameApiInteractor;
    RouteApiInteractor routeApiInteractor;
    private GameObject gameObject;


    public DetectiveRecommendationMakeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detective_recommendation_make, container, false);
        unbinder = ButterKnife.bind(this, view);

        gameApiInteractor = new GameApiInteractor(getContext());
        routeApiInteractor = new RouteApiInteractor(getContext());
        JunctionstoGoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                JunctionRestItem junctionRestItem = (JunctionRestItem) JunctionstoGoSpinner.getSelectedItem();
                routeApiInteractor.getRoutesBetween(myUser.getJunctionId(), junctionRestItem.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        return view;
    }

    public void setupData(GameObject gameObject, Integer playertoId, Integer myPlayerId) {
        this.gameObject = gameObject;
        for (Player player : gameObject.getPlayers()) {
            if (playertoId.equals(player.getId())) {
                myUser = player;
            }
        }
        myRealPlayerId = myPlayerId;
         // GamePlanmakerActualpositionTV.setText(myUser.getJunctionId());
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        gameApiInteractor.getAvailableJunctions(myUser.getId());
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
        JunctionstoGoSpinner.setAdapter(junctionRestItemArrayAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRouteWrapper(RouteWrapper routeWrapper) {

        ArrayAdapter<Route> junctionRestItemArrayAdapter = new ArrayAdapter<Route>(getContext(), R.layout.item_pass_junction, routeWrapper.routes);
        junctionRestItemArrayAdapter.setDropDownViewResource(R.layout.item_pass_junction);
        RouteSpiner.setAdapter(junctionRestItemArrayAdapter);
    }

    @OnClick(R.id.Game_recommendation_sendThisPlanBTN)
    public void onViewClicked() {

        if (JunctionstoGoSpinner.getSelectedItem() != null && RouteSpiner.getSelectedItem() != null) {
            JunctionRestItem arrivalJunc = (JunctionRestItem) JunctionstoGoSpinner.getSelectedItem();
            Route planedRoute = (Route) RouteSpiner.getSelectedItem();
            gameApiInteractor.sendDetectiveRecommedation(gameObject.getId(),
                    new StepRecommendation(arrivalJunc.getId(), myUser.getJunctionId(), myUser.getId(),myRealPlayerId));



            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}
