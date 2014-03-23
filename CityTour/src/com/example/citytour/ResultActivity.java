package com.example.citytour;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.RatingBar;
import android.widget.TextView;
/*
 * Code inspired by:http://www.developerfeed.com/simple-quiz-game-andriod
 */
public class ResultActivity extends Activity {

	@Override
		protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		//get rating bar object
		RatingBar bar=(RatingBar)findViewById(R.id.ratingBar1);
		//get text view
		TextView t=(TextView)findViewById(R.id.textResult);
		//get score
		Bundle b = getIntent().getExtras();
		int score= b.getInt("score");
		//display score
		bar.setRating(score);
		switch (score)
		{
		case 1:t.setText(R.string.unaEstrella);
		break;
		case 2:t.setText(R.string.dosEstrellas); 
		break;
		case 3:t.setText(R.string.tresEstrellas);
		break;
		}
		}
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
		}
}
