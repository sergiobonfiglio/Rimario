package org.phantomsoft.rimarioapp.data;

import java.util.Iterator;

import android.database.Cursor;
import android.database.CursorWrapper;

public class IterableCursor extends CursorWrapper implements Iterator<String> {

    private Cursor cursor;
    private String columnName;

    public IterableCursor(Cursor cursor, String columnName)
	    throws IllegalColumnNameException {
	super(cursor);
	this.cursor = cursor;

	if (!isLegal(columnName)) {
	    throw new IllegalColumnNameException();
	} else {
	    this.columnName = columnName;
	}

    }

    private boolean isLegal(String columnNames) {

	String[] legalNames = getLegalColumnNames(cursor);

	for (int j = 0; j < legalNames.length; j++) {
	    if (columnName.equals(legalNames[j])) {
		return true;
	    }
	}

	return false;
    }

    public static String[] getLegalColumnNames(Cursor c) {
	return c.getColumnNames();
    }

    @Override
    public boolean hasNext() {
	return (cursor.getPosition() < cursor.getCount());
    }

    @Override
    public String next() {
	if (this.hasNext()) {
	    cursor.moveToNext();
	    return cursor.getString(cursor.getColumnIndex(columnName));
	} else {
	    return null;
	}
    }

    @Override
    public void remove() throws UnsupportedOperationException {

	throw new UnsupportedOperationException();

    }
}
