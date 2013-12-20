package com.example.jokes;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class JokesManager extends Activity{
	View rootView;
	Activity ac;
	
	JokesManager (View rootView){
		this.rootView = rootView;
		this.ac = (Activity) rootView.getContext();
	}
	JokesManager (View rootView, Activity ac){
		this.rootView = rootView;
		this.ac = ac;
	}
	
    public void setJokes(List<Joke> jokes){
    	ViewFlipper vf = (ViewFlipper) rootView.findViewById(R.id.viewFlipper);
    	if(vf.getDisplayedChild() > 1) {
    		vf.setDisplayedChild(0);
    	}
        
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
    	ViewFlipper vf = (ViewFlipper) rootView.findViewById(R.id.viewFlipper);
    	vf.setDisplayedChild(4);
        TextView tv = (TextView) rootView.findViewById(R.id.joke_name);
        tv.setText(joke);
        tv = (TextView) rootView.findViewById(R.id.joke_content);
        tv.setText(joke);
        tv = (TextView) rootView.findViewById(R.id.joke_author);
        tv.setText(joke);
    }
    
    public void createJoke() {
    	final MainActivity temp = (MainActivity) ac;
    	final Button button = (Button) rootView.findViewById(R.id.create_joke_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	TextView error = (TextView) rootView.findViewById(R.id.error);
            	
            	EditText edit = (EditText) rootView.findViewById(R.id.create_joke_name);
            	String name = edit.getText().toString();

            	edit = (EditText) rootView.findViewById(R.id.create_joke_content);
            	String content = edit.getText().toString();

            	edit = (EditText) rootView.findViewById(R.id.create_joke_author);
            	String author = edit.getText().toString();
            	
            	if (name.equals("") || content.equals("") || author.equals("")) {
            		error.setText("You need to fill in every field");
            	}
            	else {
                	
                	Joke joke = new Joke(name, content, author);
                	
                	temp.addJokeToDatabase(joke);

            		showJoke(joke.getTitle());
            	}
            }
        });
    }
}
