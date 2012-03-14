package org.phantomsoft.rimarioapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RimarioAppActivity extends Activity {

    private HashMap<String, Object> dictionary;

    private String[] dictionaryArray;

    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	dictionary = new HashMap<String, Object>();

	try {
	    this.dictionaryArray = loadFile();

	    ListView listView = (ListView) findViewById(R.id.listView);

	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		    android.R.layout.simple_list_item_1, android.R.id.text1,
		    dictionaryArray);

	    // Assign adapter to ListView
	    listView.setAdapter(adapter);

	} catch (IOException e) {

	    e.printStackTrace();
	}

    }

    private String[] loadFile() throws IOException {

	Resources res = getResources();
	InputStream is = res.openRawResource(R.raw.wordlist);
	InputStreamReader isr = new InputStreamReader(is);
	BufferedReader br = new BufferedReader(isr);

	// TODO: build here a tree from the dictionary for flexible/fast suffix
	// matching

	ArrayList<String> tempList = new ArrayList<String>();

	String line = br.readLine();
	while (line != null) {
	    tempList.add(line);
	    line = br.readLine();
	}

	return (String[]) tempList.toArray();
    }

}