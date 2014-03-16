package com.example.citytour;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class SecondActivity extends Activity {
	
	ProgressDialog pDialog;
	Button goButton;
	ListView listView;
	TextView textView;
	ArrayAdapter<String> adapter;
	String[] zonas,cosasQueVer;
	int[] indices;
	int indexZonas,numZonas, tipoRecorrido;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
        String ciudad = b.getString("ciudad");
        tipoRecorrido = b.getInt("indexRecorrido");
        indexZonas = tipoRecorrido;
        if(tipoRecorrido==0){
        	cosasQueVer = getResources().getStringArray(R.array.array_zonas_madrid);
        }else if(tipoRecorrido==1){
        	cosasQueVer = getResources().getStringArray(R.array.array_bares_madrid);
        }
		listView = (ListView)findViewById(R.id.listaZonas);
		goButton = (Button)findViewById(R.id.goButton2);
		textView = (TextView)findViewById(R.id.textoQueVer);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,cosasQueVer);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listView.setAdapter(adapter);
		
		// inicializamos numero de zonas seleccionadas a cero
		numZonas=0;

        Toast.makeText(getBaseContext(), "Selected city: "+ciudad, Toast.LENGTH_SHORT).show();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        goButton.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View v){
        		String selected = "";
        		int countChoice = listView.getCount();
        		SparseBooleanArray checked = listView.getCheckedItemPositions();
        		for(int i=0; i < countChoice; i++){
        			if(checked.get(i)){
        				selected+=listView.getItemAtPosition(i).toString()+"\n";
        			}
        		}
        		zonas = selected.split("\n");
//        		showZonasSeleccionadas(v);
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
	
}
