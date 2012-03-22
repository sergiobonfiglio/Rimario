package org.phantomsoft.rimarioapp.data;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

@Deprecated
public class DictionaryDBHelper extends SQLiteOpenHelper implements
	DictionaryTableColumns {

    // The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.phantomsoft.rimarioapp/databases/";

    static final String DATABASE_NAME = "dict_it.db";
    static final int DATABASE_VERSION = 1;

    SQLiteDatabase db;
    Context context;

    public DictionaryDBHelper(Context context, String name,
	    CursorFactory factory, int version) {
	super(context, name, factory, version);
	this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
	db.execSQL("create table " + TABLE_NAME + " (" + ID
		+ " integer primary key autoincrement, " + WORD
		+ " text not null, " + REVERSED_WORD + " text not null, "
		+ LAST_3_CHARS + " text not null, " + LAST_2_CHARS
		+ " text not null, " + LAST_CHAR + " text not null, " + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	Log.w("TaskDBAdapter", "Upgrading from version " + oldVersion + " to "
		+ newVersion + ", which will destroy all old data");
	db.execSQL("drop table if exists " + TABLE_NAME);
	onCreate(db);
    }

    public void open() {

	db = dbExists();

	if (db == null) {
	    // the database has to be created by copying the default one from
	    // assets
	    this.getReadableDatabase();// creates default db

	}

    }

    private void copyDataBase() throws IOException {

	// Open your local db as the input stream
	InputStream myInput = context.getAssets().open(DATABASE_NAME);

	// Path to the just created empty db
	String outFileName = DB_PATH + DATABASE_NAME;

	// Open the empty db as the output stream
	OutputStream myOutput = new FileOutputStream(outFileName);

	// transfer bytes from the inputfile to the outputfile
	byte[] buffer = new byte[1024];
	int length;
	while ((length = myInput.read(buffer)) > 0) {
	    myOutput.write(buffer, 0, length);
	}

	// Close the streams
	myOutput.flush();
	myOutput.close();
	myInput.close();

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
