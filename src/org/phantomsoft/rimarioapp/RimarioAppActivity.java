package org.phantomsoft.rimarioapp;

import java.io.InputStream;
import java.util.ArrayList;

import org.phantomsoft.rimarioapp.data.DictionaryDBHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class RimarioAppActivity extends Activity {

    private Context context;

    private ArrayList<String> listData;
    //private Dictionary dictionary;
    private DictionaryDBHelper db;

    private ListView list;
    private ListAdapter listAdapter;

    private EditText text;
    private TextChangeListener textListener;

    private Spinner spinner;
    private SpinnerListener spinnerListener;

    private ProgressDialog progressDialog;
    private Handler handler;

    public static final int PROGRESS_DIALOG = 0;

    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	this.context = getApplicationContext();
	setContentView(R.layout.main);

	Resources res = getResources();
	InputStream is = res.openRawResource(R.raw.prova);

	//dictionary = new Dictionary(is, this, progressDialog, handler);

	this.listData = new ArrayList<String>();
	this.listAdapter = new ListAdapter(this,
		android.R.layout.simple_list_item_1, android.R.id.text1,
		listData, dictionary);

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
