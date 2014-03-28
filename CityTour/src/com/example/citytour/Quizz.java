package com.example.citytour;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Quizz extends Activity {
	
	ArrayList<Question> quesList;
	int score;
	int qid=0;
	Question currentQ;
	TextView txtQuestion;
	RadioButton rda, rdb, rdc;
	Button butNext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quizz);
		score=0;
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		String hito = b.getString("hito");
		quesList = getAllQuestions(hito);
		txtQuestion=(TextView)findViewById(R.id.textView1);
		rda=(RadioButton)findViewById(R.id.radio0);
		rdb=(RadioButton)findViewById(R.id.radio1);
		rdc=(RadioButton)findViewById(R.id.radio2);
		butNext=(Button)findViewById(R.id.button1);
		currentQ = quesList.get(qid);
		setQuestionView();
		butNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RadioGroup grp=(RadioGroup)findViewById(R.id.radioGroup1);
				RadioButton answer=(RadioButton)findViewById(grp.getCheckedRadioButtonId());
				Log.d("yourans", currentQ.getANSWER()+" "+answer.getText());
				if(currentQ.getANSWER().equals(answer.getText()))
				{
					score++;
					Log.d("score", "Your score"+score);
				}
				if(qid<3){
					currentQ=quesList.get(qid);
					setQuestionView();
				}else{
					Intent intent = new Intent(Quizz.this, ResultActivity.class);
					Bundle b = new Bundle();
					b.putInt("score", score); //Your score
					intent.putExtras(b); //Put your score to your next Intent
					startActivity(intent);
					finish();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quizz, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fgmnt_quizz, container,
					false);
			return rootView;
		}
	}

	private void setQuestionView()
	{
		txtQuestion.setText(currentQ.getQUESTION());
		rda.setText(currentQ.getOPTA());
		rdb.setText(currentQ.getOPTB());
		rdc.setText(currentQ.getOPTC());
		qid++;
	}
	
	public ArrayList<Question> getAllQuestions(String hito){
		ArrayList<Question> questions = new ArrayList<Question>();
		if(hito.equals("Templo de Debod")){
			String[] pregunta1 = getResources().getStringArray(R.array.templo_pregunta1);
			Question question1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
			questions.add(question1);
			String[] pregunta2 = getResources().getStringArray(R.array.templo_pregunta2);
			Question question2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
			questions.add(question2);
			String[] pregunta3 = getResources().getStringArray(R.array.templo_pregunta3);
			Question question3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
			questions.add(question3);
		}
		if(hito.equals("Plaza de España")){
			String[] pregunta1 = getResources().getStringArray(R.array.pzaEsp_pregunta1);
			Question question1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
			questions.add(question1);
			String[] pregunta2 = getResources().getStringArray(R.array.pzaEsp_pregunta2);
			Question question2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
			questions.add(question2);
			String[] pregunta3 = getResources().getStringArray(R.array.pzaEsp_pregunta3);
			Question question3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
			questions.add(question3);
		}
		return questions;
	}
}
