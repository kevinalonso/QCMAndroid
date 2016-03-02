package iia.com.qcmapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kevin-pc on 07/02/2016.
 */
public class QuestionSQLiteOpenHelper extends SQLiteOpenHelper {
    /**
     * Element in my table and the name of table in database.
     */
    public static final String TABLE_QUESTION = "questions";
    public static final String TABLE_QCM = "qcms";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME_QUESTION = "TextQuestion";
    public static final String COLUMN_ID_TYPE = "_id_Type";
    public static final String COLUMN_ID_QCM = "_id_Qcm";

    private static final String DATABASE_NAME = "qcms.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_QUESTION + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME_QUESTION + " TEXT NOT NULL,"
            + COLUMN_ID_TYPE + " INTEGER, "
            + COLUMN_ID_QCM + " INTEGER, "
            + " FOREIGN KEY("+COLUMN_ID_QCM + ") REFERENCES "
            + TABLE_QCM + "(_id) "+")";

    public QuestionSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(AppSQLiteOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        onCreate(db);
    }
}
