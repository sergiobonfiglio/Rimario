package org.phantomsoft.rimarioapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DictionaryDBHelper extends SQLiteOpenHelper implements
	DictionaryTableColumns {

    public DictionaryDBHelper(Context context, String name,
	    CursorFactory factory, int version) {
	super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
	db.execSQL("create table " + TABLE_NAME + " (" +
		ID + " integer primary key autoincrement, " +
		WORD + " text not null, " +
		REVERSED_WORD + " text not null, " +
		LAST_3_CHARS + " text not null, " +
		LAST_2_CHARS + " text not null, " +
		LAST_CHAR + " text not null, " + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	Log.w("TaskDBAdapter", "Upgrading from version " + oldVersion + " to "
		+ newVersion + ", which will destroy all old data");
	db.execSQL("drop table if exists " + TABLE_NAME);
	onCreate(db);
    }

}
