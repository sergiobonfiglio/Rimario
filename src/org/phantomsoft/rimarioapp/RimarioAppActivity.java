package org.phantomsoft.rimarioapp;

import java.io.InputStream;
import java.util.ArrayList;

import org.phantomsoft.rimarioapp.dictionary.Dictionary;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class RimarioAppActivity extends Activity {

    private Context context;

    private ArrayList<String> listData;
    private Dictionary dictionary;

    private ListView list;
    private ListAdapter listAdapter;

    private EditText text;
    private TextChangeListener textListener;

    private Spinner spinner;
    private SpinnerListener spinnerListener;

    private ProgressDialog progressDialog;
    private Handler handler;

    public static final int PROGRESS_DIALOG = 0;

    @Override
    protected Dialog onCreateDialog(int id) {
	switch (id) {
	case PROGRESS_DIALOG:
	    progressDialog = new ProgressDialog(RimarioAppActivity.this);
	    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	    progressDialog.setMessage("Loading...");
	    return progressDialog;
	default:
	    return null;
	}
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
	switch (id) {
	case PROGRESS_DIALOG:
	    progressDialog.setProgress(0);
	}
    }
    
    

    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	this.context = getApplicationContext();
	setContentView(R.layout.main);

	handler = new Handler() {
	    public void handleMessage(Message msg) {
		int total = msg.arg1;
		progressDialog.setProgress(total);
		// if (total >= 100) {
		// progressDialog.dismiss();
		// }
	    }
	};

	Resources res = getResources();
	InputStream is = res.openRawResource(R.raw.wordlist);

	dictionary = new Dictionary(is, this, progressDialog, handler);

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
