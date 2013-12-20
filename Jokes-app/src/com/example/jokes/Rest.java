package com.example.jokes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

class Rest  extends AsyncTask<String, Void, Void> {    	
    public static final int GET = 1;
    public static final int DELETE = 2;
    public static final int CREATE = 3;
    public static final int UPDATE = 4;
    
    public static final int JOKE = 1;
    public static final int PROFILE = 2;
    public static final int GENRE = 3;
    
    private final HttpClient Client = new DefaultHttpClient();
    private String data;
    private String returned;
    private String Error = null;
    private URL url;
    Request request; 
    int sizeData = 0;
    List<Joke> jokes = new ArrayList<Joke>();
    
    Gson gson = new Gson();
    
    
    
    protected void onPreExecute() {
        Log.v("Restfull error","Starting sql query");  
    }
    
    // Call after onPreExecute method
    protected Void doInBackground(String... urls) {
        BufferedReader reader=null;
    
        // Send data 
        try{ 
        // Defined URL  where to send data
        request =  gson.fromJson(urls[0], Request.class);
        url = new URL(request.url);
        Log.v("Restfull error", Arrays.toString(request.ids));
        try{
            switch (request.type){
                case JOKE:
                    switch(request.operation){
                        case GET: 
                        	data = URLEncoder.encode("id", "UTF-8") + "=" + Arrays.toString(request.ids);
                            break;
                        case DELETE:
                            data = URLEncoder.encode("id", "UTF-8") + "=" + Arrays.toString(request.ids);
                            break;
                        case CREATE:
                            data  = URLEncoder.encode("joke", "UTF-8") + "=" + request.joke._content;
                            data += "&" + URLEncoder.encode("title", "UTF-8") + "=" + request.joke._title;
                            data += "&" + URLEncoder.encode("user", "UTF-8") + "=" + request.joke._user;
                            break;
                        case UPDATE:
                            data  = URLEncoder.encode("id", "UTF-8") + "=" + request.ids;
                            data += "&" + URLEncoder.encode("joke", "UTF-8") + "=" + request.joke._content;
                            data += "&" + URLEncoder.encode("title", "UTF-8") + "=" + request.joke._title;
                            data += "&" + URLEncoder.encode("user", "UTF-8") + "=" + request.joke._user;
                            break;
                            
                    }
                case PROFILE:
                case GENRE:
            }
        }catch(Exception ex){
            Log.v("Restfull error",ex.getMessage());
            Log.v("Restfull error",ex.getCause().getMessage());
        }
            
        // Send POST data request
        Log.v("Restfull error","making connection");
        URLConnection conn = url.openConnection(); 
        conn.setDoOutput(true); 
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
        wr.write( data ); 
        wr.flush(); 
        
        // Get the server response 
            
        reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line = null;
        Log.v("Restfull error","connection made starting read");
            // Read Server Response
            while((line = reader.readLine()) != null){
                // Append server response in string
                sb.append(line + "");
            }
            
            // Append Server Response To Content String 
            returned = sb.toString();
            JSONObject jsonResponse;
            
            try {
                
                jsonResponse = new JSONObject(returned);
                
                JSONArray jsonMainNode = jsonResponse.optJSONArray("Android");
                
    
                int lengthJsonArr = jsonMainNode.length();
                for(int i=0; i < lengthJsonArr; i++){
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    switch(request.type){
                        case GET: String title       = jsonChildNode.optString("title").toString();
                            String joke     = jsonChildNode.optString("joke").toString();
                            String uid = jsonChildNode.optString("uid").toString();
                            String id  = jsonChildNode.optString("id").toString();
                            String user = jsonChildNode.optString("user").toString();
                            jokes.add(new Joke(Integer.parseInt(id), Integer.parseInt(uid), title, joke, user));
                            break;
                    }
                } 
            } catch (JSONException ex) {
                Log.v("Restfull error", ex.getMessage());
            }finally{
            }
        }
        catch(Exception ex){
            Log.v("Restfull error",ex.getMessage());
        }
        finally{
            try {
                reader.close();
            }catch(Exception ex) {
                Log.v("Restfull error",ex.getMessage());
            }
        }
        
        return null;
    }
    
    protected void onPostExecute(Void unused) {
        // NOTE: You can call UI Element here.
        
        // Close progress dialog
        
        if (Error != null) {
            
        } else {
            
        }
    }   
}