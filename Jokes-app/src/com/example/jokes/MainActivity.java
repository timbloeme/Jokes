package com.example.jokes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.gson.Gson;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
	
	
	public static final int GET = 1;
	public static final int DELETE = 2;
	public static final int CREATE = 3;
	public static final int UPDATE = 4;

	public static final int HOME       = 0;
	public static final int JOKES_LIST = 0;
	public static final int FAVORITES  = 1;
	public static final int JOKE	   = 1;
	public static final int PROFILE	   = 2;
	public static final int CREATE_JOKE= 3;

	public static final int NORMAL_BAR = 0;
	public static final int SEARCH_BAR = 1;
    
    Gson gson;
    Rest rest;
    Request req;
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = {"Main", "Favorites", "Profile", "Create Joke"};
    
    private DatabaseHandler db = new DatabaseHandler(this);
    private JokesManager jm;
	
	//public Rest rest = new Rest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("Main","got created");
        gson = new Gson();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Request req;
        Rest rest = new Rest();
        Log.v("Main","got jokes");
        
        // empty the db for testing purposes
        List<Joke> temp = db.getAllJokes();
        for(Joke joke: temp){
        	db.deleteJoke(joke);
        }
        
        // make the logo in the action bar clickable
        getActionBar().setDisplayHomeAsUpEnabled(true);

        db.addJoke(new Joke("#1","Joking1","Simone"));
        db.addJoke(new Joke("#2","Joking2","Simone"));
        db.addJoke(new Joke("#3","Joking3","Tim"));
        db.addJoke(new Joke("#4","Joking4","Debbie"));
        int array[] = {2,3};
        
        req = new Request(JOKE, GET, "http://www.timbloeme.nl/app/getjokes.php",array);
        String params = gson.toJson(req);
        rest.execute(params);
        try{
        	rest.get();
        }catch(Exception ex){
        }
        List<Joke> jokes = rest.jokes;
        
        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
 
        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        
 
        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
        
        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
         
            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }
         
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
         
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        
        jm = new JokesManager(getWindow().getDecorView());
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }
 
    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
        ViewFlipper vf = (ViewFlipper) findViewById(R.id.viewFlipper);
        if(vf != null && vf.getDisplayedChild() == 1) {
        	rest = new Rest();
        	vf.setDisplayedChild(0);
    		req = new Request(JOKE, GET, "http://www.timbloeme.nl/app/getjokes.php");
	        String params = gson.toJson(req);
	        rest.execute(params);
	        try{
	        	rest.get();
	        }catch(Exception ex){
	        }
	        List<Joke> jokes = rest.jokes;
        	jm.setJokes(jokes);
        }
    }
 
    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }
    
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int itemId = item.getItemId();
        
        switch (itemId) {
            
        case R.id.action_random:
            int max = db.getJokesCount() - 1;
            // get random joke with id between 0 - max
            Random r = new Random();
            int rnd = r.nextInt(max);
            jm.showJoke(db.getJoke(rnd));
        	break;
        
        case R.id.action_search:
            findViewById(R.id.search_bar).setVisibility(View.VISIBLE);
        	break;
        }

        return true;
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        
        return true;
    }
    
    public void showSearchedJokes(View view) {    	
    	TextView tv = (TextView) findViewById(R.id.jokes_list_title);
    	tv.setText("Found jokes:");

    	EditText edit = (EditText) findViewById(R.id.search_text);
    	String search = edit.getText().toString();
    	
    	// find jokes with search in the name / author name
    	List<Joke> jokes = db.searchJokes(search);
    	jm.setJokes(jokes);
    }
    
    public List<Joke> setJokes(String type){
    	List<Joke> error = new ArrayList<Joke>();
    	if (type.equals("Favorites")){
    		List<Joke> jokes = db.getAllJokes();
    		return jokes;
    	} else if(type.equals("Recent")){
    		rest = new Rest();
    		req = new Request(JOKE, GET, "http://www.timbloeme.nl/app/getjokes.php");
	        String params = gson.toJson(req);
	        rest.execute(params);
	        try{
	        	rest.get();
	        }catch(Exception ex){
	        }
	        List<Joke> jokes = rest.jokes;
    		return jokes;
    	}
    	return error;
    }
    
    public void addJokeToDatabase(Joke joke){
    	joke.setUID(1);
    	rest = new Rest();
		req = new Request(JOKE, CREATE, "http://www.timbloeme.nl/app/createjoke.php",joke);
        String params = gson.toJson(req);
        rest.execute(params);
    }
}
