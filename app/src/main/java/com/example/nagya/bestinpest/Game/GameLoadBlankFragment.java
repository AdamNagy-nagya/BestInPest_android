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

/**
 * A simple {@link Fragment} subclass.
 */
public class GameLoadBlankFragment extends Fragment {


    @BindView(R.id.Game_BlankFrag_TV)
    TextView GameBlankFragTV;
    Unbinder unbinder;

    public GameLoadBlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_load_blank, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void setGameEndDetectiveWins(){
        GameBlankFragTV.setText("A bűnözőt elkapták GAME OVER");
    }

    public void setGameEndCriminalWins(){
        GameBlankFragTV.setText("A bűnöző elmenekült GAME OVER");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
