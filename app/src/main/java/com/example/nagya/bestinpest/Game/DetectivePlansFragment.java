package com.example.nagya.bestinpest.Game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nagya.bestinpest.Game.item.GameObject;
import com.example.nagya.bestinpest.Game.item.Plan;
import com.example.nagya.bestinpest.Game.item.PlanswithPlayerItem;
import com.example.nagya.bestinpest.Game.item.Player;
import com.example.nagya.bestinpest.Lobby.LobbyCreateDialog;
import com.example.nagya.bestinpest.R;
import com.example.nagya.bestinpest.network.RouteNetwork.item.JunctionRestItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetectivePlansFragment extends Fragment {

    @BindView(R.id.Game_detectivePlans_makePlanBtn)
    Button PlansMakePlanBtn;
    Unbinder unbinder;
    private RecyclerView plansRV;
    private PlanAdapter plansAdapter;
    private GameObject gameObject;
    private Integer playerId;


    public DetectivePlansFragment() {
        // Required empty public constructor
    }

/*
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onJunction(JunctionRestItem junctionRestItem) {


    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_detectiveplans, container, false);
        plansRV = (RecyclerView) view.findViewById(R.id.Game_detectivePlans_listOfPlansRV);
        plansRV.setLayoutManager(new LinearLayoutManager(getContext()));
        plansAdapter = new PlanAdapter(this, gameObject, makeListFromHasMap(gameObject));
        plansRV.setAdapter(plansAdapter);
        //plansAdapter.update(makeListFromHasMap(gameObject));


        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void setupGameObject(GameObject gameObject) {
        this.gameObject = gameObject;

    }
    public void updateGameObject(GameObject gameObject){
        this.gameObject= gameObject;
        plansAdapter.update(makeListFromHasMap(gameObject));
    }

    public  void setUser(Integer myUserId){
        playerId=myUserId;
    }

    public List<PlanswithPlayerItem> makeListFromHasMap(GameObject gameObject) {

        List<PlanswithPlayerItem> planswithPlayerItemList = new ArrayList<>();
        if(gameObject.getDetectiveSteps()!=null) {
            if (!gameObject.getDetectiveSteps().isEmpty()) {
                List<Plan> planlist = new ArrayList<>(gameObject.getDetectiveSteps().get(gameObject.getRound() - 1).getPlans().values());
                List<Integer> playerIDlist = new ArrayList<>(gameObject.getDetectiveSteps().get(gameObject.getRound() - 1).getPlans().keySet());
                int cnt = 0;
                for (Integer i : playerIDlist) {
                    for (Player player : gameObject.getPlayers()) {
                        if (i.equals(player.getId())) {
                            planswithPlayerItemList.add(new PlanswithPlayerItem(player, planlist.get(cnt)));
                        }
                    }
                    cnt++;
                }
            }
        }
        return planswithPlayerItemList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Game_detectivePlans_makePlanBtn)
    public void onViewClicked() {
        GamePlanMakerFragment gamePlanMakerFragment = new GamePlanMakerFragment();

        gamePlanMakerFragment.setupData(gameObject, playerId);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.GameFrameLayout,gamePlanMakerFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


    public static class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {


        private List<PlanswithPlayerItem> mValues;
        private DetectivePlansFragment parent;
        private GameObject gameObject;


        public PlanAdapter(DetectivePlansFragment parent, GameObject gameObject, List<PlanswithPlayerItem> mValues) {

            this.gameObject = gameObject;
            this.parent = parent;
            this.mValues = mValues;
        }

        public void update(List newValues) {
            Log.e("ideért","yee");
            mValues.clear();
            mValues.addAll(newValues);
            notifyDataSetChanged();

        }




        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_game_detectiveplans, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.actualJunc.setText(mValues.get(position).plan.getDepartureJunctionId());
            holder.planedJunc.setText(mValues.get(position).plan.getArrivalJunctionId());
            holder.playerName.setText(mValues.get(position).player.getName());
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView playerName;
            final TextView actualJunc;
            final TextView planedJunc;
            final ImageButton editBtn;


            ViewHolder(View view) {
                super(view);
                playerName = view.findViewById(R.id.Game_detectivePlans_listitem_playerName);
                actualJunc = view.findViewById(R.id.Game_detectivePlans_listitem_actual);
                planedJunc = view.findViewById(R.id.Game_detectivePlans_listitem_planedJunc);
                editBtn = view.findViewById(R.id.Game_detectivePlans_listitem_editThisPlanBtn);


            }
        }

    }


}


