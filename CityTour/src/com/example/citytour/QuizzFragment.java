package com.example.citytour;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class QuizzFragment extends Fragment {
 
	List<Question> quesList;
	int score=0;
	int qid=0;
	Question currentQ;
	TextView txtQuestion;
	RadioButton rda, rdb, rdc;
	Button butNext;
	DataBaseHelper db;
	String checkpoint = "";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
    View view = inflater.inflate(R.layout.fragment_quizz, null);
	Bundle data = getArguments();
	
	if(data!=null){
        checkpoint = data.getString("checkpoint"); 
        db = new DataBaseHelper(getActivity().getApplicationContext());
        String aux = getTableName(checkpoint);
        quesList = db.getAllQuestions(aux);
        currentQ = quesList.get(qid);
        txtQuestion=(TextView)view.findViewById(R.id.textView1);
        rda=(RadioButton)view.findViewById(R.id.radio0);
        rdb=(RadioButton)view.findViewById(R.id.radio1);
        rdc=(RadioButton)view.findViewById(R.id.radio2);
        butNext=(Button)view.findViewById(R.id.button1);
        setQuestionView();
        
        butNext.setOnClickListener(new View.OnClickListener() {
        	// get shared preferences
        	SharedPreferences prefs = getActivity().getSharedPreferences("CityTourPrefs",Context.MODE_PRIVATE);
        	// numero de quizzes realizados
        	int numQuizzes = prefs.getInt("numQuizzes", 0);
        	
        	@Override
        	public void onClick(View v) {
        		RadioGroup grp = (RadioGroup)getActivity().findViewById(R.id.radioGroup1);
        		RadioButton answer = (RadioButton)getActivity().findViewById(grp.getCheckedRadioButtonId());
        		Log.d("yourans", currentQ.getANSWER()+" "+answer.getText());
        		if(currentQ.getANSWER().equals(answer.getText()))
        		{
        			score++;
        			Toast.makeText(getActivity().getBaseContext(), getResources().getString(R.string.correctAnswer), Toast.LENGTH_SHORT).show();
        			Log.d("score", "Your score: "+score);
        		}else{
        			Toast.makeText(getActivity().getBaseContext(), getResources().getString(R.string.wrongAnswer), Toast.LENGTH_SHORT).show();
        		}
        		if(qid<3){
        			currentQ=quesList.get(qid);
        			setQuestionView();
        		}else{
        			Intent intent = new Intent(getActivity(), ResultActivity.class);
        			Bundle b = new Bundle();
        			numQuizzes++;
        			SharedPreferences.Editor editor = prefs.edit();
        			editor.putInt("numQuizzes", numQuizzes);
        			editor.commit();
        			b.putInt("score", score); //Your score
        			b.putString("checkpoint", checkpoint);
        			intent.putExtras(b); //Put your score to your next Intent
        			getActivity().startActivity(intent);
        			getActivity().finish();
        		}
        	}
        });
	}
    return view;
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
	
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getActivity().getMenuInflater().inflate(R.menu.quizz, menu);
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
	
	public void onDestroy(){
		if(db != null){
			db.close();
		}
		super.onDestroy();
	}
}