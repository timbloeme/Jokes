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

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
 
public class Rest extends AsyncTask<String, Void, Void> {
	
		private Activity ac = new Activity();
          
        // Required initialization
         
        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private int initial_load = 10;
        String data =""; 
        int sizeData = 0;  
         
         

        public int add_joke(String url, String title, String joke, int uid){
        	String[] to_send = new String[2];
        	
        	try{
        		data ="&" + URLEncoder.encode("title", "UTF-8") + "=" + title;
        		data +="&" + URLEncoder.encode("joke", "UTF-8") + "=" + joke;
        		data +="&" + URLEncoder.encode("uid", "UTF-8") + "=" + String.valueOf(uid);
			}catch(Exception e){
				Log.v("error",String.valueOf(e));
			}
        	
        	to_send[0] = url;
        	to_send[1] = data;
        	
        	doInBackground(to_send);
        	
        	String OutputData = "";
            JSONObject jsonResponse;
        	
        	return 1;
        }
        
        public int delete_joke(String url, int id){
        	String[] to_send = new String[2];
        	
        	try{
        		data ="&" + URLEncoder.encode("uid", "UTF-8") + "=" + String.valueOf(id);
        	}catch(Exception e){
        		Log.v("error",String.valueOf(e));
        	}
        	
        	to_send[0] = url;
        	to_send[1] = data;
        	
        	doInBackground(to_send);
        	
        	return 1;
        }
        
        public int update_joke(String url, String title, String joke, int id, int uid){
        	String[] to_send = new String[2];
        	
        	try{
        		data ="&" + URLEncoder.encode("title", "UTF-8") + "=" + title;
        		data +="&" + URLEncoder.encode("joke", "UTF-8") + "=" + joke;
        		data +="&" + URLEncoder.encode("uid", "UTF-8") + "=" + String.valueOf(uid);
        		data +="&" + URLEncoder.encode("id", "UTF-8") + "=" + String.valueOf(id);
        	}catch(Exception e){
        		Log.v("error",String.valueOf(e));
        	}
        	
        	to_send[0] = url;
        	to_send[1] = data;
        	
        	doInBackground(to_send);
        	
        	return 1;
        }
        
        public int get_joke(String url, String id){
        	String[] to_send = new String[2]; 
            
            String OutputData = "";
            JSONObject jsonResponse;
            
            try{
            	data ="&" + URLEncoder.encode("id", "UTF-8") + "=" + id;
    		}catch(Exception e){
    			Log.v("error",String.valueOf(e));
    		}
        	
        	to_send[0] = url;
        	to_send[1] = data;
        	
        	doInBackground(to_send);
        	
        	try {
                
                /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
                jsonResponse = new JSONObject(Content);
                  
                /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
                /*******  Returns null otherwise.  *******/
                JSONArray jsonMainNode = jsonResponse.optJSONArray("Android");
                  
                /*********** Process each JSON Node ************/

                int lengthJsonArr = jsonMainNode.length();  

                for(int i=0; i < lengthJsonArr; i++){
                    /****** Get Object for each JSON node.***********/
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                      
                    /******* Fetch node values **********/
                    String J_title       = jsonChildNode.optString("title").toString();
                    String J_joke     = jsonChildNode.optString("joke").toString();
                    String J_user = jsonChildNode.optString("user").toString();
                      
                    
                    OutputData += "title           : "+ J_title + "Joke      : "+ J_joke + "User                : "+ J_user;
                    
                     
               }
            /****************** End Parse Response JSON Data *************/    
                 
                //Show Parsed Output on screen (activity)
           }catch (JSONException e) {
               
               e.printStackTrace();
           }
        	
        	return 1;
        }
        
        
        // Call after onPreExecute method
        protected Void doInBackground(String... in_data) {
             
            /************ Make Post Call To Web Server ***********/
            BufferedReader reader=null;
    
                 // Send data 
                try
                { 
                   
                   // Defined URL  where to send data
                  URL url = new URL(in_data[0]);
                  data = in_data[1];
                   
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
                   Content = sb.toString();
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
             
            /*****************************************************/
            return null;
        }
          
        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.
              
            if (Error != null) {
                  
            } else {
               
                // Show Response Json On Screen (activity)
                 
             /****************** Start Parse Response JSON Data *************/
                 
                String OutputData = "";
                JSONObject jsonResponse;
                       
                try {
                       
                     /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
                     jsonResponse = new JSONObject(Content);
                       
                     /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
                     /*******  Returns null otherwise.  *******/
                     JSONArray jsonMainNode = jsonResponse.optJSONArray("Android");
                       
                     /*********** Process each JSON Node ************/
   
                     int lengthJsonArr = jsonMainNode.length();  
   
                     for(int i=0; i < lengthJsonArr; i++) 
                     {
                         /****** Get Object for each JSON node.***********/
                         JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                           
                         /******* Fetch node values **********/
                         String title       = jsonChildNode.optString("title").toString();
                         String joke     = jsonChildNode.optString("joke").toString();
                         String user = jsonChildNode.optString("user").toString();
                           
                         
                         OutputData += "title           : "+ title + "Joke      : "+ joke + "User                : "+ user;
                         
                          
                    }
                 /****************** End Parse Response JSON Data *************/    
                      
                     //Show Parsed Output on screen (activity)
                      
                       
                 } catch (JSONException e) {
           
                     e.printStackTrace();
                 }
   
                  
             }
        }
     
}
