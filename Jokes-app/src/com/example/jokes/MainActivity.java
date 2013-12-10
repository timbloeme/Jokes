package com.example.jokes;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ViewFlipper;

public class MainActivity extends Activity {

	// favorites is nothing more than a special jokes_list
	public static final int FAVORITES  = 0;
	public static final int JOKES_LIST = 0;
	public static final int JOKE	   = 1;
	public static final int PROFILE    = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // set jokes list to most recent / most popular
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void showJoke(View view) {
    	ViewFlipper vf = (ViewFlipper) findViewById(R.id.viewFlipper);
    	vf.setDisplayedChild(JOKE);
    	// get joke and show
    }
    
    public void showSearchedJokes(View view) {
    	ViewFlipper vf = (ViewFlipper) findViewById(R.id.viewFlipper);
    	vf.setDisplayedChild(JOKES_LIST);
    	// set jokes list
    }
    
    public void showProfile(View view) {
    	ViewFlipper vf = (ViewFlipper) findViewById(R.id.viewFlipper);
    	vf.setDisplayedChild(PROFILE);
    	// get profile and show
    }
    
    public void showFavorites(View view) {
    	ViewFlipper vf = (ViewFlipper) findViewById(R.id.viewFlipper);
    	vf.setDisplayedChild(FAVORITES);
    	// set jokes list
    }
}
