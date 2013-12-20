package com.example.jokes;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

public class HomeFragment extends Fragment {
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.general, container, false);
        Activity ac = (Activity) rootView.getContext();

    	JokesManager jm = new JokesManager(rootView);
        
        ViewFlipper vf = (ViewFlipper) rootView.findViewById(R.id.viewFlipper);
        vf.setDisplayedChild(0);

    	final MainActivity temp = (MainActivity) ac;
    	List<Joke> jokes = temp.setJokes("Recent");
    	jm.setJokes(jokes);
         
        return rootView;
    }
}
