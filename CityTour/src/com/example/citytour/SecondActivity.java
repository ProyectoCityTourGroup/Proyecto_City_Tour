package com.example.citytour;


import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
	String[] routes, rutaMA, ruta1, ruta2, ruta3, ruta4, ruta5, bares, barCoord;
	int indexZonas,numZonas, tipoRecorrido, duration;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		tipoRecorrido = b.getInt("indexRecorrido");
		duration = b.getInt("indexDuration");
		if(duration==0){
			// 30 minutes
			duration = 30;
		}else if(duration==1){
			// 1 hour
			duration = 60;
		}else if(duration==2){
			// 3 hours
			duration = 60*3;
		}else if(duration==3){
			// 6 hours
			duration = 60*6;
		}
		rutaMA = getResources().getStringArray(R.array.ruta_madrid_de_los_austrias);
		ruta1 = getResources().getStringArray(R.array.ruta_ruta1);
		ruta2 = getResources().getStringArray(R.array.ruta_ruta2);
		ruta3 = getResources().getStringArray(R.array.ruta_ruta3);
		ruta4 = getResources().getStringArray(R.array.ruta_ruta4);
		ruta5 = getResources().getStringArray(R.array.ruta_ruta5);
		bares = getResources().getStringArray(R.array.array_bares_madrid);
		bars = makeBars(bares);
		barCoord = getResources().getStringArray(R.array.array_coordinates_bars);
		routes = getResources().getStringArray(R.array.array_rutas);
		rutas = makeRoutes(routes, duration);
        
        if(tipoRecorrido==0){
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
        	
        }else if(tipoRecorrido==1){
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
        		if(tipoRecorrido==0){
        			if(ruta!=null){
        				if(ruta.getName().equals("Madrid de los Austrias")){
            				String[] coord = getRutaMA();
    						gotoMapActivity(v, coord, indexZonas, ruta.getDescription());
            			}else if(ruta.getName().equals("Ruta I")){
            				String[] coord = getRuta1();
    						gotoMapActivity(v, coord, indexZonas, ruta.getDescription());
            			}else if(ruta.getName().equals("Ruta II")){
            				String[] coord = getRuta2();
    						gotoMapActivity(v, coord, indexZonas, ruta.getDescription());
            			}else if(ruta.getName().equals("Ruta III")){
            				String[] coord = getRuta3();
    						gotoMapActivity(v, coord, indexZonas, ruta.getDescription());
            			}else if(ruta.getName().equals("Ruta IV")){
            				String[] coord = getRuta4();
    						gotoMapActivity(v, coord, indexZonas, ruta.getDescription());
            			}else if(ruta.getName().equals("Ruta V")){
            				String[] coord = getRuta5();
    						gotoMapActivity(v, coord, indexZonas, ruta.getDescription());
            			}else return;
        			}else Toast.makeText(getBaseContext(),"Selecciona una ruta",Toast.LENGTH_LONG).show();
        		}else if(tipoRecorrido==1){
        			if (bar!=null){
        				ArrayList<Bar> bars = getBars();
            			String[] barCoord = getBarCoord();
        				for(int i=0; i<bars.size(); i++){
        					if(bars.get(i).getName().equals(bar.getName())){
        						String coord_bar = barCoord[i];
        						gotoMapActivity(v, coord_bar, indexZonas, bar.getName());
        					}
        				}
        			}else Toast.makeText(getBaseContext(),"Selecciona un bar",Toast.LENGTH_LONG).show();
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
		for(int i=0; i<bars.length-1; i+=3){
			Bar item = new Bar(bars[i], bars[i+1], bars[i+2]);
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
    
    private String[] getRuta3(){
    	return ruta3;
    }
    
    private String[] getRuta4(){
    	return ruta4;
    }
    
    private String[] getRuta5(){
    	return ruta5;
    }
    
    // Selects the routes which duration is less or equal to the value chosen by the user 
    private ArrayList<Route> makeRoutes(String[] routes, int duration){
		ArrayList<Route> rutas = new ArrayList<Route>();
		for(int i=0; i<routes.length-1; i+=3){
			if(Integer.parseInt(routes[i+2])<=duration){
				Route item = new Route(routes[i], routes[i+1], Integer.parseInt(routes[i+2]));
				rutas.add(item);
			}
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
