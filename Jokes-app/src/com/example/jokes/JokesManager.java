package com.example.jokes;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class JokesManager extends Activity{
	View rootView;
	Activity ac;
	public static final int JOKE	   = 1;
	
	JokesManager (View rootView){
		this.rootView = rootView;
		this.ac = (Activity) rootView.getContext();
	}
	
    public void setJokes(List<Joke> jokes){
        final ArrayList<String> list = new ArrayList<String>();
        for (Joke joke : jokes) {
    		list.add(joke.getTitle());
    	}
        ListView listView = (ListView) rootView.findViewById(R.id.jokes_list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ac, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                //pak het Joke element en geef deze mee aan showJoke
              	final String item = (String) parent.getItemAtPosition(position);
                showJoke(item);
            }
        });
    }
    
    public void showJoke(String joke){
    	ViewFlipper vf = (ViewFlipper) rootView.findViewById(R.id.jokesFlipper);
    	vf.setDisplayedChild(1);
    	Log.v("TEST",joke);
        TextView tv = (TextView) rootView.findViewById(R.id.joke_name);
        tv.setText(joke);
        tv = (TextView) rootView.findViewById(R.id.joke_content);
        tv.setText(joke);
        tv = (TextView) rootView.findViewById(R.id.joke_author);
        tv.setText(joke);
    }
}
