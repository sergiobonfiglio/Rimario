package org.phantomsoft.rimarioapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

@Deprecated
public class DictionaryDB implements DictionaryTableColumns {

    // The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.phantomsoft.rimarioapp/databases/";

    static final String DATABASE_NAME = "dict_it.db";
    static final int DATABASE_VERSION = 1;

    SQLiteDatabase db;

    public DictionaryDB(Context context) {

    }

    public void open() {

	db = dbExists();

	if (db == null) {
	    // the database has to be created by copying the default one from
	    // assets

	}

    }

    private SQLiteDatabase dbExists() throws SQLiteException {
	try {
	    return SQLiteDatabase.openDatabase(DB_PATH + DATABASE_NAME, null,
		    SQLiteDatabase.OPEN_READWRITE);
	} catch (SQLiteException e) {
	    Log.d("Rimario", "The DB already exists");
	    throw e;
	}
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

    private static String getLastChars(String input, int n) {

	if (input.length() <= n) {
	    return input;
	} else {
	    return input.substring(input.length() - n);
	}

    }

}