package com.example.citytour.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.citytour.R;

/*
 * Code inspired by:http://www.developerfeed.com/simple-quiz-game-andriod
 */
public class ResultActivity extends Activity {

	Context context;
	static final int SHARE = 1;
	int score = 0;
	String checkpoint = "";
	
	Button backButton;
	Button shareButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		context = this;
		backButton = (Button)findViewById(R.id.botonVolver);
		shareButton = (Button)findViewById(R.id.shareButton);
		//get rating bar object
		RatingBar bar=(RatingBar)findViewById(R.id.ratingBar1);
		//get text view
		TextView t=(TextView)findViewById(R.id.textResult);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// get shared preferences
				SharedPreferences prefs = getSharedPreferences("com.example.citytour",Context.MODE_PRIVATE);
				int beenThere = prefs.getInt("beenThere", 0);
				int numCheckpoints = prefs.getInt("numCheckpoints", 0);

				if(beenThere==numCheckpoints){
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
					alertDialogBuilder.setMessage(getResources().getString(R.string.routeFinished))
					.setCancelable(false)
					.setPositiveButton(getResources().getString(R.string.newRoute),
							new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface dialog, int id){
							Intent intent = new Intent(getBaseContext(), MainActivity.class);
							startActivity(intent);
							dialog.cancel();
						}
					});
					alertDialogBuilder.setNegativeButton(getResources().getString(R.string.quit),
							new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface dialog, int id){
							quit();
						}
					});
					AlertDialog alert = alertDialogBuilder.create();
					alert.show();
				}else{
					finish();
				}
			}
		});
		//get score
		Bundle b = getIntent().getExtras();
		checkpoint = b.getString("checkpoint");
		score = b.getInt("score");
		//display score
		bar.setRating(score);
		switch (score){
			case 0:
			case 1:t.setText(R.string.unaEstrella);
			break;
			case 2:t.setText(R.string.dosEstrellas); 
			break;
			case 3:t.setText(R.string.tresEstrellas);
			break;
		}
		//get rating bar object
		RatingBar bar_average=(RatingBar)findViewById(R.id.ratingBar2);
		//get text view
		TextView t2=(TextView)findViewById(R.id.textAverage);
		t2.setText(getResources().getString(R.string.averageScore));
		// get shared preferences
		SharedPreferences prefs = getSharedPreferences("CityTourPrefs",Context.MODE_PRIVATE);
		// numero de quizzes realizados
		int numQuizzes = prefs.getInt("numQuizzes", 0);
		Float averageScore = prefs.getFloat("averageScore", 0);
		if(numQuizzes!=1){
			Float totalScore = averageScore*(numQuizzes-1) + score;
			averageScore = totalScore/numQuizzes;
			SharedPreferences.Editor editor = prefs.edit();
			editor.putFloat("averageScore", averageScore);
			editor.commit();
			//display score
			bar_average.setRating(averageScore);
		}else{
			Float totalScore = (float) score;
			averageScore = totalScore/numQuizzes;
			SharedPreferences.Editor editor = prefs.edit();
			editor.putFloat("averageScore", averageScore);
			editor.commit();
			//display score
			bar_average.setRating(averageScore);
		}
		addListenerOnShareButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}

	public void addListenerOnShareButton() {
		shareButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// The data to be shared.
				String data = getResources().getString(R.string.imAt) + " " + checkpoint + ". " + getResources().getString(R.string.obtained) + " " + String.valueOf(score) + "/3";

				// Create an ACTION_SEND intent and place the data in it.
				Intent shareIntent = new Intent(Intent.ACTION_SEND);
				shareIntent.setType("text/plain");
				shareIntent.putExtra(Intent.EXTRA_TEXT, data + ".");

				// Launch the app chooser to share the data.
				startActivityForResult(Intent.createChooser(shareIntent, "Share spinner contents:"), SHARE);
				finish();
			}
		});
	}
	
	public void quit(){
		// shows Home screen
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}
