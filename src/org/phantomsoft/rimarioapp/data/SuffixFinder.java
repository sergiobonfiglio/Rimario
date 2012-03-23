package org.phantomsoft.rimarioapp.data;

import java.io.IOException;

import android.database.sqlite.SQLiteException;


public interface SuffixFinder {

    public Iterable<String> findSuffix(String suffix) throws SQLiteException, IOException;

}
