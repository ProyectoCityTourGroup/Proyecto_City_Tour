package com.example.citytour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SelectedZonesActivity extends Activity {
	ListView listView;
	TextView textView;
	ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selected_zones);
		listView = (ListView)findViewById(R.id.listaZonasSeleccionadas);
		textView = (TextView)findViewById(R.id.zonasSeleccionadas);
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		String[] zonas = b.getStringArray("zonas");
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,zonas);
		listView.setAdapter(adapter);
	}

	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.selected_zones, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case android.R.id.home:
//			// This ID represents the Home or Up button. In the case of this
//			// activity, the Up button is shown. Use NavUtils to allow users
//			// to navigate up one level in the application structure. For
//			// more details, see the Navigation pattern on Android Design:
//			//
//			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
//			//
//			NavUtils.navigateUpFromSameTask(this);
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}

}
