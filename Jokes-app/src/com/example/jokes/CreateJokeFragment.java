package com.example.jokes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CreateJokeFragment extends Fragment {
	public static final int JOKE	   = 1;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.create_joke, container, false);

    	JokesManager jm = new JokesManager(rootView);
    	
        jm.createJoke();
         
        return rootView;
    }
}
