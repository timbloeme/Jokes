package com.example.jokes;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {
	public static final int JOKE	   = 1;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.joke_list, container, false);

    	JokesManager jm = new JokesManager(rootView);

        List<Joke> jokes = new ArrayList<Joke>();
        jokes.add(new Joke("#1","Joke#1","Simone"));
        jokes.add(new Joke("#2","Joke#2","Simone"));
        jokes.add(new Joke("#3","Joke#3","Simone"));

        jm.setJokes(jokes);
         
        return rootView;
    }
}
