package org.phantomsoft.rimarioapp;

import java.util.Observable;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

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