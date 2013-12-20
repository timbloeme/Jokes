package com.example.jokes;

import android.util.Log;

public class Joke {
    int _id;
    int _uid;
    String _title;
    String _content;
    String _user;
     
    // Empty constructor
    public Joke(){
         
    }
    // constructor
    public Joke(int _id, String _title, String _content, String _user){
        this._id = _id;
        this._title = _title;
        this._content = _content;
        this._user = _user;
    }
    // constructor
    public Joke(int _id, int _uid, String _title, String _content, String _user){
        this._id = _id;
        this._uid = _uid;  
        this._title = _title;
        this._content = _content;
        this._user = _user;
    }
     
    // constructor
    public Joke(String _title, String _content, String _user){
        this._title = _title;
        this._content = _content;
        this._user = _user;
    }

    // getting ID
    public int getID(){
        return this._id;
    }
     
    // setting id
    public void setID(int id){
        this._id = id;
    }
    
    // getting ID
    public int getUID(){
        return this._uid;
    }
    
    // setting id
    public void setUID(int uid){
        this._uid = uid;
    }
     
    // getting title
    public String getTitle(){
        return this._title;
    }
     
    // setting title
    public void setTitle(String _title){
        this._title = _title;
    }

    public String getContent(){
        return this._content;
    }

    public void setContent(String _content){
        this._content = _content;
    }
     
    // getting user
    public String getUser(){
        return this._user;
    }
     
    // setting user
    public void setUser(String _user){
        this._user = _user;
    }

}
