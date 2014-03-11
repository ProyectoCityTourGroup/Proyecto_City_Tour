package com.example.citytour;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

	String[] ciudad,recorrido,duracion;
	String pName = "";
	int indexCiudad,indexRecorrido,indexDuracion;
	final Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Spinner de las ciudades
		Spinner spinnerCiudades = (Spinner) findViewById(R.id.spinnerCiudades);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapterCiudades = ArrayAdapter.createFromResource(this,
		        R.array.array_ciudades, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapterCiudades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerCiudades.setAdapter(adapterCiudades);
		spinnerCiudades.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        // your code here
		    	indexCiudad = parentView.getSelectedItemPosition();
                
	            // storing string resources into Array
	            ciudad = getResources().getStringArray(R.array.array_ciudades);
	                    
	        }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }

		});
		
		// Spinner de los recorridos
		Spinner spinnerRecorridos = (Spinner) findViewById(R.id.spinnerRecorridos);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapterRecorridos = ArrayAdapter.createFromResource(this,
		        R.array.array_recorridos, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapterRecorridos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerRecorridos.setAdapter(adapterRecorridos);
		spinnerRecorridos.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        // your code here
		    	indexRecorrido = parentView.getSelectedItemPosition();
                
	            // storing string resources into Array
	            recorrido = getResources().getStringArray(R.array.array_recorridos);
	                
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }

		});
		
		// Spinner de las duraciones
		Spinner spinnerDuraciones = (Spinner) findViewById(R.id.spinnerDuraciones);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapterDuraciones = ArrayAdapter.createFromResource(this,
		        R.array.array_duraciones, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapterDuraciones.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerDuraciones.setAdapter(adapterDuraciones);
		spinnerDuraciones.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        // your code here
		    	indexDuracion = parentView.getSelectedItemPosition();
                
	            // storing string resources into Array
	            duracion = getResources().getStringArray(R.array.array_duraciones);
	           
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }

		});
		
		// busca si está instalada la app del metro/bus/cercanias y, si no,
		// la sugiere
		pName = "com.dmp.free.madrid";
		android.content.pm.PackageManager mPm = getPackageManager();
		PackageInfo info = null;
		try {
			info = mPm.getPackageInfo(pName, 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Boolean installed = info != null;
		
		if(!installed){
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			// set title
			alertDialogBuilder.setTitle(R.string.atencion);
			// set dialog message
			alertDialogBuilder.setMessage(R.string.installMetroApp);
			alertDialogBuilder.setCancelable(false);
			alertDialogBuilder.setPositiveButton(R.string.yes,new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					Intent intent = new Intent(Intent.ACTION_VIEW);
//					intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.dmp.free.madrid&hl=en"));
					intent.setData(Uri.parse("market://details?id=com.dmp.free.madrid"));
					startActivity(intent);
				}
			  });
			alertDialogBuilder.setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, just close
					// the dialog box and do nothing
					dialog.cancel();
				}
			});
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void showPzaEspañaPage(View view){
		Intent intent = new Intent(this, InfoActivity.class);
		String url = getResources().getString(R.string.pzaEspaña);
		intent.putExtra("url", url);
		startActivity(intent);
	}
	
	public void showTemploDebodPage(View view){
		Intent intent = new Intent(this, InfoActivity.class);
		String url = getResources().getString(R.string.temploDebod);
		intent.putExtra("url", url);
		startActivity(intent);
	}
	
	public void showMuseoReinaSofiaPage(View view){
		Intent intent = new Intent(this, InfoActivity.class);
		String url = getString(R.string.museoReinaSofia);
		intent.putExtra("url", url);
		startActivity(intent);
	}
	
	public void showMuseoDelPradoPage(View view){
		Intent intent = new Intent(this, InfoActivity.class);
		String url = getString(R.string.museoPrado);
		intent.putExtra("url", url);
		startActivity(intent);
	}
	
	public void showMuseoThyssenPage(View view){
		Intent intent = new Intent(this, InfoActivity.class);
		String url = getString(R.string.museoThyssen);
		intent.putExtra("url", url);
		startActivity(intent);
	}
	
	public void showCaixaForumPage(View view){
		Intent intent = new Intent(this, InfoActivity.class);
		String url = getString(R.string.museoCaixaForum);
		intent.putExtra("url", url);
		startActivity(intent);
	}
	
	public void showMuseoLazaroGaldianoPage(View view){
		Intent intent = new Intent(this, InfoActivity.class);
		String url = getString(R.string.museoLazaroGaldiano);
		intent.putExtra("url", url);
		startActivity(intent);
	}
	
	public void showMuseoSorollaPage(View view){
		Intent intent = new Intent(this, InfoActivity.class);
		String url = getString(R.string.museoSorolla);
		intent.putExtra("url", url);
		startActivity(intent);
	}
	
	public void showMuseoArqueologicoPage(View view){
		Intent intent = new Intent(this, InfoActivity.class);
		String url = getString(R.string.museoArqueologico);
		intent.putExtra("url", url);
		startActivity(intent);
	}
	
	public void showMuseoNavalPage(View view){
		Intent intent = new Intent(this, InfoActivity.class);
		String url = getString(R.string.museoNaval);
		intent.putExtra("url", url);
		startActivity(intent);
	}
	
	public void showPalacioRealPage(View view){
		Intent intent = new Intent(this, InfoActivity.class);
		String url = getString(R.string.palacioReal);
		intent.putExtra("url", url);
		startActivity(intent);
	}
	
	public void showPuertaDelSolPage(View view){
		Intent intent = new Intent(this, InfoActivity.class);
		String url = getString(R.string.puertaDelSol);
		intent.putExtra("url", url);
		startActivity(intent);
	}
	
	public void showPlazaMayorPage(View view){
		Intent intent = new Intent(this, InfoActivity.class);
		String url = getString(R.string.pzaMayor);
		intent.putExtra("url", url);
		startActivity(intent);
	}
	
	public void showColegiataSanIsidroPage(View view){
		Intent intent = new Intent(this, InfoActivity.class);
		String url = getString(R.string.colegiataSanIsidro);
		intent.putExtra("url", url);
		startActivity(intent);
	}
	
	public void showPlazaDeLaVillaPage(View view){
		Intent intent = new Intent(this, InfoActivity.class);
		String url = getString(R.string.pzaDeLaVilla);
		intent.putExtra("url", url);
		startActivity(intent);
	}
	
	public void showPuertaDeAlcalaPage(View view){
		Intent intent = new Intent(this, InfoActivity.class);
		String url = getString(R.string.puertaAlcala);
		intent.putExtra("url", url);
		startActivity(intent);
	}
	
	public void showGranViaPage(View view){
		Intent intent = new Intent(this, InfoActivity.class);
		String url = getString(R.string.granVia);
		intent.putExtra("url", url);
		startActivity(intent);
	}
	
	public void showCibelesPage(View view){
		Intent intent = new Intent(this, InfoActivity.class);
		String url = getString(R.string.pzaCibeles);
		intent.putExtra("url", url);
		startActivity(intent);
	}
	
	public void showPlazaDeOrientePage(View view){
		Intent intent = new Intent(this, InfoActivity.class);
		String url = getString(R.string.pzaOriente);
		intent.putExtra("url", url);
		startActivity(intent);
	}

    public void goToSecondActivity(View view){
    	if(indexCiudad!=0){
    		Toast.makeText(getBaseContext(), ciudad[indexCiudad]+" not implemented yet", Toast.LENGTH_SHORT).show();
    		return;
    	}
		Intent intent = new Intent(this, SecondActivity.class);
		intent.putExtra("ciudad", ciudad[indexCiudad]);
		startActivity(intent);
	}
}
