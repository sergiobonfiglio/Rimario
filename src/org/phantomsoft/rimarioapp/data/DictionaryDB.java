package org.phantomsoft.rimarioapp.data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

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

    private static String getLastChars(String input, int n) {

	if (input.length() <= n) {
	    return input;
	} else {
	    return input.substring(input.length() - n);
	}

    }

    public static void main(String[] args) throws Exception {

	// register the driver

	String sMakeTable = "create table " + TABLE_NAME + " (" +
		ID + " integer primary key autoincrement, " +
		WORD + " text not null, " +
		REVERSED_WORD + " text not null, " +
		LAST_3_CHARS + " text not null, " +
		LAST_2_CHARS + " text not null, " +
		LAST_CHAR + " text not null, " + ");";

	String sDriverName = "org.sqlite.JDBC";
	Class.forName(sDriverName);

	// now we set up a set of fairly basic string variables to use in the
	// body of the code proper
	String sJdbc = "jdbc:sqlite";
	String sDbUrl = sJdbc + ":" + DATABASE_NAME;

	// which will produce a legitimate Url for SqlLite JDBC :
	int iTimeout = 30;

	// create a database connection
	Connection conn = DriverManager.getConnection(sDbUrl);
	conn.setAutoCommit(false);

	try {
	    Statement stmt = conn.createStatement();
	    try {
		stmt.setQueryTimeout(iTimeout);
		stmt.executeUpdate(sMakeTable);

		PreparedStatement prep = conn.prepareStatement(
			"insert into " + TABLE_NAME + " (" + WORD + ","
				+ REVERSED_WORD + ","
				+ LAST_3_CHARS + ","
				+ LAST_2_CHARS + ","
				+ LAST_CHAR + ") " +
				" values (?,?,?,?,?);");

		FileInputStream is =
			new FileInputStream(".\\res\\raw\\prova.txt");
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		String line = br.readLine();
		while (line != null) {

		    line = br.readLine();

		    prep.setNString(1, line);
		    prep.setNString(2, new StringBuffer(line).reverse()
			    .toString());
		    prep.setNString(3, getLastChars(line, 3));
		    prep.setNString(4, getLastChars(line, 2));
		    prep.setNString(5, getLastChars(line, 1));

		    prep.executeUpdate();

		}
		conn.commit();

		br.close();
		isr.close();
		is.close();

	    } finally {
		try {
		    stmt.close();
		} catch (Exception ignore) {
		}
	    }
	} finally {
	    try {
		conn.close();
	    } catch (Exception ignore) {
	    }
	}

    }
}