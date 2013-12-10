package com.example.jokes;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
        ArrayList<String> jokes = new ArrayList<String>();
        jokes.add("#1");
        jokes.add("#2");
        jokes.add("#3");
        jokes.add("#4");
        jokes.add("#5");
        jokes.add("#6");
        jokes.add("#7");
        jokes.add("#8");
        setJokes(jokes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void setJokes(ArrayList<String> jokes){
    	final ArrayList<String> list = jokes;
    	ListView listView = (ListView) findViewById(R.id.jokes_list);
    	final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
    	listView.setAdapter(adapter);
    	
    	listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    		@Override
    		public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
    			final String item = (String) parent.getItemAtPosition(position);
    			// pak het Joke element en geef deze mee aan showJoke
    			showJoke(item);
    			Log.v("TEST",item);
    		}
    	});
    	
    	/*
    	Best wel grappig, verwijder een item uit de list met een animatie
    	
    	listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    		@Override
    		public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
    			final String item = (String) parent.getItemAtPosition(position);
    			view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable() {
    				@Override
    				public void run() {
    					list.remove(item);
    					adapter.notifyDataSetChanged();
    					view.setAlpha(1);
    				}
    			});
    		}
    	});*/
    	
    }
    
    // voor nu wordt er een string meegegeven, dit moet uiteindelijk een Joke element worden
    public void showJoke(String joke){
		Log.v("TEST",joke);
    	ViewFlipper vf = (ViewFlipper) findViewById(R.id.viewFlipper);
    	vf.setDisplayedChild(JOKE);
    	TextView tv = (TextView) findViewById(R.id.joke_name);
    	tv.setText("TESTING TESTER TEST!");
    	TextView tv2 = (TextView) findViewById(R.id.joke_author);
    	tv2.setText("TESTING TESTER TEST!");
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
