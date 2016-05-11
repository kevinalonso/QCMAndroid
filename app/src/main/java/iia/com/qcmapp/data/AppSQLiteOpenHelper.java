package iia.com.qcmapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kevin-pc on 31/01/2016.
 */
public class AppSQLiteOpenHelper extends SQLiteOpenHelper {
    //region DB NAME and VERSION
    /**
     * region DB NAME and VERSION
     */
    private static final String DATABASE_NAME = "qcms.db";
    private static final int DATABASE_VERSION = 1;
    //endregion

    //region TABLE QCM
    /**
     * Element in my table and the name of table in database.
     */
    public static final String TABLE_QCM = "qcm";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME_QCM = "NameQcm";
    public static final String COLUMN_DATE_START = "DateStart";
    public static final String COLUMN_DATE_END = "DateEnd";
    public static final String COLUMN_IS_ACTIVE = "IsActive";
    public static final String COLUMN_ID_TYPE = "_id_Type";

    /**
     * To create database
     */
    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_QCM + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME_QCM + " TEXT NOT NULL,"
                + COLUMN_DATE_START + " DATETIME,"
                + COLUMN_DATE_END + " DATETIME, "
                + COLUMN_IS_ACTIVE + " BOOLEAN, "
                + COLUMN_ID_TYPE + " INTEGER);";
    //endregion

    //region TABLE QUESTION
    public static final String TABLE_QUESTION = "question";
    public static final String COLUMN_ID_QUESTION = "_id";
    public static final String COLUMN_NAME_QUESTION = "TextQuestion";
    public static final String COLUMN_ID_TYPE_QUESTION = "_id_Type";
    public static final String COLUMN_ID_QCM = "_id_Qcm";


    private static final String DATABASE_CREATE_QUESTION = "CREATE TABLE " + TABLE_QUESTION + " (" + COLUMN_ID_QUESTION + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME_QUESTION + " TEXT NOT NULL,"
            + COLUMN_ID_TYPE_QUESTION + " INTEGER, "
            + COLUMN_ID_QCM + " INTEGER, "
            + " FOREIGN KEY("+COLUMN_ID_QCM + ") REFERENCES "
            + TABLE_QCM + "(_id) "+")";
    //endregion

    //region TABLE GOODANSWER
    public static final String TABLE_ANSWER = "good_answer";
    public static final String COLUMN_ID_ANSWER = "_id";
    public static final String COLUMN_NAME_ANSWER = "AnswerQuestion";
    public static final String COLUMN_ID_QUESTION_FK = "_id_Question";


    private static final String DATABASE_CREATE_ANSWER = "CREATE TABLE " + TABLE_ANSWER + " (" + COLUMN_ID_ANSWER + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME_ANSWER + " TEXT NOT NULL,"
            + COLUMN_ID_QUESTION_FK + " INTEGER, "
            + " FOREIGN KEY("+COLUMN_ID_QUESTION_FK + ") REFERENCES "
            + TABLE_QUESTION + "(_id) "+")";
    //endregion

    //region TABLE BADANSWER
    public static final String TABLE_BAD_ANSWER = "bad_answer";
    public static final String COLUMN_ID_BAD_ANSWER = "_id";
    public static final String COLUMN_NAME_BAD_ANSWER = "BadAnswerQuestion";
    public static final String COLUMN_BAD_ID_QUESTION_FK = "_id_Question";


    private static final String DATABASE_CREATE_BAD_ANSWER = "CREATE TABLE " + TABLE_BAD_ANSWER + " (" + COLUMN_ID_BAD_ANSWER + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME_BAD_ANSWER + " TEXT NOT NULL,"
            + COLUMN_BAD_ID_QUESTION_FK + " INTEGER, "
            + " FOREIGN KEY("+COLUMN_BAD_ID_QUESTION_FK + ") REFERENCES "
            + TABLE_QUESTION + "(_id) "+")";
    //endregion

    public AppSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        database.execSQL(DATABASE_CREATE_QUESTION);
        database.execSQL(DATABASE_CREATE_ANSWER);
        database.execSQL(DATABASE_CREATE_BAD_ANSWER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(AppSQLiteOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QCM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BAD_ANSWER);
        onCreate(db);
    }


}
