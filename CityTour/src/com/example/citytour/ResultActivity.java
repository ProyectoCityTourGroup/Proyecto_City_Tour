package com.example.citytour;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

/*
 * Code inspired by:http://www.developerfeed.com/simple-quiz-game-andriod
 */
public class ResultActivity extends Activity {

	static final int SHARE = 1;
	int score = 0;
	String checkpoint = "";
	
	Button backButton;
	Button shareButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		backButton = (Button)findViewById(R.id.botonVolver);
		shareButton = (Button)findViewById(R.id.shareButton);
		//get rating bar object
		RatingBar bar=(RatingBar)findViewById(R.id.ratingBar1);
		//get text view
		TextView t=(TextView)findViewById(R.id.textResult);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
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
			Log.d("SCORE", String.valueOf(score));
			Log.d("totalSCORE", String.valueOf(totalScore));
			Log.d("numQuizzes", String.valueOf(numQuizzes));
			averageScore = totalScore/numQuizzes;
			SharedPreferences.Editor editor = prefs.edit();
			editor.putFloat("averageScore", averageScore);
			editor.commit();
			//display score
			bar_average.setRating(averageScore);
		}else{
			Float totalScore = (float) score;
			averageScore = totalScore/numQuizzes;
			Log.d("SCORE", String.valueOf(score));
			Log.d("numQuizzes", String.valueOf(numQuizzes));
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
}
