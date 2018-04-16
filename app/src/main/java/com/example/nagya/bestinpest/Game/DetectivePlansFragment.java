package com.example.nagya.bestinpest.Game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nagya.bestinpest.Game.item.GameObject;
import com.example.nagya.bestinpest.Game.item.Plan;
import com.example.nagya.bestinpest.Game.item.PlanswithPlayerItem;
import com.example.nagya.bestinpest.Game.item.Player;
import com.example.nagya.bestinpest.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetectivePlansFragment extends Fragment {

    private RecyclerView plansRV;
    private PlanAdapter plansAdapter;
    private GameObject gameObject;




    public DetectivePlansFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        plansRV = (RecyclerView) getActivity().findViewById(R.id.Game_detectivePlans_listOfPlansRV);
        plansRV.setLayoutManager(new LinearLayoutManager(getContext()));
        plansAdapter = new PlanAdapter(this,gameObject);


        return inflater.inflate(R.layout.fragment_game_detectiveplans, container, false);
    }

    public void setupGameObject(GameObject gameObject){
        this.gameObject= gameObject;


        plansAdapter.update(makeListFromHasMap(gameObject));

    }

    public List<PlanswithPlayerItem> makeListFromHasMap(GameObject gameObject){
        List<Plan> planlist= new ArrayList<>(gameObject.getDetectiveSteps().get(gameObject.getRound()).getPlans().values());
        List<Integer> playerIDlist=new ArrayList<>(gameObject.getDetectiveSteps().get(gameObject.getRound()).getPlans().keySet());
        List<PlanswithPlayerItem> planswithPlayerItemList= new ArrayList<>();
        int cnt=0;
        for (Integer i: playerIDlist) {
            for(Player player:gameObject.getPlayers()){
                if (i.equals(player.getId())){
                    planswithPlayerItemList.add(new PlanswithPlayerItem(player,planlist.get(cnt)));
                }
            }
            cnt++;
        }

        return planswithPlayerItemList;
    }



    public static class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {


        private List<PlanswithPlayerItem> mValues;
        private DetectivePlansFragment parent;
        private GameObject gameObject;




        public PlanAdapter(DetectivePlansFragment parent, GameObject gameObject){

            this.gameObject= gameObject;
            this.parent= parent;
        }

        public void update(List newValues){
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


