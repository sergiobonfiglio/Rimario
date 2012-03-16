package org.phantomsoft.rimarioapp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class RimarioAppActivity extends Activity {

    private String[] listData;
    private ListAdapter listAdapter;
    private Dictionary dictionary;

    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	Resources res = getResources();
	InputStream is = res.openRawResource(R.raw.wordlist);

	dictionary = new Dictionary(is);

	this.listData = null;
	this.listAdapter = new ListAdapter(this,
		android.R.layout.simple_list_item_1, android.R.id.text1,
		listData, dictionary);

	initListView();

	initSpinner();

    }

    private void initListView() {

	ListView listView = (ListView) findViewById(R.id.listView);

	// Assign adapter to ListView
	listView.setAdapter(this.listAdapter);

    }

    private void initSpinner() {
	Spinner spinner = (Spinner) findViewById(R.id.spinner1);

	ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
		.createFromResource(this, R.array.rhyme_length2,
			android.R.layout.simple_spinner_item);
	spinnerAdapter
		.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	spinner.setAdapter(spinnerAdapter);

	// init listener
	SpinnerListener spinnerListener = new SpinnerListener();

	spinner.setOnItemSelectedListener(spinnerListener);

    }

}

class ListAdapter extends ArrayAdapter<String> implements Observer {

    private Dictionary dictionary;

    public ListAdapter(Context context, int resource, int textViewResourceId,
	    String[] objects, Dictionary dictionary) {
	super(context, resource, textViewResourceId, objects);

	this.dictionary = dictionary;
    }

    public void update(Observable observable, Object data) {

	// search suffix
	String suffix = (String) data;
	ArrayList<String> result = dictionary.searchSuffix(suffix);

	this.setNotifyOnChange(false);
	this.clear();

	for (String word : result) {
	    this.add(word);
	}

	// update listview
	notifyDataSetChanged();
    }

}

class SpinnerListener extends Observable implements OnItemSelectedListener {

    int prevPos;

    public SpinnerListener() {
	prevPos = 0;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos,
	    long id) {

	if (pos != prevPos) {
	    prevPos = pos;
	    setChanged();
	    notifyObservers(parent.getItemAtPosition(pos).toString());
	}

    }

    public void onNothingSelected(AdapterView<?> arg0) {
	// TODO Auto-generated method stub

    }

}
