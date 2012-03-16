package org.phantomsoft.rimarioapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

class Dictionary {
    private ArrayList<String> dictionary;
    String[] data;

    public Dictionary(InputStream is) {
	try {
	    this.dictionary = loadFile(is);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private ArrayList<String> loadFile(InputStream is) throws IOException {

	// TODO: insert as a parameter

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
	br.close();
	isr.close();
	is.close();

	return tempList;
    }

    public ArrayList<String> searchSuffix(String suffix) {

	ArrayList<String> result = new ArrayList<String>();

	for (String word : this.dictionary) {
	    if (word.endsWith(suffix)) {
		result.add(word);
	    }
	}

	return result;
    }

}