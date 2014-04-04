package com.example.citytour;


import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class InfoActivity extends Activity {

	private ProgressDialog pDialog;
	
	// URL
	private String url;
	// JSON tags
	private static final String TAG_TITLE = "title";
	private static final String TAG_TEXT = "text";
	private static final String TAG_PARSE = "parse";
	private static String TITLE,DATA,TEXT;
	String[] paragraphs,titles;
	TextView titleTextView;
	WebView jsonWebView;
	JSONParser jsonparser = new JSONParser();
	JSONObject jobjParseString,jobjParse,jobjTitle,jobjText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		Intent intent = getIntent();
		
		titleTextView = (TextView)findViewById(R.id.titleTextView);
		jsonWebView = (WebView)findViewById(R.id.JSONwebView);
//		jsonWebView.getSettings().setJavaScriptEnabled(true);
		url = intent.getStringExtra("url");
		if(url.contains("twin")){
			TITLE = "Twin Studio & Gallery";
			titleTextView.setText(TITLE);
			jsonWebView.loadUrl(url);
			jsonWebView.setWebViewClient(new WebViewClient());
		}else if(url.contains("tribunal")||url.contains("TRIBUNAL")){
			TITLE = "Tribunal";
			titleTextView.setText(TITLE);
			url = "<html>En otro tiempo fue cuna de la movida madrileña de los años 80 y gira entorno a la Plaza del 2 de Mayo. Es la zona alternativa de la ciudad, con locales dedicados al rock y precios bastante asequibles en comparación con el resto de las zonas. Es una de las zonas clásicas para salir por la noche. La gente que va por este área se autodenomina malasañera. Las calles del barrio convergen en la acogedora Plaza del Dos de Mayo. El ambiente de los bares es animado y va desde los puristas del rock hasta las últimas modas. La gente que frecuenta esta zona por lo general está entre los 17 y los 25 años, pero hay para todos los gustos.En la misma zona se engloba el área del cuartel de Conde Duquesituado entre la calle San Bernardo, Alberto Aguilera, Gran Vía, Princesa y La calle Conde Duque. La zona es tranquila y especialmente agradable para tapear. En la zona más próxima a la Plaza de España se pueden encontrar pequeños restaurantes de comida exótica oriental. La tranquila y acogedora Plaza de las Comendadoras es el sitio perfecto para tomar  una cerveza o un café en las terrazas que tiene en verano.</html>";
			jsonWebView.loadDataWithBaseURL("",url,"text/html","UTF-8","");			
		}else if(url.contains("museo.abc")){
			TITLE = "Museo ABC";
			titleTextView.setText(TITLE);
			jsonWebView.loadUrl(url);
			jsonWebView.setWebViewClient(new WebViewClient());			
		}else{
			new GetData().execute();
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.info, menu);
		return super.onCreateOptionsMenu(menu);
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

	
	// Async action
	private class GetData extends AsyncTask<String, String, JSONObject>{
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			// show progress dialog
			pDialog = new ProgressDialog(InfoActivity.this);
			pDialog.setMessage(getResources().getString(R.string.wait));
			pDialog.setCancelable(false);
			pDialog.show();
		}
		@Override
		protected JSONObject doInBackground(String... arg0){
			jobjParseString = jsonparser.makeHttpRequest(url);
			return jobjParseString;
		}
		
		@Override
		protected void onPostExecute(JSONObject json){
			super.onPostExecute(json);
			try {
				jobjParse = jobjParseString.getJSONObject(TAG_PARSE);
				jobjText = jobjParse.getJSONObject(TAG_TEXT);
				TITLE = jobjParse.getString(TAG_TITLE);
				DATA = jobjText.toString();
				paragraphs = DATA.split("<p>");
				titles = DATA.split("<h2>");
				String correctText="";
				if(url.contains("Plaza_de_Esp")){
					// caso de Plaza de España
					TEXT = paragraphs[1]+paragraphs[2];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					correctText = n.replace("<\\" , "<");
				
				}else if(url.contains("Debod")){
					// caso del Templo de Debod
					TEXT = paragraphs[1]+paragraphs[2]+paragraphs[3];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					correctText = n.replace("<\\" , "<");
				}else if(url.contains("Palacio_Real")){
					// caso del palacio real
					TEXT = paragraphs[1]+paragraphs[2]+paragraphs[3]+paragraphs[4]+paragraphs[5]+paragraphs[6]+paragraphs[7];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					correctText = n.replace("<\\" , "<");
				}else if(url.contains("Plaza_Mayor")){
					// caso de la plaza mayor
					TEXT = paragraphs[1]+paragraphs[4]+paragraphs[5]+paragraphs[6]+paragraphs[7]+paragraphs[8];
					String aux = TEXT.replace("El nombre de la plaza"," ");
					correctText = aux.replace("<\\" , "<");
				}else if(url.contains("Cibeles")){
					// caso de la plaza de cibeles
					TEXT = paragraphs[1]+paragraphs[2]+paragraphs[3]+paragraphs[4];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					String aux2 = n.replace("<\\" , "<");
					String aux3 = aux2.replace("[1]", " ");
					String aux4 = aux3.replace("[2]", " ");
					correctText = aux4.replace("[3]", " ");
				}else if(url.contains("Oriente")){
					// caso de la plaza de oriente
					TEXT = paragraphs[2]+paragraphs[3]+paragraphs[4];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					correctText = n.replace("<\\" , "<");
				}else if(url.contains("Puerta_del_Sol")){
					// caso de la pueta del sol
					TEXT = paragraphs[1];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					correctText = n.replace("<\\" , "<");
				}else if(url.contains("Colegiata_de_San_Isidro")){
					// caso de la colegiata de san isidro
					TEXT = paragraphs[1]+paragraphs[2]+paragraphs[3];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					correctText = n.replace("<\\" , "<");
				}else if(url.contains("Plaza_de_la_Villa")){
					// caso de la plaza de la villa
					TEXT = paragraphs[2]+paragraphs[3]+paragraphs[4];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					correctText = n.replace("<\\" , "<");
				}else if(url.contains("Puerta_de_Al")){
					// caso de la puerta de alcala
					TEXT = paragraphs[1]+paragraphs[2]+paragraphs[3]+paragraphs[4]+paragraphs[5]+paragraphs[6];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					String aux2 = n.replace("<\\" , "<");
					String aux3 = aux2.replace("[1]", " ");
					String aux4 = aux3.replace("[2]", " ");
					String aux5 = aux4.replace("[3]", " ");
					String aux6 = aux5.replace("[4]", " ");
					String aux7 = aux6.replace("[5]", " ");
					correctText = aux7.replace("[6]", " ");
				}else if(url.contains("Gran")){
					// caso de la gran via
					TEXT = paragraphs[1];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					correctText = n.replace("<\\" , "<");
				}else if(url.contains("Museo_del_Pra")){
					// caso del museo del prado
					TEXT = paragraphs[1]+paragraphs[2]+paragraphs[3]+paragraphs[4]+paragraphs[5]+paragraphs[6]+paragraphs[7]+paragraphs[8];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					correctText = n.replace("<\\" , "<");
				}else if(url.contains("Reina_Sof")){
					// caso del museo Reina Sofía
					TEXT = paragraphs[1]+paragraphs[2]+paragraphs[3]+paragraphs[4]+paragraphs[5];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					correctText = n.replace("<\\" , "<");
				}else if(url.contains("Museo_Thy")){
					// caso del museo thyssen
					TEXT = paragraphs[1]+paragraphs[2];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					correctText = n.replace("<\\" , "<");
				}else if(url.contains("CaixaForum")){
					// caso del caixaforum
					TEXT = paragraphs[1]+paragraphs[2];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					correctText = n.replace("<\\" , "<");
				}else if(url.contains("Galdiano")){
					// caso del museo lazaro galdiano
					TEXT = paragraphs[1]+paragraphs[2]+paragraphs[3]+paragraphs[4]+paragraphs[5];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					correctText = n.replace("<\\" , "<");
				}else if(url.contains("Museo_Sorol")){
					// caso del museo sorolla
					TEXT = paragraphs[1]+paragraphs[2]+paragraphs[3];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					correctText = n.replace("<\\" , "<");
				}else if(url.contains("Museo_Arqueol")){
					// caso del museo arqueologico
					TEXT = paragraphs[1]+paragraphs[2]+paragraphs[5]+paragraphs[6]+paragraphs[7]+paragraphs[8];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					correctText = n.replace("<\\" , "<");
				}else if(url.contains("Museo_Nav")){
					// caso del museo naval
					TEXT = paragraphs[1]+paragraphs[2];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					correctText = n.replace("<\\" , "<");
				}else if(url.contains("Ventas")){
					// caso de la plaza de las ventas
					TEXT = paragraphs[1];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					String aux2 = n.replace("<\\" , "<");
					String aux3 = aux2.replace("[1]", " ");
					String aux4 = aux3.replace("[2]", " ");
					String aux5 = aux4.replace("[3]", " ");
					String aux6 = aux5.replace("[4]", " ");
					correctText = aux6.replace("\\", " ");
				}else if(url.contains("Mayo")){
					// caso de la plaza del dos de mayo
					TEXT = paragraphs[2] + paragraphs[3];
					String aux = Html.fromHtml(TEXT).toString();
					int ref = aux.indexOf("Referencias");
					aux = aux.substring(0, ref-1);
					String n = aux.replaceAll("\\\\n", "");
					String aux2 = n.replace("<\\" , "<");
					String aux3 = aux2.replace("[1]", " ");
					String aux4 = aux3.replace("[2]", " ");
					String aux5 = aux4.replace("[3]", " ");
					String aux6 = aux5.replace("[4]", " ");
					correctText = aux6.replace("\\", " ");
				}else if(url.contains("San_Miguel")){
					// caso de la plaza de las ventas
					TEXT = paragraphs[2] + paragraphs[4] + paragraphs[5];
					String aux = Html.fromHtml(TEXT).toString();
					int ref = aux.indexOf("Orígenes");
					aux = aux.substring(0, ref-1);
					String n = aux.replaceAll("\\\\n", "");
					String aux2 = n.replace("<\\" , "<");
					String aux3 = aux2.replace("[1]", " ");
					String aux4 = aux3.replace("[2]", " ");
					String aux5 = aux4.replace("[3]", " ");
					String aux6 = aux5.replace("[4]", " ");
					correctText = aux6.replace("\\", " ");
				}else if(url.contains("Teatro_Real")){
					// caso del Teatro Real
					TEXT = paragraphs[2] + paragraphs[3];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					String aux2 = n.replace("<\\" , "<");
					correctText = aux2.replace("\\", " ");
				}else if(url.contains("Almudena")){
					// caso de la catedral de la almudena
					TEXT = paragraphs[2] + paragraphs[3] + paragraphs[4] + paragraphs[5];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					String aux2 = n.replace("<\\" , "<");
					correctText = aux2.replace("\\", " ");
				}else if(url.contains("Toledo")){
					// caso de la puerta de toledo
					TEXT = paragraphs[1] + paragraphs[2];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					String aux2 = n.replace("<\\" , "<");
					String aux3 = aux2.replace("[1]", " ");
					correctText = aux3.replace("[2]", " ");
				}else if(url.contains("Abrantes")){
					// caso del palacio de abrantes
					TEXT = paragraphs[1] + paragraphs[2] + paragraphs[3] + paragraphs[4] + paragraphs[5] + paragraphs[6];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					String aux2 = n.replace("<\\" , "<");
					String aux3 = aux2.replace("[1]", " ");
					String aux4 = aux3.replace("[2]", " ");
					String aux5 = aux4.replace("\\", "");
					correctText = aux5.replace("Historia[editar]", "");
					correctText = correctText.substring(0, correctText.indexOf("Enlaces externos")-1);
				}else if(url.contains("Nicolás")){
					// caso de la iglesia de san nicolas
					TEXT = paragraphs[1] + paragraphs[4] + paragraphs[5] + paragraphs[6];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					String aux2 = n.replace("<\\" , "<");
					String aux3 = aux2.replace("[1]", " ");
					String aux4 = aux3.replace("[2]", " ");
					correctText = aux4.replace("\\", "");
					correctText = correctText.substring(0, correctText.indexOf("Descripción[editar]")-1);
				}else if(url.contains("Callao")){
					// caso de la plaza de callao
					TEXT = paragraphs[2] + paragraphs[5] + paragraphs[6];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					String aux2 = n.replace("<\\" , "<");
					String aux3 = aux2.replace("[1]", " ");
					String aux4 = aux3.replace("[2]", " ");
					String aux5 = aux4.replace("\\", "");
					correctText = aux5.replace("Historia[editar]", "");
				}else if(url.contains("Neptuno")){
					// caso de la plaza de neptuno
					TEXT = paragraphs[1] + paragraphs[4] + paragraphs[5] + paragraphs[6];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					String aux2 = n.replace("<\\" , "<");
					String aux3 = aux2.replace("[1]", " ");
					String aux4 = aux3.replace("[2]", " ");
					correctText = aux4.replace("\\", "");
					correctText = correctText.substring(0, correctText.indexOf("Características[editar]")-1);
				}else if(url.contains("Diputados")){
					// caso del congreso de los diputados
					TEXT = paragraphs[1] + paragraphs[3] + paragraphs[4] + paragraphs[5] + paragraphs[6] + paragraphs[7];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					String aux2 = n.replace("<\\" , "<");
					String aux3 = aux2.replace("[1]", " ");
					String aux4 = aux3.replace("[2]", " ");
					correctText = aux4.replace("\\", "");
					correctText = correctText.substring(0, correctText.indexOf("Antecedentes")-1)+correctText.substring(correctText.lastIndexOf("Parlamentarismo español")+23, correctText.indexOf("Posición constitucional")-1);
				}else if(url.contains("Jardines_del_Retiro")){
					// caso del parque del retiro
					TEXT = paragraphs[1] + paragraphs[3] + paragraphs[4] + paragraphs[5] + paragraphs[6] + paragraphs[7] + paragraphs[8] + paragraphs[9] + paragraphs[10] + paragraphs[11] + paragraphs[12] + paragraphs[13] + paragraphs[14];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					String aux2 = n.replace("<\\" , "<");
					String aux3 = aux2.replace("[1]", " ");
					String aux4 = aux3.replace("[2]", " ");
					String aux5 = aux4.replace("\\", "");
					correctText = aux5.replace("Historia[editar]", "");
					correctText = correctText.substring(0, correctText.indexOf("Véase")-1) + correctText.substring(correctText.indexOf(".Los jardines")+1, correctText.indexOf("Puntos de interés")-1);
				}else if(url.contains("Palacio_de_Cristal")){
					// caso del palacio de cristal
					TEXT = paragraphs[1] + paragraphs[3] + paragraphs[4] + paragraphs[5] + paragraphs[6] + paragraphs[7] + paragraphs[8] + paragraphs[9] + paragraphs[10];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					String aux2 = n.replace("<\\" , "<");
					String aux3 = aux2.replace("[1]", " ");
					String aux4 = aux3.replace("[2]", " ");
					String aux5 = aux4.replace("\\", "");
					correctText = aux5.replace("Historia[editar]", "");
					correctText = correctText.substring(0, correctText.indexOf("Interior")-5)+": \""+correctText.substring(correctText.indexOf("..."), correctText.indexOf("A sus pies")-1)+"\" "+correctText.substring(correctText.indexOf("A sus pies"), correctText.indexOf("Galería")-1);
				}else if(url.contains("Viaducto")){
					// caso del viaducto de segovia
					TEXT = paragraphs[1] + paragraphs[12];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					String aux2 = n.replace("<\\" , "<");
					String aux3 = aux2.replace("[1]", " ");
					String aux4 = aux3.replace("[2]", " ");
					String aux5 = aux4.replace("[3]", " ");
					String aux6 = aux5.replace("[4]", " ");
					String aux7 = aux6.replace("[5]", " ");
					String aux8 = aux7.replace("[6]", " ");
					String aux9 = aux8.replace("Historia[editar]", "");
					correctText = aux9.replace("\\", "");
				}else if(url.contains("Bailén")){
					// caso de la calle bailen
					TEXT = paragraphs[1] + paragraphs[5] + paragraphs[6] + paragraphs[7];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					String aux2 = n.replace("<\\" , "<");
					String aux3 = aux2.replace("[1]", " ");
					String aux4 = aux3.replace("[2]", " ");
					correctText = aux4.replace("\\", "");
					correctText = correctText.substring(0, correctText.indexOf("Fuentes[editar]")-1);
				}else if(url.contains("Atocha")){
					// caso de la estación de atocha
					TEXT = paragraphs[1];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					String aux2 = n.replace("<\\" , "<");
					String aux3 = aux2.replace("[1]", " ");
					String aux4 = aux3.replace("[2]", " ");
					correctText = aux4.replace("\\", "");
				}else if(url.contains("Botánico")){
					// caso del real jardin botanico
					TEXT = paragraphs[1];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					String aux2 = n.replace("<\\" , "<");
					String aux3 = aux2.replace("[1]", " ");
					String aux4 = aux3.replace("[2]", " ");
					correctText = aux4.replace("\\", "");
					correctText = correctText.substring(0, correctText.indexOf("Puerta Real")-1);
				}else if(url.contains("Ayuntamiento")){
					// caso del real jardin botanico
					TEXT = paragraphs[1] + paragraphs[2];
					String aux = Html.fromHtml(TEXT).toString();
					String n = aux.replaceAll("\\\\n", "");
					String aux2 = n.replace("<\\" , "<");
					String aux3 = aux2.replace("[1]", " ");
					String aux4 = aux3.replace("[2]", " ");
					correctText = aux4.replace("\\", "");
				}
				titleTextView.setText(TITLE);
				jsonWebView.loadDataWithBaseURL("", correctText, "text/html; charset=UTF-8", "UTF-8", "");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			// dismiss progress dialog
			pDialog.dismiss();		
		}
	}
}
