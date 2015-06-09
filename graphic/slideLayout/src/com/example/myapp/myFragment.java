package com.example.myapp;

import android.app.Fragment;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class myFragment extends Fragment{
//    boolean sceneVerif;
    public myFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



            View rootView = inflater.inflate(R.layout.main, container, false);
            final Scene scene2 = Scene.getSceneForLayout(container, R.layout.main_2, getActivity());
            Button goButton = (Button) rootView.findViewById(R.id.goButton);

            goButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TransitionManager.go(scene2);


                }
            });


            return rootView;


    }

}