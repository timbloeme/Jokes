package com.example.jokes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

public class CreateJokeFragment extends Fragment {
	public static final int JOKE	   = 1;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.general, container, false);
        
        ViewFlipper vf = (ViewFlipper) rootView.findViewById(R.id.viewFlipper);
        vf.setDisplayedChild(2);

    	JokesManager jm = new JokesManager(rootView, getActivity());
    	
        jm.createJoke();
         
        return rootView;
    }
}
