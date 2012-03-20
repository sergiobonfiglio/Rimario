package org.phantomsoft.rimarioapp.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

public class Dictionary {
    private List<String> dictionary;
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

	DictionaryLoader dictLoading = new DictionaryLoader(activity);
	dictLoading.execute(is);
	// try {
	// this.dictionary = dictLoading.get();
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// } catch (ExecutionException e) {
	// e.printStackTrace();
	// }

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

class DictionaryLoader extends AsyncTask<InputStream, Integer, List<String>> {

    Activity context;
    private ProgressDialog dialog;

    public DictionaryLoader(Activity context) {
	this.context = context;
	this.dialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
	this.dialog.setMessage("Loading dictionary");
	dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	dialog.setCancelable(false);
	this.dialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
	dialog.setProgress(values[0]);
    }

    @Override
    protected List<String> doInBackground(InputStream... params) {
	ArrayList<String> tempList = new ArrayList<String>();

	int count = 0;

	try {
	    for (int i = 0; i < params.length; i++) {
		InputStreamReader isr = new InputStreamReader(params[i]);
		BufferedReader br = new BufferedReader(isr);

		
		String line;
		line = br.readLine();

		while (line != null) {
		    tempList.add(line);
		    // suffixTree.add(line);
		    line = br.readLine();

		    publishProgress(count++);
		}
		br.close();
		isr.close();
		params[i].close();
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}

	return tempList;
    }

    @Override
    protected void onPostExecute(List<String> result) {

	Toast.makeText(context, "Dictionary loaded", Toast.LENGTH_LONG).show();

	if (this.dialog.isShowing()) {
	    this.dialog.dismiss();
	}

    }

}