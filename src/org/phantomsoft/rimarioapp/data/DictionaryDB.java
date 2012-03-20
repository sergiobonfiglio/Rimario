package org.phantomsoft.rimarioapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DictionaryDB implements DictionaryTableColumns {

    static final String DATABASE_NAME = "dict-it.db";
    static final int DATABASE_VERSION = 1;

    SQLiteOpenHelper dbHelper;
    SQLiteDatabase db;

    public DictionaryDB(Context context) {
	dbHelper = new DictionaryDBHelper(context, DATABASE_NAME, null,
		DATABASE_VERSION);
    }

    public void open() {
	db = dbHelper.getWritableDatabase();
    }

    public void close() {
	db.close();
    }

    public void insertWord(String string) {
	ContentValues map = new ContentValues();
	map.put(WORD, string);
	map.put(REVERSED_WORD, new StringBuffer(string).reverse().toString());
	map.put(LAST_3_CHARS, getLastChars(string, 3));
	map.put(LAST_2_CHARS, getLastChars(string, 2));
	map.put(LAST_CHAR, getLastChars(string, 1));

	db.insert(TABLE_NAME, null, map);
    }

    private String getLastChars(String input, int n) {

	if (input.length() <= n) {
	    return input;
	} else {
	    return input.substring(input.length() - n);
	}

    }

    public static void main(String[] args) {
	
	
    }
    
}