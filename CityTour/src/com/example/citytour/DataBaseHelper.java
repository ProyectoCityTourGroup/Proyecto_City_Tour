package com.example.citytour;
/*
 * Code inspired by:http://www.developerfeed.com/simple-quiz-game-andriod
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {
	
	private static DataBaseHelper sInstance;
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "triviaQuiz";
	// tasks table name
	private static final String TABLE_QUEST = "quest";
	// tasks Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_QUES = "question";
	private static final String KEY_ANSWER = "answer"; //correct option
	private static final String KEY_OPTA= "opta"; //option a
	private static final String KEY_OPTB= "optb"; //option b
	private static final String KEY_OPTC= "optc"; //option c
	private SQLiteDatabase dbase;
	
	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public static DataBaseHelper getInstance(Context context) {

	    // Use the application context, which will ensure that you 
	    // don't accidentally leak an Activity's context.
	    // See this article for more information: http://bit.ly/6LRzfx
	    if (sInstance == null) {
	      sInstance = new DataBaseHelper(context.getApplicationContext());
	    }
	    return sInstance;
	  }
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUEST + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		db.execSQL(sql);
//		addQuestions();
		Question q1=new Question("Which company is the largest manufacturer" +
				" of network equipment?","HP", "IBM", "CISCO", "C");
		if(this.addQuestion(q1)){
			Log.d("ADDED","question1");
		}
		Question q2=new Question("Which of the following is NOT " +
				"an operating system?", "SuSe", "BIOS", "DOS", "B");
		if(this.addQuestion(q2)){
			Log.d("ADDED","question2");
		}
		Question q3=new Question("Which of the following is the fastest" +
				" writable memory?","RAM", "FLASH","Register","C");
		if(this.addQuestion(q3)){
			Log.d("ADDED","question3");
		}
		Question q4=new Question("Which of the following device" +
				" regulates internet traffic?",    "Router", "Bridge", "Hub","A");
		if(this.addQuestion(q4)){
			Log.d("ADDED","question4");
		}
		Question q5=new Question("Which of the following is NOT an" +
				" interpreted language?","Ruby","Python","BASIC","C");
		if(this.addQuestion(q5)){
			Log.d("ADDED","question5");
		}
		Log.d("PSST", "por lo menos llegamos hasta aqui");
	}
	
//	private void addQuestions(){
//		Question q1=new Question("Which company is the largest manufacturer" +
//				" of network equipment?","HP", "IBM", "CISCO", "C");
//		if(this.addQuestion(q1)){
//			Log.d("ADDED","question1");
//		}
//		Question q2=new Question("Which of the following is NOT " +
//				"an operating system?", "SuSe", "BIOS", "DOS", "B");
//		if(this.addQuestion(q2)){
//			Log.d("ADDED","question2");
//		}
//		Question q3=new Question("Which of the following is the fastest" +
//				" writable memory?","RAM", "FLASH","Register","C");
//		if(this.addQuestion(q3)){
//			Log.d("ADDED","question3");
//		}
//		Question q4=new Question("Which of the following device" +
//				" regulates internet traffic?",    "Router", "Bridge", "Hub","A");
//		if(this.addQuestion(q4)){
//			Log.d("ADDED","question4");
//		}
//		Question q5=new Question("Which of the following is NOT an" +
//				" interpreted language?","Ruby","Python","BASIC","C");
//		if(this.addQuestion(q5)){
//			Log.d("ADDED","question5");
//		}
//	}
	
	// Adding new question
	public boolean addQuestion(Question quest) {
		try{
			ContentValues values = new ContentValues();
			values.put(KEY_QUES, quest.getQUESTION());
			values.put(KEY_ANSWER, quest.getANSWER());
			values.put(KEY_OPTA, quest.getOPTA());
			values.put(KEY_OPTB, quest.getOPTB());
			values.put(KEY_OPTC, quest.getOPTC());
			// Inserting Row
			long result = dbase.insert(TABLE_QUEST, null, values);
			return (result > 0);
		}catch (SQLException ex) {
		       Log.w("SQLException", ex.fillInStackTrace());
		       return false;
		}
	}
		
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);
		// Create tables again
		onCreate(db);
	}
	
	public List<Question> getAllQuestions() {
		List<Question> quesList = new ArrayList<Question>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
		if(dbase != null){
			Cursor cursor = dbase.rawQuery(selectQuery, null);
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					Question quest = new Question();
					quest.setID(cursor.getInt(0));
					quest.setQUESTION(cursor.getString(1));
					quest.setANSWER(cursor.getString(2));
					quest.setOPTA(cursor.getString(3));
					quest.setOPTB(cursor.getString(4));
					quest.setOPTC(cursor.getString(5));
					quesList.add(quest);
				} while (cursor.moveToNext());
			}
		}else Log.d("ERROR", "DataBase is null for some reason");
		return quesList;
	}
	
	public int rowcount(){
		int row=0;
		String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
		Cursor cursor = dbase.rawQuery(selectQuery, null);
		row=cursor.getCount();
		return row;
	}

	public synchronized void open(){
		dbase = this.getWritableDatabase();
	}
	
	public synchronized void close() {
	    if(dbase != null){
	    	dbase.close();
	    }
	}
}
