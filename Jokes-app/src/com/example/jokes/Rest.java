package com.example.jokes;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
 
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
            Log.v("get_joke", "hi");
            String OutputData = "";
            JSONObject jsonResponse;
            Log.v("get_joke", "hi");
            
            try{
            	data ="&" + URLEncoder.encode("id", "UTF-8") + "=" + id;
    		}catch(Exception e){
    			Log.v("error",String.valueOf(e));
    		}
            Log.v("get_joke", "hi");
        	
        	to_send[0] = url;
        	to_send[1] = data;
        	
        	doInBackground(to_send);
            Log.v("get_joke", "hi");
        	
        	try {
                
                /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
                jsonResponse = new JSONObject(Content);
                  
                /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
                /*******  Returns null otherwise.  *******/
                JSONArray jsonMainNode = jsonResponse.optJSONArray("Android");
                Log.v("get_joke", "hi");
                  
                /*********** Process each JSON Node ************/

                int lengthJsonArr = jsonMainNode.length();  

                for(int i=0; i < lengthJsonArr; i++){
                    Log.v("get_joke", "hi");
                    /****** Get Object for each JSON node.***********/
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                      
                    /******* Fetch node values **********/
                    String J_title= jsonChildNode.optString("title").toString();
                    String J_joke = jsonChildNode.optString("joke").toString();
                    String J_user = jsonChildNode.optString("user").toString();
                      

                    Log.v("get_joke", "hi");
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
        	AndroidHttpClient httpClient = new AndroidHttpClient("http://www.timbloeme.nl");
            httpClient.setMaxRetries(5);
            ParameterMap params = httpClient.newParams()
                    .add("id", "0");
            httpClient.post("/app/getjokes.php", params, new AsyncCallback() {
                public void onSuccess(HttpResponse httpResponse) {
                    Log.v("get_joke", httpResponse.getBodyAsString());
                }
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            });
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
