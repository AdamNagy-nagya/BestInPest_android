package com.example.nagya.bestinpest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nagya.bestinpest.Game.CriminalPlanFragment;
import com.example.nagya.bestinpest.Game.DetectivePlansFragment;
import com.example.nagya.bestinpest.Game.GameLoadBlankFragment;
import com.example.nagya.bestinpest.Game.GameMapFragment;
import com.example.nagya.bestinpest.Game.item.CriminalStep;
import com.example.nagya.bestinpest.Game.item.GameObject;
import com.example.nagya.bestinpest.network.GameNetwork.GameApiInteractor;
import com.example.nagya.bestinpest.network.RabbitMq.GameRabbitMq;
import com.example.nagya.bestinpest.network.RabbitMq.item.GameRabbitMqItem;
import com.example.nagya.bestinpest.spdb.SharedPreferencesHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity {

    @BindView(R.id.GameGamestatusTV)
    TextView GameGamestatusTV;
    @BindView(R.id.GameRoundNumberTV)
    TextView GameRoundNumberTV;
    @BindView(R.id.GameMrXpastSteps)
    RecyclerView CriminalStepRV;
    @BindView(R.id.GameFrameLayout)
    FrameLayout GameFrameLayout;
    CriminalStepAdapter criminalStepAdapter;
    @BindView(R.id.ShowMapBtn)
    Button ShowMapBtn;

    private String state;
    private GameObject gameObject;
    private GameApiInteractor gameApiInteractor;
    protected Integer gameID;
    protected Integer myPlayerID;
    private String rabbitMqURL;

    private DetectivePlansFragment detectivePlansFragment;
    private CriminalPlanFragment criminalPlanFragment;
    private GameLoadBlankFragment blankFragment;
    private GameMapFragment mapFragment;
    private GameRabbitMq gameRabbitMq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);

        SharedPreferencesHandler sharedPreferencesHandler = new SharedPreferencesHandler(this);

        gameID = sharedPreferencesHandler.loadGameID();
        myPlayerID = sharedPreferencesHandler.loadPlayerID();
        rabbitMqURL = sharedPreferencesHandler.loadRabbitMqUrl();

        if (myPlayerID == -1 && gameID == -1) {
            try {
                throw new InvalidClassException("Nincs meg a playerID vagy a GameId!");
            } catch (InvalidClassException e) {
                e.printStackTrace();
            }
        }

        CriminalStepRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        criminalStepAdapter = new CriminalStepAdapter(this);
        CriminalStepRV.setAdapter(criminalStepAdapter);

        gameApiInteractor = new GameApiInteractor(this);
        detectivePlansFragment = new DetectivePlansFragment();
        criminalPlanFragment = new CriminalPlanFragment();
        blankFragment = new GameLoadBlankFragment();
        mapFragment = new GameMapFragment();

        CriminalStepRV.getRootView().setBackgroundColor(getResources().getColor(R.color.asphaltGray));

        getSupportFragmentManager().beginTransaction().add(R.id.GameFrameLayout, blankFragment).commit();
    }


    @Override
    public void onStart() {
        super.onStart();
        gameRabbitMq = new GameRabbitMq(rabbitMqURL, gameID);
        EventBus.getDefault().register(this);
        gameApiInteractor.getGameObjectById(gameID);
    }

    @Override
    public void onStop() {
        gameRabbitMq.stopGameRabbitMq();
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
        setupViewStuff();
        criminalStepAdapter.update(gameObject.getCriminalSteps());
        Log.e("Game found", "YEE");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRabbitMessage(GameRabbitMqItem gameRabbitMqItem) {
        this.gameObject = gameRabbitMqItem.getObject();
        switch (gameRabbitMqItem.getType()) {
            case "turn-changed":
                setupViewStuff();
                break;

            case "game-ended":
                blankFragment.setGameEndCriminalWins();
                getSupportFragmentManager().beginTransaction().replace(R.id.GameFrameLayout, blankFragment).commit();
                break;

            case "detectives-step":
                break;

            case "detective-plan":
                refreshFragmentData();
                break;


            case "criminal-step":
                Toast.makeText(this, "The criminal stepped", Toast.LENGTH_LONG).show();
                criminalStepAdapter.update(gameObject.getCriminalSteps());
                break;

            case "plan-approved":
                Toast.makeText(this, "Plan approved", Toast.LENGTH_LONG).show();
                break;
            case "invalid-step":
                Toast.makeText(this, "Invalid step!", Toast.LENGTH_LONG).show();
                break;

            case "criminal-caught":
                blankFragment.setGameEndDetectiveWins();
                getSupportFragmentManager().beginTransaction().replace(R.id.GameFrameLayout, blankFragment).commit();
                break;

            case "game-removed":
                Toast.makeText(this, "Game removed", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MainMenuActivity.class);
                startActivity(intent);
                break;

            case "plan-reaction":
                break;
            case "recommendation-sent":
                break;
            case "recommendation-removed":
                break;
            case "player-ready":
                break;


        }


    }


    private void refreshFragmentData() {
        detectivePlansFragment.updateGameObject(gameObject);
    }

    private void setupViewStuff() {
        if (gameObject != null) {
            GameGamestatusTV.setText(gameObject.getTurn());
            GameRoundNumberTV.setText(gameObject.getRound() + ".");
            state = gameObject.getTurn();

            switch (state) {
                case "criminal":    //getSupportFragmentManager().beginTransaction().remove(detectivePlansFragment);

                    criminalPlanFragment.setupGameObject(gameObject);
                    criminalPlanFragment.setUser(myPlayerID);
                    getSupportFragmentManager().beginTransaction().replace(R.id.GameFrameLayout, criminalPlanFragment).commit();
                                    /*getSupportFragmentManager().beginTransaction()
                                    .add(R.id.GameFrameLayout, criminalPlanFragment).commit();*/
                    break;
                case "detectives":  //getSupportFragmentManager().beginTransaction().remove(criminalPlanFragment);
                    detectivePlansFragment.setupGameObject(gameObject);
                    detectivePlansFragment.setUser(myPlayerID);
                    //getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager().beginTransaction().replace(R.id.GameFrameLayout, detectivePlansFragment).commit();

                    // getSupportFragmentManager().beginTransaction()
                    // .add(R.id.GameFrameLayout, detectivePlansFragment).commit();
                    break;
            }
        }
    }

    @OnClick(R.id.ShowMapBtn)
    public void onViewClicked() {

        getSupportFragmentManager().beginTransaction().add(R.id.GameFrameLayout, mapFragment).addToBackStack(null).commit();

    }


    public static class CriminalStepAdapter extends RecyclerView.Adapter<CriminalStepAdapter.ViewHolder> {


        private List<CriminalStep> mValues;
        private Activity parent;
        private GameObject gameObject;


        public CriminalStepAdapter(Activity parent) {
            mValues = new ArrayList<>();
            //this.gameObject = gameObject;
            this.parent = parent;
            //this.mValues = mValues;
        }

        public void update(List newValues) {
            mValues.clear();
            mValues.addAll(newValues);
            notifyDataSetChanged();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_criminalstep_card, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.iconImg.setBackgroundColor(Color.BLACK);
            switch (mValues.get(position).getType()) {

                case "BUS":
                    holder.iconImg.setImageResource(R.drawable.ic_directions_bus_white_24dp);
                    holder.iconImg.setBackgroundColor(parent.getResources().getColor(R.color.colorBUS));
                    break;
                case "SUBWAY":
                    holder.iconImg.setImageResource(R.drawable.ic_directions_subway_white_24dp);
                    holder.iconImg.setBackgroundColor(parent.getResources().getColor(R.color.colorSUBWAY));
                    break;
                case "TRAM":
                    holder.iconImg.setImageResource(R.drawable.ic_tram_white_24dp);
                    holder.iconImg.setBackgroundColor(parent.getResources().getColor(R.color.colorTRAM));
                    break;
                case "WALK":
                    holder.iconImg.setImageResource(R.drawable.baseline_directions_walk_white_24);
                    holder.iconImg.setBackgroundColor(parent.getResources().getColor(R.color.colorWALK));
                    break;


            }

        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            final ImageView iconImg;


            ViewHolder(View view) {
                super(view);
                iconImg = view.findViewById(R.id.Game_item_criminalstepImage);


            }
        }

    }

}
