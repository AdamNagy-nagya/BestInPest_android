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
import com.example.nagya.bestinpest.network.GameNetwork.item.CriminalStepPOST;
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
public class CriminalPlanFragment extends Fragment {

    @BindView(R.id.Game_criminal_actualpozTV)
    TextView ActualpozTV;
    @BindView(R.id.Game_criminal_junctionstoGoSpinner)
    Spinner JunctionstoGoSpinner;
    @BindView(R.id.Game_criminal_routeSpiner)
    Spinner RouteSpiner;
    @BindView(R.id.Game_criminal_sendThisPlanBTN)
    Button SendThisPlanBtn;
    Unbinder unbinder;
    private GameObject gameObject;
    private Integer playerId;

    private Player criminal;

    Player myUser;
    GameApiInteractor gameApiInteractor;
    RouteApiInteractor routeApiInteractor;


    public CriminalPlanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_criminal_plan, container, false);
        gameApiInteractor = new GameApiInteractor(getContext());
        routeApiInteractor = new RouteApiInteractor(getContext());
        unbinder = ButterKnife.bind(this, view);

        JunctionstoGoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                JunctionRestItem junctionRestItem=(JunctionRestItem) JunctionstoGoSpinner.getSelectedItem();


                //TODO !!! ITT IS MÁSIK ID KELL!!!
                routeApiInteractor.getRoutesBetween(myUser.getJunctionId(),junctionRestItem.getId());


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    public void setupGameObject(GameObject gameObject) {
        this.gameObject = gameObject;

    }

    public void updateGameObject(GameObject gameObject) {
        this.gameObject = gameObject;

    }

    public void setUser(Integer myUserId) {
        playerId = myUserId;

        //TODO Test miatt itt nem valós az ID!!!
        for (Player player: gameObject.getPlayers()){
            if(gameObject.getCriminalId().equals(player.getId())){
                myUser= player;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        //TODO !!!! ITT NEM EZ AZ ID VAN!!! csak a test miatt!!!
        gameApiInteractor.getAvailableJunctions(gameObject.getCriminalId());
        ActualpozTV.setText(myUser.getJunctionId());
        //!!!!!
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

    @OnClick(R.id.Game_criminal_sendThisPlanBTN)
    public void onViewClicked() {
        if(JunctionstoGoSpinner.getSelectedItem()!=null && RouteSpiner.getSelectedItem()!=null){
            JunctionRestItem arrivalJunc= (JunctionRestItem) JunctionstoGoSpinner.getSelectedItem();
            Route planedRoute = (Route) RouteSpiner.getSelectedItem();
            gameApiInteractor.sendCriminalStep(gameObject.getId(),new CriminalStepPOST(arrivalJunc.getId(),myUser.getJunctionId(),planedRoute.getId()));
           // getActivity().getSupportFragmentManager().popBackStackImmediate();

        }

    }
}
