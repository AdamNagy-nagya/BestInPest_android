package com.example.nagya.bestinpest.Game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nagya.bestinpest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GameLoadBlankFragment extends Fragment {



    TextView GameBlankFragTV;
    String actualText;

    public GameLoadBlankFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_load_blank, container, false);
        GameBlankFragTV= view.findViewById(R.id.Game_BlankFrag_TV);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        GameBlankFragTV.setText(actualText);
    }

    public void setGameEndDetectiveWins(){
        actualText= getResources().getString(R.string.DetectiveWins);
        //GameBlankFragTV.setText(R.string.DetectiveWins);
    }

    public void setGameEndCriminalWins(){
        actualText= getResources().getString(R.string.CriminalWins);
        //GameBlankFragTV.setText(R.string.CriminalWins);
    }

    public void setWaitingForCriminal(){
        actualText= getResources().getString(R.string.WaitForCriminal);
        //GameBlankFragTV.setText(R.string.WaitForCriminal);
    }
    public void setWaitingForDetectives(){
        actualText= getResources().getString(R.string.WaitForDetectives);
        //GameBlankFragTV.setText(R.string.WaitForDetectives);
    }

}
