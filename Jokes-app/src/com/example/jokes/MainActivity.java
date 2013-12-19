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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	public static final int HOME       = 0;
	public static final int JOKES_LIST = 0;
	public static final int FAVORITES  = 1;
	public static final int JOKE	   = 1;
	public static final int PROFILE	   = 2;
	public static final int CREATE_JOKE= 3;

	public static final int NORMAL_BAR = 0;
	public static final int SEARCH_BAR = 1;

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = {"Main", "Favorites", "Profile"};
    
    public DatabaseHandler db = new DatabaseHandler(this);
	
	//public Rest rest = new Rest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
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
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }
 
    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
        /*if(tab.getPosition() == HOME){
        	List<Joke> jokes = db.getAllJokes();
            setJokes(jokes);
        } else if(tab.getPosition() == FAVORITES){
        	// if logged in
        	// db.getFavoriteJokes
        	List<Joke> jokes = db.getAllJokes();
            setJokes(jokes);
            TextView tv = (TextView) findViewById(R.id.jokes_list_title);
            tv.setText("Your favorite jokes");
        }*/
    }
 
    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }
    
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int itemId = item.getItemId();
        ViewFlipper tf;
        
        switch (itemId) {
        
        case android.R.id.home:
        	ViewFlipper vf = (ViewFlipper) findViewById(R.id.bottomFlipper);
        	vf.setDisplayedChild(HOME);
        	tf = (ViewFlipper) findViewById(R.id.topFlipper);
        	tf.setDisplayedChild(NORMAL_BAR);
        	TextView tv = (TextView) findViewById(R.id.jokes_list_title);
        	tv.setText("Most recent jokes:");
        	
        	List<Joke> jokes = db.getAllJokes();
            setJokes(jokes);
            
            break;
            
        case R.id.action_random:
            int max = db.getJokesCount() - 1;
            // get random joke with id between 0 - max
            Random r = new Random();
            int rnd = r.nextInt(max);
            showJoke(db.getJoke(rnd));
        	break;
        
        case R.id.action_search:
        	tf = (ViewFlipper) findViewById(R.id.topFlipper);
        	tf.setDisplayedChild(SEARCH_BAR);
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
    
    public void setJokes(List<Joke> jokes){
        final ArrayList<String> list = new ArrayList<String>();
        for (Joke joke : jokes) {
    		list.add(joke.getTitle());
    	}
        ListView listView = (ListView) findViewById(R.id.jokes_list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                //pak het Joke element en geef deze mee aan showJoke
              	final String item = (String) parent.getItemAtPosition(position);
              	Joke joke = db.getJoke(item);
                showJoke(joke);
            }
        });
    }
    
    public void showJoke(Joke joke){
    	ViewFlipper vf = (ViewFlipper) findViewById(R.id.bottomFlipper);
    	vf.setDisplayedChild(JOKE);
        TextView tv = (TextView) findViewById(R.id.joke_name);
        tv.setText(joke.getTitle());
        tv = (TextView) findViewById(R.id.joke_content);
        tv.setText(joke.getContent());
        tv = (TextView) findViewById(R.id.joke_author);
        tv.setText(joke.getUser());
    }
    
    public void showSearchedJokes(View view) {
    	ViewFlipper vf = (ViewFlipper) findViewById(R.id.bottomFlipper);
    	vf.setDisplayedChild(JOKES_LIST);
    	TextView tv = (TextView) findViewById(R.id.jokes_list_title);
    	tv.setText("Found jokes:");

    	EditText edit = (EditText) findViewById(R.id.search_text);
    	String search = edit.getText().toString();
    	
    	// find jokes with search in the name / author name
    	List<Joke> jokes = db.searchJokes(search);
    	setJokes(jokes);
    }
    
    public void createJoke(View view) {
    	ViewFlipper vf = (ViewFlipper) findViewById(R.id.bottomFlipper);
    	vf.setDisplayedChild(CREATE_JOKE);
    	
    	View b = findViewById(R.id.createButton);
    	b.setVisibility(View.GONE);
    	
    	final Button button = (Button) findViewById(R.id.createJokeButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	TextView error = (TextView) findViewById(R.id.error);
            	
            	EditText edit = (EditText) findViewById(R.id.create_joke_name);
            	String name = edit.getText().toString();

            	edit = (EditText) findViewById(R.id.create_joke_content);
            	String content = edit.getText().toString();

            	edit = (EditText) findViewById(R.id.create_joke_author);
            	String author = edit.getText().toString();
            	
            	if (name.equals("") || content.equals("") || author.equals("")) {
            		error.setText("You need to fill in every field");
            	}
            	else {
                	
                	View b = findViewById(R.id.createButton);
                	b.setVisibility(View.VISIBLE);
                	
                	Joke joke = new Joke(name, content, author);
                	
                    db.addJoke(joke);

            		showJoke(joke);
            	}
            }
        });
    }
}
