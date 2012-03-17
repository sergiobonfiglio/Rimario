package org.phantomsoft.rimarioapp;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

class TextChangeListener extends Observable implements TextWatcher, Observer {

    String text;
    int suffixLength;
    String prevSuffix;
    long lastTimeTextChanged;
    private long waitTime;
    private Timer timer;
    private Activity activity;

    public TextChangeListener(int suffixLength, Activity activity) {
	this.timer = new Timer();
	this.suffixLength = suffixLength;
	this.prevSuffix = "";
	this.lastTimeTextChanged = System.currentTimeMillis();
	this.waitTime = 2000;
	this.activity = activity;
    }

    public void afterTextChanged(Editable s) {

	this.text = s.toString();

	// reset timer;
	timer.cancel();
	timer = new Timer();

	// schedule a new job
	this.timer.schedule(new TimerTask() {
	    @Override
	    public void run() {
		update(null, "" + suffixLength);
	    }
	}, waitTime);

    }

    public void beforeTextChanged(CharSequence s, int start, int count,
	    int after) {

    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    public void update(Observable arg0, Object suffixLen) {

	
	// update from the spinner listener
	int length = Integer.parseInt((String) suffixLen);

	int startingPos = text.length() - length;
	startingPos = startingPos >= 0 ? startingPos : 0;

	final String suffix = this.text.substring(startingPos);

	if (!suffix.equals(prevSuffix)) {
	    this.setChanged();
	    prevSuffix = suffix;
	}

	// notify the list adapter with the new suffix
	activity.runOnUiThread(new Runnable() {
	    public void run() {
		Toast.makeText(activity, "Updating...("+suffix+")", Toast.LENGTH_SHORT).show();
		notifyObservers(suffix);
	    }
	});

    }
}
