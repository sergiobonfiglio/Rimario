package org.phantomsoft.rimarioapp;

import java.util.HashMap;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RimarioAppActivity extends Activity {
    
    private HashMap<String,Object> dictionary;
    
    private String[] dictionaryArray;
    
    /** Called when the activity is first created. */
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        dictionary = new HashMap<String, Object>();
        
        Resources res = getResources();
        String[] dictArray = res.getStringArray(R.array.dictionary);
        this.dictionaryArray = dictArray;     
        
        ListView listView = findViewById(R.id.mylist);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        	android.R.layout.simple_list_item_1, android.R.id.text1, dictArray);

        // Assign adapter to ListView
        listView.setAdapter(adapter);
        
    }
    
   
    
}