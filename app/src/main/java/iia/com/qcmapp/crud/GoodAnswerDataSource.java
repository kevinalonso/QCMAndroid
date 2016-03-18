package iia.com.qcmapp.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import iia.com.qcmapp.data.AppSQLiteOpenHelper;
import iia.com.qcmapp.entity.GoodAnswer;

/**
 * Created by kevin-pc on 06/03/2016.
 */
public class GoodAnswerDataSource {

    private SQLiteDatabase database;
    private AppSQLiteOpenHelper dbHelper;
    private String[] allColumns = { AppSQLiteOpenHelper.COLUMN_ID_ANSWER,
            AppSQLiteOpenHelper.COLUMN_NAME_ANSWER,
            AppSQLiteOpenHelper.COLUMN_ID_QUESTION_FK};

    public GoodAnswerDataSource(Context context) {
        dbHelper = new AppSQLiteOpenHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public GoodAnswer createGoodAnwser(String textAnswer, long id,long idQuestion) {
        Boolean exist = existGoodAnswerWithId(id);

        if(exist == true){
            GoodAnswer existGoodAnswer = getGoodAnswerWithId(idQuestion);
            GoodAnswer updateGoodAnswer = updateGoodAnswer(id, existGoodAnswer);
            return updateGoodAnswer;
        }
        else {
            ContentValues values = new ContentValues();
            values.put(AppSQLiteOpenHelper.COLUMN_NAME_ANSWER, textAnswer);
            values.put(AppSQLiteOpenHelper.COLUMN_ID_QUESTION_FK, idQuestion);

            long insertId = database.insert(AppSQLiteOpenHelper.TABLE_ANSWER, null,
                    values);
            Cursor cursor = database.query(AppSQLiteOpenHelper.TABLE_ANSWER,
                    allColumns, AppSQLiteOpenHelper.COLUMN_ID_ANSWER + " = " + insertId, null,
                    null, null, null);
            cursor.moveToFirst();
            GoodAnswer newGoodAnswer = cursorToGoodAnswer(cursor);
            cursor.close();
            return newGoodAnswer;
        }
    }

    public GoodAnswer updateGoodAnswer(long id, GoodAnswer goodAnswer){
        ContentValues values = new ContentValues();

        values.put(AppSQLiteOpenHelper.COLUMN_NAME_ANSWER, goodAnswer.getAnswerQuestion());
        values.put(AppSQLiteOpenHelper.COLUMN_ID_QUESTION_FK, goodAnswer.getIdQuestion());

        database.update(AppSQLiteOpenHelper.TABLE_ANSWER, values, AppSQLiteOpenHelper.COLUMN_ID_ANSWER + " = " + goodAnswer.getId(), null);

        return getGoodAnswerWithId(goodAnswer.getId());
    }

    public void deleteGoodAnswer(GoodAnswer goodAnswer) {
        long id = goodAnswer.getId();
        System.out.println("Contact deleted with id: " + id);
        database.delete(AppSQLiteOpenHelper.TABLE_ANSWER, AppSQLiteOpenHelper.COLUMN_ID_ANSWER
                + " = " + id, null);
    }

    public List<GoodAnswer> getAllGoodAnswer() {
        List<GoodAnswer> goodAnswers = new ArrayList<GoodAnswer>();

        Cursor cursor = database.query(AppSQLiteOpenHelper.TABLE_ANSWER,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            GoodAnswer goodAnswer = cursorToGoodAnswer(cursor);
            goodAnswers.add(goodAnswer);
            cursor.moveToNext();
        }

        cursor.close();
        return goodAnswers;
    }

    public GoodAnswer getGoodAnswerWithId(Long id){
        Cursor cursor = database.query(AppSQLiteOpenHelper.TABLE_ANSWER, allColumns, AppSQLiteOpenHelper.COLUMN_ID_QUESTION_FK + " = \"" + id +"\"", null, null, null, null);
        cursor.moveToFirst();
        GoodAnswer goodAnswer = cursorToGoodAnswer(cursor);
        cursor.close();
        return goodAnswer;
    }

    public List<GoodAnswer> getGoodAnswerWithIdList(Long id){
        List<GoodAnswer> goodAnswerList = new ArrayList<GoodAnswer>();
        Cursor cursor = database.query(AppSQLiteOpenHelper.TABLE_ANSWER, allColumns, AppSQLiteOpenHelper.COLUMN_ID_QUESTION_FK + " = \"" + id + "\"", null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            GoodAnswer goodAnswer = cursorToGoodAnswer(cursor);
            goodAnswerList.add(goodAnswer);
            cursor.moveToNext();
        }
        cursor.close();
        return goodAnswerList;
    }

    public Boolean existGoodAnswerWithId(Long id){
        Cursor c = database.query(AppSQLiteOpenHelper.TABLE_ANSWER, allColumns, AppSQLiteOpenHelper.COLUMN_ID_ANSWER + " = \"" + id +"\"", null, null, null, null);
        if(c.getCount()>0){
            c.close();
            return true;
        }
        else {
            c.close();
            return false;
        }
    }

    private GoodAnswer cursorToGoodAnswer(Cursor cursor) {
        GoodAnswer goodAnswer = new GoodAnswer();
        goodAnswer.setId(cursor.getLong(0));
        goodAnswer.setAnswerQuestion(cursor.getString(1));
        goodAnswer.setIdQuestion(cursor.getLong(2));

        return goodAnswer;
    }
}
