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
	private static final String DATABASE_NAME = "cityTourQuizz";
	// tasks table name
//	private static final String TABLE_QUEST = "quest";
	private static final String TABLE_PLAZA_DE_ESPANA = "Plaza_de_España";
	private static final String TABLE_TEMPLO_DE_DEBOD = "Templo_de_Debod";
//	private static final String TABLE_PALACIO_REAL = "Palacio Real";
	// tasks Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_QUES = "question";
	private static final String KEY_ANSWER = "answer"; //correct option
	private static final String KEY_OPTA= "opta"; //option a
	private static final String KEY_OPTB= "optb"; //option b
	private static final String KEY_OPTC= "optc"; //option c
	private SQLiteDatabase dbase;
	private final Context myContext;
	
	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.myContext = context;
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
	
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_PLAZA_DE_ESPANA + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql2 = "CREATE TABLE IF NOT EXISTS " + TABLE_TEMPLO_DE_DEBOD + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		db.execSQL(sql);
		db.execSQL(sql2);
		addQuestions(myContext);
	}
	
	private void addQuestions(Context context){
		
		// Templo de Debod
		String[] pregunta1 = context.getResources().getStringArray(R.array.templo_pregunta1);
		Question question_templo_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_templo_1,TABLE_TEMPLO_DE_DEBOD);
		String[] pregunta2 = context.getResources().getStringArray(R.array.templo_pregunta2);
		Question question_templo_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_templo_2,TABLE_TEMPLO_DE_DEBOD);
		String[] pregunta3 = context.getResources().getStringArray(R.array.templo_pregunta3);
		Question question_templo_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_templo_3,TABLE_TEMPLO_DE_DEBOD);
		// Plaza de España
		pregunta1 = context.getResources().getStringArray(R.array.pzaEsp_pregunta1);
		Question question_pzaEsp_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_pzaEsp_1, TABLE_PLAZA_DE_ESPANA);
		pregunta2 = context.getResources().getStringArray(R.array.pzaEsp_pregunta2);
		Question question_pzaEsp_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_pzaEsp_2, TABLE_PLAZA_DE_ESPANA);
		pregunta3 = context.getResources().getStringArray(R.array.pzaEsp_pregunta3);
		Question question_pzaEsp_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_pzaEsp_3, TABLE_PLAZA_DE_ESPANA);
	}
	
	// Adding new question
	public boolean addQuestion(Question quest, String tableName) {
		try{
			ContentValues values = new ContentValues();
			values.put(KEY_QUES, quest.getQUESTION());
			values.put(KEY_ANSWER, quest.getANSWER());
			values.put(KEY_OPTA, quest.getOPTA());
			values.put(KEY_OPTB, quest.getOPTB());
			values.put(KEY_OPTC, quest.getOPTC());
			// Inserting Row
			long result = dbase.insert(tableName, null, values);
			return (result > 0);
		}catch (SQLException ex) {
		       Log.w("SQLException", ex.fillInStackTrace());
		       return false;
		}
	}
		
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAZA_DE_ESPANA);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMPLO_DE_DEBOD);
		// Create tables again
		onCreate(db);
	}
	
	public List<Question> getAllQuestions(String tableName) {
		List<Question> quesList = new ArrayList<Question>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + tableName;
		dbase = this.getReadableDatabase();
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
		dbase.close();
		return quesList;
	}
	
	public int rowcount(){
		int row=0;
		String selectQuery = "SELECT  * FROM " + TABLE_PLAZA_DE_ESPANA;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
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
