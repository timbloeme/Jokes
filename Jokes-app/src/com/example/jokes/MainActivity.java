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
	
	//public Rest rest = new Rest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // make the logo in the action bar clickable
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        // set jokes list to most recent / most popular
        ArrayList<String> jokes = new ArrayList<String>();
        Log.v("TEST","get joke");
        //rest.get_joke("http://www.timbloeme.nl/app/getjokes.php", "1");
        Log.v("TEST","get joke succes");
        
        jokes.add("#1");
        jokes.add("#2");
        jokes.add("#3");
        jokes.add("#4");
        jokes.add("#5");
        jokes.add("#6");
        jokes.add("#7");
        jokes.add("#8");
        jokes.add("#9");
        jokes.add("#10");
        jokes.add("#11");
        jokes.add("#12");
        jokes.add("#13");
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
        	Log.v("TEST","Random");
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
                    }
            });
            
    }
    
    public void showJoke(String joke){
    	ViewFlipper vf = (ViewFlipper) findViewById(R.id.bottomFlipper);
    	vf.setDisplayedChild(JOKE);
    	TextView tv = (TextView) findViewById(R.id.joke_name);
    	tv.setText(joke);
    }
    
    public void showSearchedJokes(View view) {
    	ViewFlipper vf = (ViewFlipper) findViewById(R.id.bottomFlipper);
    	vf.setDisplayedChild(JOKES_LIST);
    	TextView tv = (TextView) findViewById(R.id.jokes_list_title);
    	tv.setText("Found jokes:");

    	EditText edit = (EditText) findViewById(R.id.search_text);
    	String search = edit.getText().toString();
    	
    	// find jokes with search in the name / author name
    	
    	ArrayList<String> jokes = new ArrayList<String>();
    	jokes.add("#1 " + search);
    	jokes.add("#2 " + search);
    	jokes.add("#3 " + search);
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
                	
            		showJoke(name);
            	}
            }
        });
    }
}
