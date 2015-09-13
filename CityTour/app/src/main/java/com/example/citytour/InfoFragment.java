package com.example.citytour;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class InfoFragment extends Fragment {

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_info, null);
		titleTextView = (TextView) view.findViewById(R.id.titleTextView);
		jsonWebView = (WebView)view.findViewById(R.id.JSONwebView);

		Bundle data = getArguments();
		if(data!=null){
			url = data.getString("url");
			if(url.contains("twin")){
				TITLE = "Twin Studio & Gallery";
				titleTextView.setText(TITLE);
				jsonWebView.loadUrl(url);
				jsonWebView.setWebViewClient(new WebViewClient());
			}else if(url.contains("tribunal")||url.contains("TRIBUNAL")){
				TITLE = "Tribunal";
				titleTextView.setText(TITLE);
				url = getString(R.string.descripcion_tribunal);
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

		return view;
	}

	// Async action
	private class GetData extends AsyncTask<String, String, JSONObject>{
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			// show progress dialog
			pDialog = new ProgressDialog(getActivity());
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