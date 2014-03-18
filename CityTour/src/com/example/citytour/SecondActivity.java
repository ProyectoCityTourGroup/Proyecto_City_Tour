package com.example.citytour;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class SecondActivity extends Activity {
	
	ProgressDialog pDialog;
	Button goButton;
	ListView listView;
	TextView textView;
	ArrayAdapter<String> adapter;
	CustomAdapter customAdapter;
	ArrayList<Bar> bars;
	String[] zonas,cosasQueVer;
	int[] indices;
	int indexZonas,numZonas, tipoRecorrido;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
//        String ciudad = b.getString("ciudad");
        tipoRecorrido = b.getInt("indexRecorrido");
        indexZonas = tipoRecorrido;
        if(indexZonas==0){
        	cosasQueVer = getResources().getStringArray(R.array.array_zonas_madrid);
        	adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,cosasQueVer);
        	listView = (ListView)findViewById(R.id.listaZonas);
    		goButton = (Button)findViewById(R.id.goButton2);
    		textView = (TextView)findViewById(R.id.textoQueVer);
    		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    		listView.setAdapter(adapter);
        }else if(indexZonas==1){
        	cosasQueVer = getResources().getStringArray(R.array.array_bares_madrid);
        	bars = makeBars(cosasQueVer);
        	customAdapter = new CustomAdapter(this,bars);
        	listView = (ListView)findViewById(R.id.listaZonas);
    		goButton = (Button)findViewById(R.id.goButton2);
    		textView = (TextView)findViewById(R.id.textoQueVer);
    		textView.setText(getResources().getString(R.string.textoQueTomar));
    		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    		listView.setAdapter(customAdapter);
        }

        getActionBar().setDisplayHomeAsUpEnabled(true);
        goButton.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View v){
        		String selected = "";
        		if(indexZonas==0){
        			SparseBooleanArray checked = listView.getCheckedItemPositions();
        			int countChoice = listView.getCount();
        			for(int i=0; i < countChoice; i++){
            			if(checked.get(i)){
            				selected+=listView.getItemAtPosition(i).toString()+"\n";
            			}
            		}
        		}else if(indexZonas==1){
        			SparseBooleanArray checked = customAdapter.getCheckedItemPositions();
        			int countChoice = customAdapter.getNumChecked();
        			Log.d("NUM_CHECKED", String.valueOf(countChoice));
        			for(int i=0; i < checked.size(); i++){
            			if(checked.get(i)){
            				selected+=customAdapter.getItem(i).getName()+"\n";
            			}
            		}
        		}
        		zonas = selected.split("\n");
        		for(int i=0; i<zonas.length;i++){
        			Log.d("ZONAS:", zonas[i]);
        		}
        		gotoMapActivity(v,zonas,indexZonas);
        	}
        });
                
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

	public void showZonasSeleccionadas(View view){
		Intent intent = new Intent(this, SelectedZonesActivity.class);
		intent.putExtra("zonas", zonas);
		startActivity(intent);
	}
	
	public void gotoMapActivity(View view, String[] zonas, int tipoRecorrido){
		Intent intent = new Intent(this, DisplayOnMapActivity.class);
		intent.putExtra("zonas", zonas);
		intent.putExtra("tipoRecorrido", tipoRecorrido);
		startActivity(intent);
	}
	
	private ArrayList<Bar> makeBars(String[] bars){
		ArrayList<Bar> bares = new ArrayList<Bar>();
		for(int i=0; i<bars.length-1; i+=2){
			Bar item = new Bar(bars[i], bars[i+1]);
			bares.add(item);
		}
		return bares;
	}
}
