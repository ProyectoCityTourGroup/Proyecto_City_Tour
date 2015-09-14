package com.example.citytour.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.citytour.R;

public class MainActivity extends Activity {

    String[] city,route,duration;
    String pName = "";

    int cityIndex,routeIndex,timeIndex;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinnerCiudades = (Spinner) findViewById(R.id.spinnerCiudades);
        Spinner spinnerRecorridos = (Spinner) findViewById(R.id.spinnerRecorridos);
        Spinner spinnerDuraciones = (Spinner) findViewById(R.id.spinnerDuraciones);
        setupSpinners(spinnerCiudades, spinnerRecorridos, spinnerDuraciones);

    }

    private void setupSpinners(Spinner spinnerCiudades, Spinner spinnerRecorridos, Spinner spinnerDuraciones) {
        ArrayAdapter<CharSequence> adapterCiudades = ArrayAdapter.createFromResource(this,
                R.array.array_ciudades, android.R.layout.simple_spinner_item);
        adapterCiudades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCiudades.setAdapter(adapterCiudades);
        spinnerCiudades.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                cityIndex = parentView.getSelectedItemPosition();
                city = getResources().getStringArray(R.array.array_ciudades);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        ArrayAdapter<CharSequence> adapterRecorridos = ArrayAdapter.createFromResource(this,
                R.array.array_recorridos, android.R.layout.simple_spinner_item);
        adapterRecorridos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRecorridos.setAdapter(adapterRecorridos);
        spinnerRecorridos.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                routeIndex = parentView.getSelectedItemPosition();
                route = getResources().getStringArray(R.array.array_recorridos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // do nothing
            }
        });
        ArrayAdapter<CharSequence> adapterDuraciones = ArrayAdapter.createFromResource(this,
                R.array.array_duraciones, android.R.layout.simple_spinner_item);
        adapterDuraciones.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDuraciones.setAdapter(adapterDuraciones);
        spinnerDuraciones.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                timeIndex = parentView.getSelectedItemPosition();
                duration = getResources().getStringArray(R.array.array_duraciones);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void goToSecondActivity(View view){
        if(cityIndex!=0){
            Toast.makeText(getBaseContext(), city[cityIndex]+" "+getResources().getString(R.string.notYet), Toast.LENGTH_SHORT).show();
            return;
        }
        if(cityIndex==0&&routeIndex > 1){
            Toast.makeText(getBaseContext(), "Recorrido "+ getResources().getString(R.string.notYet), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, SecondActivity.class);
        // save data into Shared Preferences
        SharedPreferences prefs = getSharedPreferences("com.example.citytour", Context.MODE_PRIVATE);
        Editor editor = prefs.edit();
        editor.putInt("cityIndex", cityIndex);
        editor.putInt("routeIndex", routeIndex);
        editor.putInt("timeIndex", timeIndex);
        editor.apply();
        startActivity(intent);
    }

}
