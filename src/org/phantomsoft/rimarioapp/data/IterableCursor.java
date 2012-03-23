package org.phantomsoft.rimarioapp.data;

import java.util.Iterator;

import android.database.Cursor;
import android.database.CursorWrapper;

public class IterableCursor extends CursorWrapper implements Iterable<String>,
	Iterator<String> {

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

    private boolean isLegal(String columnName) {

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

    public boolean hasNext() {
	return cursor.getCount() != 0
		&& (cursor.getPosition() < cursor.getCount()-1);
    }

    public String next() {
	if (this.hasNext()) {
	    cursor.moveToNext();
	    return cursor.getString(cursor.getColumnIndex(columnName));
	} else {
	    return null;
	}
    }

    public void remove() throws UnsupportedOperationException {

	throw new UnsupportedOperationException();

    }

    public Iterator<String> iterator() {
	return this;
    }
}
