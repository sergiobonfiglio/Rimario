package org.phantomsoft.rimarioapp;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import org.phantomsoft.rimarioapp.dictionary.Dictionary;

import android.content.Context;
import android.widget.ArrayAdapter;

class ListAdapter extends ArrayAdapter<String> implements Observer {

    private Dictionary dictionary;

    public ListAdapter(Context context, int resource, int textViewResourceId,
	    ArrayList<String> objects, Dictionary dictionary) {
	super(context, resource, textViewResourceId, objects);

	this.dictionary = dictionary;
    }

    public void update(Observable observable, Object suffix) {

	// search suffix
	ArrayList<String> result = dictionary.searchSuffix((String) suffix);

	this.setNotifyOnChange(false);

	this.clear();

	for (String word : result) {
	    this.add(word);
	}

	// update listview
	notifyDataSetChanged();
    }
}