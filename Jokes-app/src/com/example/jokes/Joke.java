package com.example.hangman;

public class Joke {
    int _id;
    String _title;
    String _content;
    int _uid;
     
    // Empty constructor
    public Joke(){
         
    }
    // constructor
    public Joke(int _id, String _title, String _content, int _uid){
        this._id = _id;
        this._title = _title;
        this._content = _content;
        this._uid = _uid;
    }
     
    // constructor
    public Joke(String _title, String _content, int _uid){
        this._title = _title;
        this._content = _content
        this._uid = _uid;
    }

    // getting ID
    public int getID(){
        return this._id;
    }
     
    // setting id
    public void setID(int id){
        this._id = id;
    }
     
    // getting title
    public String getTitle(){
        return this._title;
    }
     
    // setting title
    public void setTitle(String _joke){
        this._title = _title;
    }

    public String getContent(){
        return this._content;
    }

    public void setContent(String _content){
        this._content = _content;
    }
     
    // getting uid
    public int getUID(){
        return this._uid;
    }
     
    // setting uid
    public void setUID(int _uid){
        this._uid = _uid;
    }

}
