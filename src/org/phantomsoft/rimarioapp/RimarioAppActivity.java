package org.phantomsoft.rimarioapp;

import java.io.IOException;
import java.util.ArrayList;

import org.phantomsoft.rimarioapp.data.DictionaryDBHelper;
import org.phantomsoft.rimarioapp.data.SuffixFinder;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class RimarioAppActivity extends Activity {

    private Context context;

    private ArrayList<String> listData;
    // private Dictionary dictionary;
    private SuffixFinder suffixFinder;

    private ListView list;
    private ListAdapter listAdapter;

    private EditText text;
    private TextChangeListener textListener;

    private Spinner spinner;
    private SpinnerListener spinnerListener;

    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	this.context = getApplicationContext();
	setContentView(R.layout.main);

	// dictionary = new Dictionary(is, this, progressDialog, handler);
	try {
	    suffixFinder = new DictionaryDBHelper(context);
	} catch (IOException e) {
	    Log.e("Rimario", "An error occured while opening the db");
	    e.printStackTrace();
	}

	this.listData = new ArrayList<String>();
	this.listAdapter = new ListAdapter(this,
		android.R.layout.simple_list_item_1, android.R.id.text1,
		listData, suffixFinder);

	this.list = (ListView) findViewById(R.id.listView);

	// Assign adapter to ListView
	this.list.setAdapter(this.listAdapter);
	this.spinner = (Spinner) findViewById(R.id.spinner1);

	ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
		.createFromResource(this, R.array.rhyme_length2,
			android.R.layout.simple_spinner_item);
	spinnerAdapter
		.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	spinner.setAdapter(spinnerAdapter);

	this.text = (EditText) findViewById(R.id.editText1);
	int selectedSuffixLength = Integer.parseInt(spinnerAdapter.getItem(
		spinner.getSelectedItemPosition()).toString());
	this.textListener = new TextChangeListener(selectedSuffixLength, this);
	text.addTextChangedListener(textListener);
	textListener.addObserver(listAdapter);

	spinnerListener = new SpinnerListener();
	spinner.setOnItemSelectedListener(spinnerListener);
	spinnerListener.addObserver(textListener);

    }
}
