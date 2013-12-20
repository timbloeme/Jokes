package com.example.jokes;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

public class UserFragment extends Fragment {
	 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.general, container, false);
        
        ViewFlipper vf = (ViewFlipper) rootView.findViewById(R.id.viewFlipper);
        vf.setDisplayedChild(3);
         
        return rootView;
    }
}