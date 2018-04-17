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
import com.example.nagya.bestinpest.network.RabbitMq.GameRabbitMq;
import com.example.nagya.bestinpest.network.RabbitMq.item.GameRabbitMqItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.InvalidClassException;

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
    protected Integer gameID;
    protected Integer myPlayerID;
    private String rabbitMqURL;

    private DetectivePlansFragment detectivePlansFragment;
    private GameRabbitMq gameRabbitMq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        gameID  = intent.getIntExtra("GameId",-1);
        myPlayerID = intent.getIntExtra("PlayerId",-1);
        rabbitMqURL = intent.getStringExtra("RabbitMqURL");

        if (myPlayerID==-1 && gameID==-1){
            try {
                throw new InvalidClassException("Nincs meg a playerID!");
            } catch (InvalidClassException e) {
                e.printStackTrace();
            }
        }

        gameApiInteractor = new GameApiInteractor(this);
        detectivePlansFragment= new DetectivePlansFragment();
        detectivePlansFragment.setUser(myPlayerID);




    }


    @Override
    public void onStart() {
        super.onStart();
        gameRabbitMq= new GameRabbitMq(rabbitMqURL, gameID);
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRabbitMessage(GameRabbitMqItem gameRabbitMqItem) {
        this.gameObject=gameRabbitMqItem.getObject();
        switch (gameRabbitMqItem.getType()){
            case "turn-changed":setupViewStuff();
                                break;
            case "game-ended": break;
            case "detectives-step": break;
            case "detective-plan": refreshFragmentData();
                                    break;
            case "recommendation-sent": break;
            case "criminal-step": break;
            case "plan-approved": break;
            case "invalid-step": break;
            case "criminal-caught": break;
            case "game-removed": break;
            case "plan-reaction": break;
            case "recommendation-removed": break;
            case "player-ready": break;





        }
        setupViewStuff();

    }


    @OnClick(R.id.GameOkayBtn)
    public void onViewClicked() {
    }

    private void refreshFragmentData(){
        detectivePlansFragment.updateGameObject(gameObject);
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
                case "detectives":   detectivePlansFragment.setupGameObject(gameObject);
                                    getSupportFragmentManager().beginTransaction()
                                    .add(R.id.GameFrameLayout, detectivePlansFragment).commit();
                                    break;
            }
        }
    }
}
