package org.phantomsoft.rimarioapp.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.widget.Toast;

public class Dictionary {
    private ArrayList<String> dictionary;
    String[] data;

    private SuffixesTree suffixTree;
    private Handler handler;

    private Activity activity;
    private ProgressDialog progressDialog;

    public Dictionary(final InputStream is, Activity activity,
	    ProgressDialog progressDialog, Handler handler) {
	this.handler = handler;
	this.activity = activity;
	this.progressDialog = progressDialog;

	this.suffixTree = new SuffixesTree();
	new Thread() {
	    @Override
	    public void run() {
		try {
		    loadFile(is);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}.start();

    }

    private void loadFile(InputStream is) throws IOException {

	// activity.showDialog(RimarioAppActivity.PROGRESS_DIALOG);

	InputStreamReader isr = new InputStreamReader(is);
	BufferedReader br = new BufferedReader(isr);

	ArrayList<String> tempList = new ArrayList<String>();
	String line = br.readLine();
	while (line != null) {
	    tempList.add(line);
	    // suffixTree.add(line);
	    line = br.readLine();

	    // Message msg = handler.obtainMessage();
	    // msg.arg1 += 1;
	    // handler.sendMessage(msg);
	}
	br.close();
	isr.close();
	is.close();

	this.dictionary = tempList;

	// notify the list adapter with the new suffix
	activity.runOnUiThread(new Runnable() {
	    public void run() {
		Toast.makeText(activity, "Dictionary loaded",
			Toast.LENGTH_SHORT).show();
	    }
	});
	// return tempList;
    }

    public ArrayList<String> searchSuffix(String suffix) {

	ArrayList<String> result = new ArrayList<String>();

	for (String word : this.dictionary) {
	    if (word.endsWith(suffix)) {
		result.add(word);
	    }
	}

	// String endSuffix = "z" + suffix;
	// return this.suffixTree.subSet(suffix, endSuffix);

	return result;
    }

}