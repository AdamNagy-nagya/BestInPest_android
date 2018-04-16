package com.example.nagya.bestinpest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.nagya.bestinpest.Game.DetectivePlansFragment;
import com.example.nagya.bestinpest.Game.item.GameObject;
import com.example.nagya.bestinpest.network.GameNetwork.GameApiInteractor;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity {

    @BindView(R.id.GameGamestatusTV)
    TextView GameGamestatusTV;
    @BindView(R.id.GameRoundNumberTV)
    TextView GameRoundNumberTV;
    @BindView(R.id.GameMrXpastSteps)
    RecyclerView GameMrXpastSteps;
    @BindView(R.id.GameFrameLayout)
    FrameLayout GameFrameLayout;
    @BindView(R.id.GameOkayBtn)
    Button GameOkayBtn;


    private String state;
    private GameObject gameObject;
    private GameApiInteractor gameApiInteractor;
    private Integer gameID;

    private DetectivePlansFragment detectivePlansFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        gameID  = intent.getIntExtra("GameId",1000);
        gameApiInteractor = new GameApiInteractor(this);
        detectivePlansFragment= new DetectivePlansFragment();




    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        gameApiInteractor.getGameObjectById(gameID);
    }

    @Override
    public void onStop() {

        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGameObject(GameObject gameObject) {
        this.gameObject= gameObject;
        setupViewStuff();
        Log.e("Game found","YEE");
    }



    @OnClick(R.id.GameOkayBtn)
    public void onViewClicked() {
    }

    private void setupViewStuff(){
        if(gameObject!=null){
            GameGamestatusTV.setText(gameObject.getTurn());
            GameRoundNumberTV.setText(gameObject.getRound()+".");
            state=gameObject.getTurn();
            switch (state){

                //TODO!!!
                case "criminal":    detectivePlansFragment.setupGameObject(gameObject);
                                    getSupportFragmentManager().beginTransaction()
                                    .add(R.id.GameFrameLayout, detectivePlansFragment).commit();
                                    break;
                case "detective": break;
            }
        }
    }
}
