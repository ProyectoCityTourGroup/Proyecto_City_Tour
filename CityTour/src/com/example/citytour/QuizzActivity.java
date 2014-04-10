package com.example.citytour;
/*
 * Code inspired by:http://www.developerfeed.com/simple-quiz-game-andriod
 */
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class QuizzActivity extends Activity {

	List<Question> quesList;
	int score=0;
	int qid=0;
	Question currentQ;
	TextView txtQuestion;
	RadioButton rda, rdb, rdc;
	Button butNext;
	DataBaseHelper db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quizz);
					
		db = new DataBaseHelper(this.getApplicationContext());
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		String hito = b.getString("hito");
		String aux = getTableName(hito);
		quesList = db.getAllQuestions(aux);
		currentQ = quesList.get(qid);
		txtQuestion=(TextView)findViewById(R.id.textView1);
		rda=(RadioButton)findViewById(R.id.radio0);
		rdb=(RadioButton)findViewById(R.id.radio1);
		rdc=(RadioButton)findViewById(R.id.radio2);
		butNext=(Button)findViewById(R.id.button1);
		setQuestionView();
		butNext.setOnClickListener(new View.OnClickListener() {
			// get shared preferences
			SharedPreferences prefs = getSharedPreferences("CityTourPrefs",Context.MODE_PRIVATE);
			// numero de quizzes realizados
			int numQuizzes = prefs.getInt("numQuizzes", 0);
			@Override
			public void onClick(View v) {
				RadioGroup grp=(RadioGroup)findViewById(R.id.radioGroup1);
				RadioButton answer=(RadioButton)findViewById(grp.getCheckedRadioButtonId());
				Log.d("yourans", currentQ.getANSWER()+" "+answer.getText());
				if(currentQ.getANSWER().equals(answer.getText()))
				{
					score++;
					Toast.makeText(getBaseContext(), getResources().getString(R.string.correctAnswer), Toast.LENGTH_SHORT).show();
					Log.d("score", "Your score: "+score);
				}else{
					Toast.makeText(getBaseContext(), getResources().getString(R.string.wrongAnswer)+" "+answer.getText(), Toast.LENGTH_SHORT).show();
				}
				if(qid<3){
					currentQ=quesList.get(qid);
					setQuestionView();
				}else{
					Intent intent = new Intent(QuizzActivity.this, ResultActivity.class);
					Bundle b = new Bundle();
					numQuizzes++;
					SharedPreferences.Editor editor = prefs.edit();
					editor.putInt("numQuizzes", numQuizzes);
					editor.commit();
					b.putInt("score", score); //Your score
					intent.putExtras(b); //Put your score to your next Intent
					startActivity(intent);
					finish();
				}
			}
		});
	}
	
	public String getTableName(String hito){
		if(hito.contains("Thyssen")){
			hito = "Museo Thyssen Bornemisza";
		}
		String[] aux = hito.split(" ");
		String name = "";
		for(int i=0; i<aux.length; i++){
			name += aux[i]+"_";
		}
		name = name.substring(0, name.lastIndexOf("_"));
		Log.d("TABLE NAME", name);
		return name;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quizz, menu);
		return true;
	}
	
	private void setQuestionView()
	{
		txtQuestion.setText(currentQ.getQUESTION());
		rda.setText(currentQ.getOPTA());
		rdb.setText(currentQ.getOPTB());
		rdc.setText(currentQ.getOPTC());
		qid++;
	}
	
	protected void onDestroy(){
		if(db != null){
			db.close();
		}
		super.onDestroy();
	}

}
