package iia.com.qcmapp.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import iia.com.qcmapp.data.AppSQLiteOpenHelper;
import iia.com.qcmapp.entity.BadAnswer;

/**
 * Created by kevin-pc on 30/03/2016.
 */
public class BadAnswerDataSource {

    private SQLiteDatabase database;
    private AppSQLiteOpenHelper dbHelper;
    private String[] allColumns = { AppSQLiteOpenHelper.COLUMN_ID_BAD_ANSWER,
            AppSQLiteOpenHelper.COLUMN_NAME_BAD_ANSWER,
            AppSQLiteOpenHelper.COLUMN_BAD_ID_QUESTION_FK};

    public BadAnswerDataSource(Context context) {
        dbHelper = new AppSQLiteOpenHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * Use to create badAnswer in databases if this not exist else check if the column is update
     * @param id badAnswer
     * @param idQuestion id of question
     * @return
     */
    public BadAnswer createBadAnwser(String textAnswer, long id,long idQuestion) {
        Boolean exist = existBadAnswerWithId(id);

        if(exist == true){
            BadAnswer existBadAnswer = getBadAnswerWithId(idQuestion);
            BadAnswer updateBadAnswer = updateBadAnswer(id, existBadAnswer);
            return updateBadAnswer;
        }
        else {
            ContentValues values = new ContentValues();
            values.put(AppSQLiteOpenHelper.COLUMN_NAME_BAD_ANSWER, textAnswer);
            values.put(AppSQLiteOpenHelper.COLUMN_BAD_ID_QUESTION_FK, idQuestion);

            long insertId = database.insert(AppSQLiteOpenHelper.TABLE_BAD_ANSWER, null,
                    values);
            Cursor cursor = database.query(AppSQLiteOpenHelper.TABLE_BAD_ANSWER,
                    allColumns, AppSQLiteOpenHelper.COLUMN_ID_BAD_ANSWER + " = " + insertId, null,
                    null, null, null);
            cursor.moveToFirst();
            BadAnswer newBadAnswer = cursorToBadAnswer(cursor);
            cursor.close();
            return newBadAnswer;
        }
    }

    /**
     * Use to update badAnswer in databases
     * @param id id badAnswer
     * @param badAnswer all value for the badAnswer
     * @return
     */
    public BadAnswer updateBadAnswer(long id, BadAnswer badAnswer){
        ContentValues values = new ContentValues();

        values.put(AppSQLiteOpenHelper.COLUMN_NAME_BAD_ANSWER, badAnswer.getBadAnswerQuestion());
        values.put(AppSQLiteOpenHelper.COLUMN_BAD_ID_QUESTION_FK, badAnswer.getIdQuestion());

        database.update(AppSQLiteOpenHelper.TABLE_BAD_ANSWER, values, AppSQLiteOpenHelper.COLUMN_ID_BAD_ANSWER + " = " + badAnswer.getId(), null);

        return getBadAnswerWithId(badAnswer.getId());
    }

    public void deleteBadAnswer(BadAnswer goodAnswer) {
        long id = goodAnswer.getId();
        System.out.println("Contact deleted with id: " + id);
        database.delete(AppSQLiteOpenHelper.TABLE_BAD_ANSWER, AppSQLiteOpenHelper.COLUMN_ID_BAD_ANSWER
                + " = " + id, null);
    }

    public List<BadAnswer> getAllBadAnswer() {
        List<BadAnswer> goodAnswers = new ArrayList<BadAnswer>();

        Cursor cursor = database.query(AppSQLiteOpenHelper.TABLE_BAD_ANSWER,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            BadAnswer goodAnswer = cursorToBadAnswer(cursor);
            goodAnswers.add(goodAnswer);
            cursor.moveToNext();
        }

        cursor.close();
        return goodAnswers;
    }

    /**
     * Get one badAnswer
     * @param id id for one goodAnswer
     * @return the badAnswer where id = id_badAnswer
     */
    public BadAnswer getBadAnswerWithId(Long id){
        Cursor cursor = database.query(AppSQLiteOpenHelper.TABLE_BAD_ANSWER, allColumns, AppSQLiteOpenHelper.COLUMN_ID_BAD_ANSWER + " = \"" + id +"\"", null, null, null, null);
        cursor.moveToFirst();
        BadAnswer badAnswer = cursorToBadAnswer(cursor);
        cursor.close();
        return badAnswer;
    }

    /**
     * Get Bad Answer from question in database
     * @param id question
     * @return the badAnswer where id = id_question
     */
    public List<BadAnswer> getBadAnswerWithIdList(Long id){
        List<BadAnswer> badAnswerList = new ArrayList<BadAnswer>();
        Cursor cursor = database.query(AppSQLiteOpenHelper.TABLE_BAD_ANSWER, allColumns, AppSQLiteOpenHelper.COLUMN_BAD_ID_QUESTION_FK + " = \"" + id + "\"", null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            BadAnswer badAnswer = cursorToBadAnswer(cursor);
            badAnswerList.add(badAnswer);
            cursor.moveToNext();
        }
        cursor.close();
        return badAnswerList;
    }
    /**
     * Get if badAnswer exist
     * @param id of badAnswer
     * @return if badAnswer exist in databases
     */
    public Boolean existBadAnswerWithId(Long id){
        Cursor c = database.query(AppSQLiteOpenHelper.TABLE_BAD_ANSWER, allColumns, AppSQLiteOpenHelper.COLUMN_ID_BAD_ANSWER + " = \"" + id +"\"", null, null, null, null);
        if(c.getCount()>0){
            c.close();
            return true;
        }
        else {
            c.close();
            return false;
        }
    }

    /**
     * Return badAnswer
     * @param cursor result comlumn from databases
     * @return entity badAnswer completed
     */
    private BadAnswer cursorToBadAnswer(Cursor cursor) {
        BadAnswer badAnswer = new BadAnswer();
        badAnswer.setId(cursor.getLong(0));
        badAnswer.setBadAnswerQuestion(cursor.getString(1));
        badAnswer.setIdQuestion(cursor.getLong(2));

        return badAnswer;
    }
}
