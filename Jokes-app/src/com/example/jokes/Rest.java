package com.example.jokes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

class Rest  extends AsyncTask<String, Void, Void> {
          
        // Required initialization
    	

    	
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
        Joke jokes[];
        
        Gson gson = new Gson();
        
         
         
        protected void onPreExecute() {             
        }
  
        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            BufferedReader reader=null;
    
                 // Send data 
                try
                { 
                  // Defined URL  where to send data
                  request =  gson.fromJson(urls[0], Request.class);
                  url = new URL(request.url);
                  switch (request.type){
                  	case JOKE:
                  		try{
	                  		data = "&" + URLEncoder.encode("id", "UTF-8") + "=" + request.ids;
	                  		if(request.operation == UPDATE || request.operation == CREATE){
		                  		data += "&" + URLEncoder.encode("joke", "UTF-8") + "=" + gson.toJson(request.joke._content);
		                  		data += "&" + URLEncoder.encode("title", "UTF-8") + "=" + gson.toJson(request.joke._title);
		                  		data += "&" + URLEncoder.encode("user", "UTF-8") + "=" + gson.toJson(request.joke._user);
	                  		}
                  		}catch(Exception ex){
                  			Log.v("Data encoding",ex.getMessage());
                  		}finally{
                  		}
                  	case PROFILE:
                  	case GENRE:
                  }
                  
                      
                  // Send POST data request
        
                  URLConnection conn = url.openConnection(); 
                  conn.setDoOutput(true); 
                  OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
                  wr.write( data ); 
                  wr.flush(); 
               
                  // Get the server response 
                    
                  reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                  StringBuilder sb = new StringBuilder();
                  String line = null;
                 
                    // Read Server Response
                    while((line = reader.readLine()) != null)
                        {
                               // Append server response in string
                               sb.append(line + "");
                        }
                     
                    // Append Server Response To Content String 
                    returned = sb.toString();
                }
                catch(Exception ex)
                {
                    Error = ex.getMessage();
                }
                finally
                {
                    try
                    {
          
                        reader.close();
                    }
        
                    catch(Exception ex) {}
                }
             
            return null;
        }
          
        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.
              
            // Close progress dialog
              
            if (Error != null) {
                  
            } else {
               
                // Show Response Json On Screen (activity)
                 
                 
                String OutputData = "";
                JSONObject jsonResponse;
                       
                try {
                       
                     jsonResponse = new JSONObject(returned);
                       
                     JSONArray jsonMainNode = jsonResponse.optJSONArray("Android");
                       
   
                     int lengthJsonArr = jsonMainNode.length();  
   
                     for(int i=0; i < lengthJsonArr; i++) 
                     {
                         JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                           
                         String title       = jsonChildNode.optString("title").toString();
                         String joke     = jsonChildNode.optString("joke").toString();
                         String uid = jsonChildNode.optString("uid").toString();
                           
                         
                         OutputData += " title           : "+ title +"\n"+ "joke      : "+ joke +"\n"+ "uid                : "+ uid +"\n"  +"--------------------------------------------------\n";
                         
                          
                    } 
                      
                     //Show Parsed Output on screen (activity)
                      
                       
                 } catch (JSONException e) {
           
                     e.printStackTrace();
                 }
   
                  
             }
        }
          
    }
     
