package com.example.citytour;


import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SecondActivity extends Activity {
	
	ProgressDialog pDialog;
	Button goButton;
	ListView listView;
	TextView textView;
	CustomAdapter customAdapter;
	RutaAdapter routeAdapter;
	ArrayList<Bar> bars;
	Bar bar;
	Route ruta;
	ArrayList<Route> rutas;
	String[] routes, rutaMA, ruta1, ruta2, ruta4, bares, barCoord;
	int indexZonas,numZonas, tipoRecorrido;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		rutaMA = getResources().getStringArray(R.array.ruta_madrid_de_los_austrias);
		ruta1 = getResources().getStringArray(R.array.ruta_ruta1);
		ruta2 = getResources().getStringArray(R.array.ruta_ruta2);
		ruta4 = getResources().getStringArray(R.array.ruta_ruta4);
		bares = getResources().getStringArray(R.array.array_bares_madrid);
		bars = makeBars(bares);
		barCoord = getResources().getStringArray(R.array.array_coordinates_bars);
		routes = getResources().getStringArray(R.array.array_rutas);
		rutas = makeRoutes(routes);
        tipoRecorrido = b.getInt("indexRecorrido");
        indexZonas = tipoRecorrido;
        if(indexZonas==0){
        	// recorrido cultural
        	for(int i=0; i<rutas.size(); i++){
        		Log.d("Rutas",rutas.get(i).getName());
        	}
        	routeAdapter = new RutaAdapter(this, rutas);
        	listView = (ListView) findViewById(R.id.listaZonas);
        	goButton = (Button)findViewById(R.id.goButton2);
        	textView = (TextView)findViewById(R.id.textoQueVer);
        	listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        	listView.setAdapter(routeAdapter);
        	listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
    			@Override
    			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
    				ruta = (Route)parent.getItemAtPosition(position);
    			}
    		});
        	
        }else if(indexZonas==1){
        	// recorrido de bares y tapas
        	for(int i=0; i<bars.size(); i++){
        		Log.d("Bares",bars.get(i).getName());
        	}
        	customAdapter = new CustomAdapter(this,bars);
        	listView = (ListView)findViewById(R.id.listaZonas);
    		goButton = (Button)findViewById(R.id.goButton2);
    		textView = (TextView)findViewById(R.id.textoQueVer);
    		textView.setText(getResources().getString(R.string.textoQueTomar));
    		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    		listView.setAdapter(customAdapter);
    		listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
    			@Override
    			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
    				bar = (Bar)parent.getItemAtPosition(position);
    			}
    		});
        }

        getActionBar().setDisplayHomeAsUpEnabled(true);
        goButton.setOnClickListener(new View.OnClickListener(){
        	
        	@Override
        	public void onClick(View v){
        		if(indexZonas==0){
        			if(ruta.getName().equals("Madrid de los Austrias")){
        				String[] coord = getRutaMA();
						gotoMapActivity(v, coord, indexZonas, ruta.getDescription());
        			}else if(ruta.getName().equals("Ruta I")){
        				String[] coord = getRuta1();
						gotoMapActivity(v, coord, indexZonas, ruta.getDescription());
        			}else if(ruta.getName().equals("Ruta II")){
        				String[] coord = getRuta2();
						gotoMapActivity(v, coord, indexZonas, ruta.getDescription());
        			}else if(ruta.getName().equals("Ruta IV")){
        				String[] coord = getRuta4();
						gotoMapActivity(v, coord, indexZonas, ruta.getDescription());
        			}else return;
        		}else if(indexZonas==1){
        			ArrayList<Bar> bars = getBars();
        			String[] barCoord = getBarCoord();
    				for(int i=0; i<bars.size(); i++){
    					Log.d("UNO", bars.get(i).getName());
    					Log.d("OTRO", bar.getName());
    					Log.d("BOOLEANO", String.valueOf(bars.get(i).getName().equals(bar.getName())));
    					if(bars.get(i).getName().equals(bar.getName())){
    						String coord_bar = barCoord[i];
    						gotoMapActivity(v, coord_bar, indexZonas, bar.getName());
    					}
    				}
        		}
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
	
	private ArrayList<Bar> makeBars(String[] bars){
		ArrayList<Bar> bares = new ArrayList<Bar>();
		for(int i=0; i<bars.length-1; i+=2){
			Bar item = new Bar(bars[i], bars[i+1]);
			bares.add(item);
		}
		return bares;
	}
	
	private ArrayList<Bar> getBars(){
		return bars;
	}
    
    private String[] getBarCoord(){
    	return barCoord;
    }
    
    private String[] getRutaMA(){
    	return rutaMA;
    }
    
    private String[] getRuta1(){
    	return ruta1;
    }
    
    private String[] getRuta2(){
    	return ruta2;
    }
    
    private String[] getRuta4(){
    	return ruta4;
    }
    
    private ArrayList<Route> makeRoutes(String[] routes){
		ArrayList<Route> rutas = new ArrayList<Route>();
		for(int i=0; i<routes.length-1; i+=2){
			Route item = new Route(routes[i], routes[i+1]);
			rutas.add(item);
		}
		return rutas;
	}
	
	public void gotoMapActivity(View v, String[] coordinates, int value, String description){
		Intent intent = new Intent(this, DisplayOnMapActivity.class);
		intent.putExtra("coordinates", coordinates);
		intent.putExtra("tipoRecorrido", value);
		intent.putExtra("description", description);
		startActivity(intent);
	}
	
	public void gotoMapActivity(View v, String coordinates, int value, String bar){
		Intent intent = new Intent(this, DisplayOnMapActivity.class);
		intent.putExtra("coordinates", coordinates);
		intent.putExtra("tipoRecorrido", value);
		intent.putExtra("bar", bar);
		startActivity(intent);
	}

}
