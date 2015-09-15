package com.example.citytour.fragments;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.citytour.R;
import com.example.citytour.activities.ResultActivity;
import com.example.citytour.core.CityTour;
import com.example.citytour.core.CityTourDBHelper;
import com.example.citytour.models.Question;

public class QuizzFragment extends Fragment {

    private static final String TAG = QuizzFragment.class.getCanonicalName();
	private List<Question> quesList;
	private int score=0;
	private int qid=0;
	private Question currentQ;
	private TextView txtQuestion;
	private RadioButton rda;
	private RadioButton rdb;
	private RadioButton rdc;
	private String checkpoint = "";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
    View view = inflater.inflate(R.layout.fragment_quizz, null);
	Bundle data = getArguments();
	TextView title = (TextView)view.findViewById(R.id.checkPointName);
	if(data!=null){
        checkpoint = data.getString("checkpoint");
        title.setText(checkpoint);
		CityTourDBHelper cityTourDBHelper = new CityTourDBHelper(getActivity().getApplicationContext());
        String aux = getTableName(checkpoint);
        quesList = getAllQuestions(aux);
        currentQ = quesList.get(qid);
        txtQuestion=(TextView)view.findViewById(R.id.textView1);
        rda=(RadioButton)view.findViewById(R.id.radio0);
        rdb=(RadioButton)view.findViewById(R.id.radio1);
        rdc=(RadioButton)view.findViewById(R.id.radio2);
		Button butNext = (Button) view.findViewById(R.id.button1);
        setQuestionView();
        
        butNext.setOnClickListener(new View.OnClickListener() {
			final SharedPreferences prefs = getActivity().getSharedPreferences("CityTourPrefs", Context.MODE_PRIVATE);
			int numQuizzes = prefs.getInt("numQuizzes", 0);

			@Override
			public void onClick(View v) {
				RadioGroup grp = (RadioGroup) getActivity().findViewById(R.id.radioGroup1);
				RadioButton answer = (RadioButton) getActivity().findViewById(grp.getCheckedRadioButtonId());
				Log.d("yourans", currentQ.getANSWER() + " " + answer.getText());
				if (currentQ.getANSWER().equals(answer.getText())) {
					score++;
					Toast.makeText(getActivity().getBaseContext(), getResources().getString(R.string.correctAnswer), Toast.LENGTH_SHORT).show();
					Log.d(TAG, "Your score: " + score);
				} else {
					Toast.makeText(getActivity().getBaseContext(), getResources().getString(R.string.wrongAnswer), Toast.LENGTH_SHORT).show();
				}
				if (qid < 3) {
					currentQ = quesList.get(qid);
					setQuestionView();
				} else {
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
 
	private String getTableName(String hito){
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
	
	private void setQuestionView(){
		txtQuestion.setText(currentQ.getQUESTION());
		rda.setText(currentQ.getOPTA());
		rdb.setText(currentQ.getOPTB());
		rdc.setText(currentQ.getOPTC());
		qid++;
	}

	private List<Question> getAllQuestions(String tableName) {
		List<Question> quesList = new ArrayList<>();
		String selectQuery = "SELECT  * FROM " + tableName;
        SQLiteDatabase databaseInstance = CityTour.getDatabaseInstance();
		if(databaseInstance != null){
			Cursor cursor = databaseInstance.rawQuery(selectQuery, null);
			if (cursor.moveToFirst()) {
				do {
					Question question = new Question();
					question.setID(cursor.getInt(0));
					question.setQUESTION(cursor.getString(1));
					question.setANSWER(cursor.getString(2));
					question.setOPTA(cursor.getString(3));
					question.setOPTB(cursor.getString(4));
					question.setOPTC(cursor.getString(5));
					quesList.add(question);
				} while (cursor.moveToNext());
			}
		}else Log.d(TAG, "ERROR. DataBase is null for some reason");
		return quesList;
	}
}