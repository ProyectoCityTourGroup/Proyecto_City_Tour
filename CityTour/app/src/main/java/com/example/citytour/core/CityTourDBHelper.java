package com.example.citytour.core;
/*
 * Code inspired by:http://www.developerfeed.com/simple-quiz-game-andriod
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.citytour.R;
import com.example.citytour.models.Question;

public class CityTourDBHelper extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "CT";

	private static final String TABLE_PLAZA_DE_ESPANA = "Plaza_de_España";
	private static final String TABLE_TEMPLO_DE_DEBOD = "Templo_de_Debod";
	private static final String TABLE_PALACIO_REAL = "Palacio_Real";
	private static final String TABLE_PLAZA_MAYOR = "Plaza_Mayor";
	private static final String TABLE_PLAZA_DE_CIBELES = "Plaza_de_Cibeles";
	private static final String TABLE_PLAZA_DE_ORIENTE = "Plaza_de_Oriente";
	private static final String TABLE_PUERTA_DEL_SOL = "Puerta_del_Sol";
	private static final String TABLE_COLEGIATA_DE_SAN_ISIDRO = "Colegiata_de_San_Isidro";
	private static final String TABLE_PLAZA_DE_LA_VILLA = "Plaza_de_la_Villa";
	private static final String TABLE_PUERTA_DE_ALCALA = "Puerta_de_Alcalá";
	private static final String TABLE_GRAN_VIA = "Gran_Vía";
	private static final String TABLE_GALERIA_TWIN = "Galería_Twin_Studio_and_Gallery";
	private static final String TABLE_MUSEO_DEL_PRADO = "Museo_del_Prado";
	private static final String TABLE_MUSEO_REINA_SOFIA = "Museo_Reina_Sofía";
	private static final String TABLE_MUSEO_THYSSEN = "Museo_Thyssen_Bornemisza";
	private static final String TABLE_CAIXAFORUM = "Caixa_Forum";
	private static final String TABLE_MUSEO_LAZARO_GALDIANO = "Museo_Lázaro_Galdiano";
	private static final String TABLE_MUSEO_SOROLLA = "Museo_Sorolla";
	private static final String TABLE_MUSEO_ARQUEOLOGICO = "Museo_Arqueológico_Nacional";
	private static final String TABLE_MUSEO_NAVAL = "Museo_Naval";
	private static final String TABLE_LAS_VENTAS = "Plaza_de_toros_de_Las_Ventas";
	private static final String TABLE_TRIBUNAL = "Tribunal";
	private static final String TABLE_PLAZA_DOS_DE_MAYO = "Plaza_del_Dos_de_Mayo";
	private static final String TABLE_MERCADO_DE_SAN_MIGUEL = "Mercado_de_San_Miguel";
	private static final String TABLE_TEATRO_REAL = "Teatro_Real";
	private static final String TABLE_CATEDRAL_DE_LA_ALMUDENA = "Catedral_de_La_Almudena";
	private static final String TABLE_PUERTA_DE_TOLEDO = "Puerta_de_Toledo";
	private static final String TABLE_PALACIO_DE_ABRANTES = "Palacio_de_Abrantes";
	private static final String TABLE_IGLESIA_DE_SAN_NICOLAS = "Iglesia_de_San_Nicolás";
	private static final String TABLE_MUSEO_ABC = "Museo_ABC";
	private static final String TABLE_PLAZA_DE_CALLAO = "Plaza_de_Callao";
	private static final String TABLE_PLAZA_DE_NEPTUNO = "Plaza_de_Neptuno";
	private static final String TABLE_CONGRESO = "Congreso_de_los_Diputados";
	private static final String TABLE_PARQUE_DEL_RETIRO = "Parque_del_Retiro";
	private static final String TABLE_PALACIO_DE_CRISTAL = "Palacio_de_Cristal";
	private static final String TABLE_VIADUCTO_DE_SEGOVIA = "Viaducto_de_Segovia";
	private static final String TABLE_CALLE_BAILEN = "Calle_Bailén";
	private static final String TABLE_ATOCHA = "Atocha";
	private static final String TABLE_JARDIN_BOTANICO = "Jardín_Botánico";
	private static final String TABLE_AYUNTAMIENTO = "Ayuntamiento_de_Madrid";

	private static final String KEY_ID = "id";
	private static final String KEY_QUES = "question";
	private static final String KEY_ANSWER = "answer"; //correct option
	private static final String KEY_OPTA= "opta"; //option a
	private static final String KEY_OPTB= "optb"; //option b
	private static final String KEY_OPTC= "optc"; //option c
	private SQLiteDatabase sqLiteDatabase;
	private static Context myContext;
	
	public CityTourDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		myContext = context;
	}
	
	private void onCreate(SQLiteDatabase db, Context context) {
		sqLiteDatabase = db;
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_PLAZA_DE_ESPANA + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql2 = "CREATE TABLE IF NOT EXISTS " + TABLE_TEMPLO_DE_DEBOD + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql3 = "CREATE TABLE IF NOT EXISTS " + TABLE_PALACIO_REAL + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql4 = "CREATE TABLE IF NOT EXISTS " + TABLE_PLAZA_DE_CIBELES + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql5 = "CREATE TABLE IF NOT EXISTS " + TABLE_PLAZA_DE_ORIENTE + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql6 = "CREATE TABLE IF NOT EXISTS " + TABLE_PUERTA_DEL_SOL + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql7 = "CREATE TABLE IF NOT EXISTS " + TABLE_COLEGIATA_DE_SAN_ISIDRO + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql8 = "CREATE TABLE IF NOT EXISTS " + TABLE_PLAZA_DE_LA_VILLA + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql9 = "CREATE TABLE IF NOT EXISTS " + TABLE_PUERTA_DE_ALCALA + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql10 = "CREATE TABLE IF NOT EXISTS " + TABLE_GRAN_VIA + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql11 = "CREATE TABLE IF NOT EXISTS " + TABLE_GALERIA_TWIN + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql12 = "CREATE TABLE IF NOT EXISTS " + TABLE_MUSEO_DEL_PRADO + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql13 = "CREATE TABLE IF NOT EXISTS " + TABLE_MUSEO_REINA_SOFIA + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql14 = "CREATE TABLE IF NOT EXISTS " + TABLE_MUSEO_THYSSEN + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql15 = "CREATE TABLE IF NOT EXISTS " + TABLE_CAIXAFORUM + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql16 = "CREATE TABLE IF NOT EXISTS " + TABLE_MUSEO_LAZARO_GALDIANO + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql17 = "CREATE TABLE IF NOT EXISTS " + TABLE_MUSEO_SOROLLA + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql18 = "CREATE TABLE IF NOT EXISTS " + TABLE_MUSEO_ARQUEOLOGICO + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql19 = "CREATE TABLE IF NOT EXISTS " + TABLE_MUSEO_NAVAL + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql20 = "CREATE TABLE IF NOT EXISTS " + TABLE_LAS_VENTAS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql21 = "CREATE TABLE IF NOT EXISTS " + TABLE_TRIBUNAL + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql22 = "CREATE TABLE IF NOT EXISTS " + TABLE_PLAZA_DOS_DE_MAYO + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql23 = "CREATE TABLE IF NOT EXISTS " + TABLE_MERCADO_DE_SAN_MIGUEL + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql24 = "CREATE TABLE IF NOT EXISTS " + TABLE_TEATRO_REAL + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql25 = "CREATE TABLE IF NOT EXISTS " + TABLE_CATEDRAL_DE_LA_ALMUDENA + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql26 = "CREATE TABLE IF NOT EXISTS " + TABLE_PUERTA_DE_TOLEDO + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql27 = "CREATE TABLE IF NOT EXISTS " + TABLE_PALACIO_DE_ABRANTES + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql28 = "CREATE TABLE IF NOT EXISTS " + TABLE_IGLESIA_DE_SAN_NICOLAS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql29 = "CREATE TABLE IF NOT EXISTS " + TABLE_MUSEO_ABC + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql30 = "CREATE TABLE IF NOT EXISTS " + TABLE_PLAZA_DE_CALLAO + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql31 = "CREATE TABLE IF NOT EXISTS " + TABLE_PLAZA_DE_NEPTUNO + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql32 = "CREATE TABLE IF NOT EXISTS " + TABLE_CONGRESO + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql33 = "CREATE TABLE IF NOT EXISTS " + TABLE_PARQUE_DEL_RETIRO + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql34 = "CREATE TABLE IF NOT EXISTS " + TABLE_PALACIO_DE_CRISTAL + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql35 = "CREATE TABLE IF NOT EXISTS " + TABLE_VIADUCTO_DE_SEGOVIA + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql36 = "CREATE TABLE IF NOT EXISTS " + TABLE_CALLE_BAILEN + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql37 = "CREATE TABLE IF NOT EXISTS " + TABLE_ATOCHA + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql38 = "CREATE TABLE IF NOT EXISTS " + TABLE_JARDIN_BOTANICO + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		String sql39 = "CREATE TABLE IF NOT EXISTS " + TABLE_AYUNTAMIENTO + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		db.execSQL(sql);
		db.execSQL(sql2);
		db.execSQL(sql3);
		db.execSQL(sql4);
		db.execSQL(sql5);
		db.execSQL(sql6);
		db.execSQL(sql7);
		db.execSQL(sql8);
		db.execSQL(sql9);
		db.execSQL(sql10);
		db.execSQL(sql11);
		db.execSQL(sql12);
		db.execSQL(sql13);
		db.execSQL(sql14);
		db.execSQL(sql15);
		db.execSQL(sql16);
		db.execSQL(sql17);
		db.execSQL(sql18);
		db.execSQL(sql19);
		db.execSQL(sql20);
		db.execSQL(sql21);
		db.execSQL(sql22);
		db.execSQL(sql23);
		db.execSQL(sql24);
		db.execSQL(sql25);
		db.execSQL(sql26);
		db.execSQL(sql27);
		db.execSQL(sql28);
		db.execSQL(sql29);
		db.execSQL(sql30);
		db.execSQL(sql31);
		db.execSQL(sql32);
		db.execSQL(sql33);
		db.execSQL(sql34);
		db.execSQL(sql35);
		db.execSQL(sql36);
		db.execSQL(sql37);
		db.execSQL(sql38);
		db.execSQL(sql39);
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
		// Palacio Real
		pregunta1 = context.getResources().getStringArray(R.array.palacio_real_pregunta1);
		Question question_palacioReal_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_palacioReal_1, TABLE_PALACIO_REAL);
		pregunta2 = context.getResources().getStringArray(R.array.pzaEsp_pregunta2);
		Question question_palacioReal_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_palacioReal_2, TABLE_PALACIO_REAL);
		pregunta3 = context.getResources().getStringArray(R.array.pzaEsp_pregunta3);
		Question question_palacioReal_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_palacioReal_3, TABLE_PALACIO_REAL);
		// Plaza Mayor
		pregunta1 = context.getResources().getStringArray(R.array.pzaMayor_pregunta1);
		Question question_pzaMayor_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_pzaMayor_1, TABLE_PLAZA_MAYOR);
		pregunta2 = context.getResources().getStringArray(R.array.pzaMayor_pregunta2);
		Question question_pzaMayor_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_pzaMayor_2, TABLE_PLAZA_MAYOR);
		pregunta3 = context.getResources().getStringArray(R.array.pzaMayor_pregunta3);
		Question question_pzaMayor_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_pzaMayor_3, TABLE_PLAZA_MAYOR);
		// Plaza de Cibeles
		pregunta1 = context.getResources().getStringArray(R.array.pzaCib_pregunta1);
		Question question_pzaCibeles_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_pzaCibeles_1, TABLE_PLAZA_DE_CIBELES);
		pregunta2 = context.getResources().getStringArray(R.array.pzaCib_pregunta2);
		Question question_pzaCibeles_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_pzaCibeles_2, TABLE_PLAZA_DE_CIBELES);
		pregunta3 = context.getResources().getStringArray(R.array.pzaCib_pregunta3);
		Question question_pzaCibeles_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_pzaCibeles_3, TABLE_PLAZA_DE_CIBELES);
		// Plaza de Oriente
		pregunta1 = context.getResources().getStringArray(R.array.pzaOriente_pregunta1);
		Question question_pzaOriente_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_pzaOriente_1, TABLE_PLAZA_DE_ORIENTE);
		pregunta2 = context.getResources().getStringArray(R.array.pzaOriente_pregunta2);
		Question question_pzaOriente_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_pzaOriente_2, TABLE_PLAZA_DE_ORIENTE);
		pregunta3 = context.getResources().getStringArray(R.array.pzaOriente_pregunta3);
		Question question_pzaOriente_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_pzaOriente_3, TABLE_PLAZA_DE_ORIENTE);
		// Puerta del Sol
		pregunta1 = context.getResources().getStringArray(R.array.ptaSol_pregunta1);
		Question question_ptaSol_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_ptaSol_1, TABLE_PUERTA_DEL_SOL);
		pregunta2 = context.getResources().getStringArray(R.array.ptaSol_pregunta2);
		Question question_ptaSol_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_ptaSol_2, TABLE_PUERTA_DEL_SOL);
		pregunta3 = context.getResources().getStringArray(R.array.ptaSol_pregunta3);
		Question question_ptaSol_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_ptaSol_3, TABLE_PUERTA_DEL_SOL);
		// Colegiata de San Isidro
		pregunta1 = context.getResources().getStringArray(R.array.colegiata_pregunta1);
		Question question_colegiata_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_colegiata_1, TABLE_COLEGIATA_DE_SAN_ISIDRO);
		pregunta2 = context.getResources().getStringArray(R.array.colegiata_pregunta2);
		Question question_colegiata_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_colegiata_2, TABLE_COLEGIATA_DE_SAN_ISIDRO);
		pregunta3 = context.getResources().getStringArray(R.array.colegiata_pregunta3);
		Question question_colegiata_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_colegiata_3, TABLE_COLEGIATA_DE_SAN_ISIDRO);
		// Plaza de la Villa
		pregunta1 = context.getResources().getStringArray(R.array.pzaVilla_pregunta1);
		Question question_pzaVilla_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_pzaVilla_1, TABLE_PLAZA_DE_LA_VILLA);
		pregunta2 = context.getResources().getStringArray(R.array.pzaVilla_pregunta2);
		Question question_pzaVilla_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_pzaVilla_2, TABLE_PLAZA_DE_LA_VILLA);
		pregunta3 = context.getResources().getStringArray(R.array.pzaVilla_pregunta3);
		Question question_pzaVilla_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_pzaVilla_3, TABLE_PLAZA_DE_LA_VILLA);
		// Puerta de Alcalá
		pregunta1 = context.getResources().getStringArray(R.array.ptaAlcala_pregunta1);
		Question question_ptaAlcala_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_ptaAlcala_1, TABLE_PUERTA_DE_ALCALA);
		pregunta2 = context.getResources().getStringArray(R.array.ptaAlcala_pregunta2);
		Question question_ptaAlcala_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_ptaAlcala_2, TABLE_PUERTA_DE_ALCALA);
		pregunta3 = context.getResources().getStringArray(R.array.ptaAlcala_pregunta3);
		Question question_ptaAlcala_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_ptaAlcala_3, TABLE_PUERTA_DE_ALCALA);
		// Gran Vía
		pregunta1 = context.getResources().getStringArray(R.array.granVia_pregunta1);
		Question question_granVia_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_granVia_1, TABLE_GRAN_VIA);
		pregunta2 = context.getResources().getStringArray(R.array.granVia_pregunta2);
		Question question_granVia_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_granVia_2, TABLE_GRAN_VIA);
		pregunta3 = context.getResources().getStringArray(R.array.granVia_pregunta3);
		Question question_granVia_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_granVia_3, TABLE_GRAN_VIA);
		// Museo del Prado
		pregunta1 = context.getResources().getStringArray(R.array.mPrado_pregunta1);
		Question question_mPrado_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_mPrado_1, TABLE_MUSEO_DEL_PRADO);
		pregunta2 = context.getResources().getStringArray(R.array.mPrado_pregunta2);
		Question question_mPrado_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_mPrado_2, TABLE_MUSEO_DEL_PRADO);
		pregunta3 = context.getResources().getStringArray(R.array.mPrado_pregunta3);
		Question question_mPrado_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_mPrado_3, TABLE_MUSEO_DEL_PRADO);
		// Museo Reina Sofía
		pregunta1 = context.getResources().getStringArray(R.array.mRSofia_pregunta1);
		Question question_mRSofia_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_mRSofia_1, TABLE_MUSEO_REINA_SOFIA);
		pregunta2 = context.getResources().getStringArray(R.array.mRSofia_pregunta2);
		Question question_mRSofia_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_mRSofia_2, TABLE_MUSEO_REINA_SOFIA);
		pregunta3 = context.getResources().getStringArray(R.array.mRSofia_pregunta3);
		Question question_mRSofia_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_mRSofia_3, TABLE_MUSEO_REINA_SOFIA);
		// Museo Thyssen-Bornemisza
		pregunta1 = context.getResources().getStringArray(R.array.mThyssen_pregunta1);
		Question question_mThyssen_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_mThyssen_1, TABLE_MUSEO_THYSSEN);
		pregunta2 = context.getResources().getStringArray(R.array.mThyssen_pregunta2);
		Question question_mThyssen_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_mThyssen_2, TABLE_MUSEO_THYSSEN);
		pregunta3 = context.getResources().getStringArray(R.array.mThyssen_pregunta3);
		Question question_mThyssen_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_mThyssen_3, TABLE_MUSEO_THYSSEN);
		// CaixaForum
		pregunta1 = context.getResources().getStringArray(R.array.caixaforum_pregunta1);
		Question question_caixaforum_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_caixaforum_1, TABLE_CAIXAFORUM);
		pregunta2 = context.getResources().getStringArray(R.array.caixaforum_pregunta2);
		Question question_caixaforum_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_caixaforum_2, TABLE_CAIXAFORUM);
		pregunta3 = context.getResources().getStringArray(R.array.caixaforum_pregunta3);
		Question question_caixaforum_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_caixaforum_3, TABLE_CAIXAFORUM);
		// Museo Lázaro Galdiano
		pregunta1 = context.getResources().getStringArray(R.array.mLazaro_pregunta1);
		Question question_mLazaro_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_mLazaro_1, TABLE_MUSEO_LAZARO_GALDIANO);
		pregunta2 = context.getResources().getStringArray(R.array.mLazaro_pregunta2);
		Question question_mLazaro_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_mLazaro_2, TABLE_MUSEO_LAZARO_GALDIANO);
		pregunta3 = context.getResources().getStringArray(R.array.mLazaro_pregunta3);
		Question question_mLazaro_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_mLazaro_3, TABLE_MUSEO_LAZARO_GALDIANO);
		// Museo Sorolla
		pregunta1 = context.getResources().getStringArray(R.array.mSorolla_pregunta1);
		Question question_mSorolla_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_mSorolla_1, TABLE_MUSEO_SOROLLA);
		pregunta2 = context.getResources().getStringArray(R.array.mSorolla_pregunta2);
		Question question_mSorolla_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_mSorolla_2, TABLE_MUSEO_SOROLLA);
		pregunta3 = context.getResources().getStringArray(R.array.mSorolla_pregunta3);
		Question question_mSorolla_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_mSorolla_3, TABLE_MUSEO_SOROLLA);
		// Museo Arqueológico Nacional
		pregunta1 = context.getResources().getStringArray(R.array.mArq_pregunta1);
		Question question_mArq_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_mArq_1, TABLE_MUSEO_ARQUEOLOGICO);
		pregunta2 = context.getResources().getStringArray(R.array.mArq_pregunta2);
		Question question_mArq_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_mArq_2, TABLE_MUSEO_ARQUEOLOGICO);
		pregunta3 = context.getResources().getStringArray(R.array.mArq_pregunta3);
		Question question_mArq_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_mArq_3, TABLE_MUSEO_ARQUEOLOGICO);
		// Museo Naval
		pregunta1 = context.getResources().getStringArray(R.array.mNaval_pregunta1);
		Question question_mNaval_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_mNaval_1, TABLE_MUSEO_NAVAL);
		pregunta2 = context.getResources().getStringArray(R.array.mNaval_pregunta2);
		Question question_mNaval_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_mNaval_2, TABLE_MUSEO_NAVAL);
		pregunta3 = context.getResources().getStringArray(R.array.mNaval_pregunta3);
		Question question_mNaval_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_mNaval_3, TABLE_MUSEO_NAVAL);
		// Las Ventas
		pregunta1 = context.getResources().getStringArray(R.array.lasVentas_pregunta1);
		Question question_lasVentas_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_lasVentas_1, TABLE_LAS_VENTAS);
		pregunta2 = context.getResources().getStringArray(R.array.lasVentas_pregunta2);
		Question question_lasVentas_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_lasVentas_2, TABLE_LAS_VENTAS);
		pregunta3 = context.getResources().getStringArray(R.array.lasVentas_pregunta3);
		Question question_lasVentas_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_lasVentas_3, TABLE_LAS_VENTAS);
		// Tribunal
		pregunta1 = context.getResources().getStringArray(R.array.tribunal_pregunta1);
		Question question_tribunal_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_tribunal_1, TABLE_TRIBUNAL);
		pregunta2 = context.getResources().getStringArray(R.array.tribunal_pregunta2);
		Question question_tribunal_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_tribunal_2, TABLE_TRIBUNAL);
		pregunta3 = context.getResources().getStringArray(R.array.tribunal_pregunta3);
		Question question_tribunal_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_tribunal_3, TABLE_TRIBUNAL);
		// Plaza del Dos de Mayo
		pregunta1 = context.getResources().getStringArray(R.array.dosDeMayo_pregunta1);
		Question question_dosDeMayo_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_dosDeMayo_1, TABLE_PLAZA_DOS_DE_MAYO);
		pregunta2 = context.getResources().getStringArray(R.array.dosDeMayo_pregunta2);
		Question question_dosDeMayo_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_dosDeMayo_2, TABLE_PLAZA_DOS_DE_MAYO);
		pregunta3 = context.getResources().getStringArray(R.array.dosDeMayo_pregunta3);
		Question question_dosDeMayo_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_dosDeMayo_3, TABLE_PLAZA_DOS_DE_MAYO);
		// Mercado de San Miguel
		pregunta1 = context.getResources().getStringArray(R.array.mSMiguel_pregunta1);
		Question question_mSMiguel_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_mSMiguel_1, TABLE_MERCADO_DE_SAN_MIGUEL);
		pregunta2 = context.getResources().getStringArray(R.array.mSMiguel_pregunta2);
		Question question_mSMiguel_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_mSMiguel_2, TABLE_MERCADO_DE_SAN_MIGUEL);
		pregunta3 = context.getResources().getStringArray(R.array.mSMiguel_pregunta3);
		Question question_mSMiguel_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_mSMiguel_3, TABLE_MERCADO_DE_SAN_MIGUEL);
		// Teatro Real
		pregunta1 = context.getResources().getStringArray(R.array.teatReal_pregunta1);
		Question question_teatReal_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_teatReal_1, TABLE_TEATRO_REAL);
		pregunta2 = context.getResources().getStringArray(R.array.teatReal_pregunta2);
		Question question_teatReal_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_teatReal_2, TABLE_TEATRO_REAL);
		pregunta3 = context.getResources().getStringArray(R.array.teatReal_pregunta3);
		Question question_teatReal_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_teatReal_3, TABLE_TEATRO_REAL);
		// Catedral de La Almudena
		pregunta1 = context.getResources().getStringArray(R.array.catAlmu_pregunta1);
		Question question_catAlmu_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_catAlmu_1, TABLE_CATEDRAL_DE_LA_ALMUDENA);
		pregunta2 = context.getResources().getStringArray(R.array.catAlmu_pregunta2);
		Question question_catAlmu_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_catAlmu_2, TABLE_CATEDRAL_DE_LA_ALMUDENA);
		pregunta3 = context.getResources().getStringArray(R.array.catAlmu_pregunta3);
		Question question_catAlmu_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_catAlmu_3, TABLE_CATEDRAL_DE_LA_ALMUDENA);
		// Puerta de Toledo
		pregunta1 = context.getResources().getStringArray(R.array.ptaTol_pregunta1);
		Question question_ptaTol_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_ptaTol_1, TABLE_PUERTA_DE_TOLEDO);
		pregunta2 = context.getResources().getStringArray(R.array.ptaTol_pregunta2);
		Question question_ptaTol_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_ptaTol_2, TABLE_PUERTA_DE_TOLEDO);
		pregunta3 = context.getResources().getStringArray(R.array.ptaTol_pregunta3);
		Question question_ptaTol_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_ptaTol_3, TABLE_PUERTA_DE_TOLEDO);
		// Palacio de Abrantes
		pregunta1 = context.getResources().getStringArray(R.array.pAbrantes_pregunta1);
		Question question_pAbrantes_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_pAbrantes_1, TABLE_PALACIO_DE_ABRANTES);
		pregunta2 = context.getResources().getStringArray(R.array.pAbrantes_pregunta2);
		Question question_pAbrantes_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_pAbrantes_2, TABLE_PALACIO_DE_ABRANTES);
		pregunta3 = context.getResources().getStringArray(R.array.pAbrantes_pregunta3);
		Question question_pAbrantes_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_pAbrantes_3, TABLE_PALACIO_DE_ABRANTES);
		// Iglesia de San Nicolás
		pregunta1 = context.getResources().getStringArray(R.array.iSNicolas_pregunta1);
		Question question_iSNicolas_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_iSNicolas_1, TABLE_IGLESIA_DE_SAN_NICOLAS);
		pregunta2 = context.getResources().getStringArray(R.array.iSNicolas_pregunta2);
		Question question_iSNicolas_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_iSNicolas_2, TABLE_IGLESIA_DE_SAN_NICOLAS);
		pregunta3 = context.getResources().getStringArray(R.array.iSNicolas_pregunta3);
		Question question_iSNicolas_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_iSNicolas_3, TABLE_IGLESIA_DE_SAN_NICOLAS);
		// Museo ABC
		pregunta1 = context.getResources().getStringArray(R.array.abc_pregunta1);
		Question question_abc_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_abc_1, TABLE_MUSEO_ABC);
		pregunta2 = context.getResources().getStringArray(R.array.abc_pregunta2);
		Question question_abc_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_abc_2, TABLE_MUSEO_ABC);
		pregunta3 = context.getResources().getStringArray(R.array.abc_pregunta3);
		Question question_abc_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_abc_3, TABLE_MUSEO_ABC);
		// Plaza de Callao
		pregunta1 = context.getResources().getStringArray(R.array.callao_pregunta1);
		Question question_callao_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_callao_1, TABLE_PLAZA_DE_CALLAO);
		pregunta2 = context.getResources().getStringArray(R.array.callao_pregunta2);
		Question question_callao_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_callao_2, TABLE_PLAZA_DE_CALLAO);
		pregunta3 = context.getResources().getStringArray(R.array.callao_pregunta3);
		Question question_callao_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_callao_3, TABLE_PLAZA_DE_CALLAO);
		// Plaza de Neptuno
		pregunta1 = context.getResources().getStringArray(R.array.pzaNeptuno_pregunta1);
		Question question_pzaNeptuno_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_pzaNeptuno_1, TABLE_PLAZA_DE_NEPTUNO);
		pregunta2 = context.getResources().getStringArray(R.array.pzaNeptuno_pregunta2);
		Question question_pzaNeptuno_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_pzaNeptuno_2, TABLE_PLAZA_DE_NEPTUNO);
		pregunta3 = context.getResources().getStringArray(R.array.pzaNeptuno_pregunta3);
		Question question_pzaNeptuno_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_pzaNeptuno_3, TABLE_PLAZA_DE_NEPTUNO);
		// Congreso de los Diputados
		pregunta1 = context.getResources().getStringArray(R.array.diputados_pregunta1);
		Question question_diputados_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_diputados_1, TABLE_CONGRESO);
		pregunta2 = context.getResources().getStringArray(R.array.diputados_pregunta2);
		Question question_diputados_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_diputados_2, TABLE_CONGRESO);
		pregunta3 = context.getResources().getStringArray(R.array.diputados_pregunta3);
		Question question_diputados_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_diputados_3, TABLE_CONGRESO);
		// Parque del Retiro
		pregunta1 = context.getResources().getStringArray(R.array.retiro_pregunta1);
		Question question_retiro_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_retiro_1, TABLE_PARQUE_DEL_RETIRO);
		pregunta2 = context.getResources().getStringArray(R.array.retiro_pregunta2);
		Question question_retiro_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_retiro_2, TABLE_PARQUE_DEL_RETIRO);
		pregunta3 = context.getResources().getStringArray(R.array.retiro_pregunta3);
		Question question_retiro_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_retiro_3, TABLE_PARQUE_DEL_RETIRO);
		// Palacio de Cristal
		pregunta1 = context.getResources().getStringArray(R.array.pCristal_pregunta1);
		Question question_pCristal_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_pCristal_1, TABLE_PALACIO_DE_CRISTAL);
		pregunta2 = context.getResources().getStringArray(R.array.pCristal_pregunta2);
		Question question_pCristal_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_pCristal_2, TABLE_PALACIO_DE_CRISTAL);
		pregunta3 = context.getResources().getStringArray(R.array.pCristal_pregunta3);
		Question question_pCristal_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_pCristal_3, TABLE_PALACIO_DE_CRISTAL);
		// Viaducto de Segovia
		pregunta1 = context.getResources().getStringArray(R.array.viaducto_pregunta1);
		Question question_viaducto_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_viaducto_1, TABLE_VIADUCTO_DE_SEGOVIA);
		pregunta2 = context.getResources().getStringArray(R.array.viaducto_pregunta2);
		Question question_viaducto_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_viaducto_2, TABLE_VIADUCTO_DE_SEGOVIA);
		pregunta3 = context.getResources().getStringArray(R.array.viaducto_pregunta3);
		Question question_viaducto_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_viaducto_3, TABLE_VIADUCTO_DE_SEGOVIA);
		// Calle Bailén
		pregunta1 = context.getResources().getStringArray(R.array.bailen_pregunta1);
		Question question_bailen_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_bailen_1, TABLE_CALLE_BAILEN);
		pregunta2 = context.getResources().getStringArray(R.array.bailen_pregunta2);
		Question question_bailen_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_bailen_2, TABLE_CALLE_BAILEN);
		pregunta3 = context.getResources().getStringArray(R.array.bailen_pregunta3);
		Question question_bailen_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_bailen_3, TABLE_CALLE_BAILEN);
		// Atocha
		pregunta1 = context.getResources().getStringArray(R.array.atocha_pregunta1);
		Question question_atocha_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_atocha_1, TABLE_ATOCHA);
		pregunta2 = context.getResources().getStringArray(R.array.atocha_pregunta2);
		Question question_atocha_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_atocha_2, TABLE_ATOCHA);
		pregunta3 = context.getResources().getStringArray(R.array.atocha_pregunta3);
		Question question_atocha_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_atocha_3, TABLE_ATOCHA);
		// Jardín Botánico
		pregunta1 = context.getResources().getStringArray(R.array.jBot_pregunta1);
		Question question_jBot_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_jBot_1, TABLE_JARDIN_BOTANICO);
		pregunta2 = context.getResources().getStringArray(R.array.jBot_pregunta2);
		Question question_jBot_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_jBot_2, TABLE_JARDIN_BOTANICO);
		pregunta3 = context.getResources().getStringArray(R.array.jBot_pregunta3);
		Question question_jBot_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_jBot_3, TABLE_JARDIN_BOTANICO);
		// Ayuntamiento de Madrid
		pregunta1 = context.getResources().getStringArray(R.array.ayuntamiento_pregunta1);
		Question question_ayuntamiento_1 = new Question(pregunta1[0],pregunta1[1],pregunta1[2],pregunta1[3],pregunta1[4]);
		this.addQuestion(question_ayuntamiento_1, TABLE_AYUNTAMIENTO);
		pregunta2 = context.getResources().getStringArray(R.array.ayuntamiento_pregunta2);
		Question question_ayuntamiento_2 = new Question(pregunta2[0],pregunta2[1],pregunta2[2],pregunta2[3],pregunta2[4]);
		this.addQuestion(question_ayuntamiento_2, TABLE_AYUNTAMIENTO);
		pregunta3 = context.getResources().getStringArray(R.array.ayuntamiento_pregunta3);
		Question question_ayuntamiento_3 = new Question(pregunta3[0],pregunta3[1],pregunta3[2],pregunta3[3],pregunta3[4]);
		this.addQuestion(question_ayuntamiento_3, TABLE_AYUNTAMIENTO);
	}
	
	// Adding new question
	private boolean addQuestion(Question quest, String tableName) {
		if(sqLiteDatabase !=null){
			try{
				ContentValues values = new ContentValues();
				values.put(KEY_QUES, quest.getQUESTION());
				values.put(KEY_ANSWER, quest.getANSWER());
				values.put(KEY_OPTA, quest.getOPTA());
				values.put(KEY_OPTB, quest.getOPTB());
				values.put(KEY_OPTC, quest.getOPTC());
				long result = sqLiteDatabase.insert(tableName, null, values);
				return (result > 0);
			}catch (SQLException ex) {
			       Log.w("SQLException", ex.fillInStackTrace());
			       return false;
			}
		}else {
			Log.d("SQLite SUCKS", "no entiendo nada");
			return false;
		}
	}
		
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAZA_DE_ESPANA);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMPLO_DE_DEBOD);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PALACIO_REAL);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAZA_MAYOR);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAZA_DE_CIBELES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAZA_DE_ORIENTE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PUERTA_DEL_SOL);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLEGIATA_DE_SAN_ISIDRO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAZA_DE_LA_VILLA);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PUERTA_DE_ALCALA);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRAN_VIA);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GALERIA_TWIN);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MUSEO_DEL_PRADO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MUSEO_REINA_SOFIA);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MUSEO_THYSSEN);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAIXAFORUM);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MUSEO_LAZARO_GALDIANO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MUSEO_SOROLLA);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MUSEO_ARQUEOLOGICO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MUSEO_NAVAL);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LAS_VENTAS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIBUNAL);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAZA_DOS_DE_MAYO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MERCADO_DE_SAN_MIGUEL);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEATRO_REAL);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEDRAL_DE_LA_ALMUDENA);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PUERTA_DE_TOLEDO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PALACIO_DE_ABRANTES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_IGLESIA_DE_SAN_NICOLAS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MUSEO_ABC);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAZA_DE_CALLAO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAZA_DE_NEPTUNO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONGRESO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARQUE_DEL_RETIRO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PALACIO_DE_CRISTAL);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIADUCTO_DE_SEGOVIA);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALLE_BAILEN);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATOCHA);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_JARDIN_BOTANICO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_AYUNTAMIENTO);
				
		onCreate(db);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		this.onCreate(db, myContext);
	}
}
