package org.phantomsoft.rimarioapp;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import org.phantomsoft.rimarioapp.data.SuffixFinder;

import android.content.Context;
import android.widget.ArrayAdapter;

class ListAdapter extends ArrayAdapter<String> implements Observer {

    private SuffixFinder suffixFinder;

    public ListAdapter(Context context, int resource, int textViewResourceId,
	    ArrayList<String> objects, SuffixFinder dictionary) {
	super(context, resource, textViewResourceId, objects);

	this.suffixFinder = dictionary;
    }

    public void update(Observable observable, Object suffix) {

	// search suffix
	Iterable<String> result = suffixFinder.findSuffix((String) suffix);

	this.setNotifyOnChange(false);

	this.clear();

	for (String word : result) {
	    this.add(word);
	}

	// update listview
	notifyDataSetChanged();
    }
}