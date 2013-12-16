package com.example.jokes;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;
 
public class Rest extends Activity {      
      
    // Class with extends AsyncTask class
     
    private class db_connection  extends AsyncTask<String, Void, Void> {
          
        // Required initialization
         
        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Rest.this);
        private int initial_load = 10;
        String data =""; 
        TextView uiUpdate = (TextView) findViewById(R.id.output);
        TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);
        int sizeData = 0;  
        EditText serverText = (EditText) findViewById(R.id.serverText);
         
         

        protected int add_joke(String url, String tittle, String joke, int uid){
        	String[] to_send = new String[2];
        	
        	Dialog.setMessage("Please wait..");
            Dialog.show();
        	
        	data ="&" + URLEncoder.encode("tittle", "UTF-8") + "=" + tittle;
        	data +="&" + URLEncoder.encode("joke", "UTF-8") + "=" + joke;
        	data +="&" + URLEncoder.encode("uid", "UTF-8") + "=" + String.valueOf(uid);
        	
        	to_send[0] = url;
        	to_send[1] = data;
        	
        	doInBackground(to_send);
        	
        	String OutputData = "";
            JSONObject jsonResponse;
        	
        	return 1;
        }
        
        protected int delete_joke(String url, int id){
        	String[] to_send = new String[2];
        	
        	
        	data ="&" + URLEncoder.encode("uid", "UTF-8") + "=" + String.valueOf(id);
        	
        	to_send[0] = url;
        	to_send[1] = data;
        	
        	doInBackground(to_send);
        	
        	Dialog.dismiss();
        	
        	return 1;
        }
        
        protected int update_joke(String url, String tittle, String joke, int id, int uid){
        	String[] to_send = new String[2];
        	
        	data ="&" + URLEncoder.encode("tittle", "UTF-8") + "=" + tittle;
        	data +="&" + URLEncoder.encode("joke", "UTF-8") + "=" + joke;
        	data +="&" + URLEncoder.encode("uid", "UTF-8") + "=" + String.valueOf(uid);
        	data +="&" + URLEncoder.encode("id", "UTF-8") + "=" + String.valueOf(id);
        	
        	to_send[0] = url;
        	to_send[1] = data;
        	
        	doInBackground(to_send);
        	
        	return 1;
        }
        
        protected int get_joke(String url, String id){
        	String[] to_send = new String[2]; 
            
            String OutputData = "";
            JSONObject jsonResponse;       	
 
        	data ="&" + URLEncoder.encode("id", "UTF-8") + "=" + id;
        	
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
                    String J_tittle       = jsonChildNode.optString("tittle").toString();
                    String J_joke     = jsonChildNode.optString("joke").toString();
                    String J_user = jsonChildNode.optString("user").toString();
                      
                    
                    OutputData += "Tittle           : "+ J_tittle + "Joke      : "+ J_joke + "User                : "+ J_user;
                    
                     
               }
            /****************** End Parse Response JSON Data *************/    
                 
                //Show Parsed Output on screen (activity)
                jsonParsed.setText( OutputData );
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
              
            // Close progress dialog
            Dialog.dismiss();
              
            if (Error != null) {
                  
                uiUpdate.setText("Output : "+Error);
                  
            } else {
               
                // Show Response Json On Screen (activity)
                uiUpdate.setText( Content );
                 
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
                         String tittle       = jsonChildNode.optString("tittle").toString();
                         String joke     = jsonChildNode.optString("joke").toString();
                         String user = jsonChildNode.optString("user").toString();
                           
                         
                         OutputData += "Tittle           : "+ tittle + "Joke      : "+ joke + "User                : "+ user;
                         
                          
                    }
                 /****************** End Parse Response JSON Data *************/    
                      
                     //Show Parsed Output on screen (activity)
                     jsonParsed.setText( OutputData );
                      
                       
                 } catch (JSONException e) {
           
                     e.printStackTrace();
                 }
   
                  
             }
        }
          
    }
     
}
