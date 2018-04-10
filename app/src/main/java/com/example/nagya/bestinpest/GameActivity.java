package com.example.nagya.bestinpest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);


    }


    @OnClick(R.id.GameOkayBtn)
    public void onViewClicked() {
    }
}
