// mede mogelijk gemaakt door: http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/

package com.example.jokes;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "jokesManager";
 
    // highScores table name
    private static final String TABLE_JOKES = "jokes";
 
    // highScores Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_UID = "uid";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_JOKES_TABLE = "CREATE TABLE " + TABLE_JOKES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_CONTENT + " TEXT," + KEY_UID + " INTEGER" + ")";
        db.execSQL(CREATE_JOKES_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOKES);
 
        // Create tables again
        onCreate(db);
    }
    
    // Adding new joke
    public void addJoke(Joke joke) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
        ContentValues values = new ContentValues();
        values.put(KEY_CONTENT, joke.getContent()); // Joke
        values.put(KEY_UID, joke.getUID()); // author id of joke
     
        // Inserting Row
        db.insert(TABLE_JOKES, null, values);
        db.close(); // Closing database connection
    }
     
    // Getting single joke
    public Joke getJoke(int id) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	 
        Cursor cursor = db.query(TABLE_JOKES, new String[] { KEY_ID,
                KEY_CONTENT, KEY_UID }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
     
        Joke joke = new Joke(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
        					 cursor.getString(2), Integer.parseInt(cursor.getString(3)));
        // return joke
        return joke;
    }

    public List<Joke> searchJokes(String search) {
        List<Joke> jokeList = new ArrayList<Joke>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_JOKES + " WHERE title LIKE %" + search.toLowerCase() +"%";
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(1);
                if (title.toLowerCase().contains(search.toLowerCase())){
                    Joke joke = new Joke();
                    joke.setID(Integer.parseInt(cursor.getString(0)));
                    joke.setTitle(cursor.getString(1));
                    joke.setContent(cursor.getString(2));
                    joke.setUID(Integer.parseInt(cursor.getString(3)));
                    jokeList.add(joke);
                }
            } while (cursor.moveToNext());
        }
     
        // return joke list
        return jokeList;
    }
     
    // Getting most recent jokes
    public List<Joke> getRecentJokes() {
        List<Joke> jokeList = new ArrayList<Joke>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_JOKES + " ORDER BY id DESC LIMIT 10";
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Joke joke = new Joke();
                joke.setID(Integer.parseInt(cursor.getString(0)));
                joke.setTitle(cursor.getString(1));
                joke.setContent(cursor.getString(2));
                joke.setUID(Integer.parseInt(cursor.getString(3)));
                jokeList.add(joke);
            } while (cursor.moveToNext());
        }
     
        // return joke list
        return jokeList;
    }
     
    // Getting All jokes
    public List<Joke> getAllJokes() {
    	List<Joke> jokeList = new ArrayList<Joke>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_JOKES;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Joke joke = new Joke();
                joke.setID(Integer.parseInt(cursor.getString(0)));
                joke.setTitle(cursor.getString(1));
                joke.setContent(cursor.getString(2));
                joke.setUID(Integer.parseInt(cursor.getString(3)));
                jokeList.add(joke);
            } while (cursor.moveToNext());
        }
     
        // return joke list
        return jokeList;
    }
    
    // Updating single joke
    public int updateJoke(Joke joke) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, joke.getTitle());
        values.put(KEY_CONTENT, joke.getContent());
        values.put(KEY_UID, joke.getUID());
     
        // updating row
        return db.update(TABLE_JOKES, values, KEY_ID + " = ?",
                	     new String[] { String.valueOf(joke.getID()) });
    } 
     
    // Deleting single joke
    public void deleteJoke(Joke joke) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_JOKES, KEY_ID + " = ?",
                new String[] { String.valueOf(joke.getID()) });
        db.close();
    }
     
    // Getting jokes Count
    public int getJokesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_JOKES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
    