package com.example.jokes;

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
import android.widget.EditText;
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
    private JokesManager jm;
	
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
        ViewFlipper vf = (ViewFlipper) findViewById(R.id.jokesFlipper);
        if(vf != null && vf.getDisplayedChild() == 1) {
        	vf.setDisplayedChild(0);
        	List<Joke> jokes = db.getRecentJokes();
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
            jm.showJoke(db.getJoke(rnd).getTitle());
        	break;
        
        case R.id.action_search:
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
    
    public void createJoke(View view) {
    	/*ViewFlipper vf = (ViewFlipper) findViewById(R.id.bottomFlipper);
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
        });*/
    }
}
