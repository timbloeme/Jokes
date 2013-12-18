package com.example.jokes;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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

public class MainActivity extends Activity {


	// favorites and home are nothing more than a special jokes_list
	public static final int FAVORITES  = 0;
	public static final int HOME       = 0;
	public static final int JOKES_LIST = 0;
	public static final int JOKE	   = 1;
	public static final int PROFILE    = 2;
	public static final int CREATE_JOKE= 3;

	public static final int NORMAL_BAR = 0;
	public static final int SEARCH_BAR = 1;

    public DatabaseHandler db = new DatabaseHandler();
	
	//public Rest rest = new Rest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // make the logo in the action bar clickable
        getActionBar().setDisplayHomeAsUpEnabled(true);

        db.addJoke(new Joke("#1","Joking",1));
        db.addJoke(new Joke("#2","Joking",1));
        db.addJoke(new Joke("#3","Joking",2));
        db.addJoke(new Joke("#4","Joking",2));

        List<Jokes> jokes = db.getRecentJokes();

        setJokes(jokes);
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
            // Set jokes list to most recent
            break;
            
        case R.id.action_random:
            int max = db.getJokesCount();
            // get random number between 0 - max
            int rnd = 2;
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
        final ArrayList<Joke> list = jokes;
        ListView listView = (ListView) findViewById(R.id.jokes_list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        final Joke item = (Joke) parent.getItemAtPosition(position);
                        // pak het Joke element en geef deze mee aan showJoke
                        showJoke(item);
                }
        });
    }
    
    public void showJoke(Joke joke){
    	ViewFlipper vf = (ViewFlipper) findViewById(R.id.bottomFlipper);
    	vf.setDisplayedChild(JOKE);
        TextView tv = (TextView) findViewById(R.id.joke_name);
        tv.setText(joke.getTitle);
        TextView tv = (TextView) findViewById(R.id.joke_content);
        tv.setText(joke.getContent());
        TextView tv = (TextView) findViewById(R.id.joke_name);
        tv.setText(String.valueOf(joke.getUID()));
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
    
    public void showProfile(View view) {
    	ViewFlipper vf = (ViewFlipper) findViewById(R.id.bottomFlipper);
    	vf.setDisplayedChild(PROFILE);
    	
    	// get profile and show
    }
    
    public void showFavorites(View view) {
    	ViewFlipper vf = (ViewFlipper) findViewById(R.id.bottomFlipper);
    	vf.setDisplayedChild(FAVORITES);
    	TextView tv = (TextView) findViewById(R.id.jokes_list_title);
    	tv.setText("Your favorite jokes:");
    	
    	// set jokes list
    }
    
    public void createJoke(View view) {
    	ViewFlipper vf = (ViewFlipper) findViewById(R.id.bottomFlipper);
    	vf.setDisplayedChild(CREATE_JOKE);
    	
    	View b = findViewById(R.id.createButton);
    	b.setVisibility(View.GONE);

        String name, content;
        int author;
    	
    	final Button button = (Button) findViewById(R.id.createJokeButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	TextView error = (TextView) findViewById(R.id.error);
            	
            	EditText edit = (EditText) findViewById(R.id.create_joke_name);
            	name = edit.getText().toString();

            	edit = (EditText) findViewById(R.id.create_joke_content);
            	content = edit.getText().toString();

            	edit = (EditText) findViewById(R.id.create_joke_author);
            	author = edit.getText().valueOf();
            	
            	if (name.equals("") || content.equals("") || author.equals("")) {
            		error.setText("You need to fill in every field");
            	}
            	else {
                	
                	View b = findViewById(R.id.createButton);
                	b.setVisibility(View.VISIBLE);
                	
                    db.addJoke(name, content, author);

            		showJoke(name);
            	}
            }
        });
    }
}
