package iia.com.qcmapp.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import iia.com.qcmapp.data.AppSQLiteOpenHelper;
import iia.com.qcmapp.entity.Question;

/**
 * Created by kevin-pc on 07/02/2016.
 */
public class QuestionDataSource {
    private SQLiteDatabase database;
    private AppSQLiteOpenHelper dbHelper;
    //QuestionSQLiteOpenHelper
    private String[] allColumns = { AppSQLiteOpenHelper.COLUMN_ID_QUESTION,
            AppSQLiteOpenHelper.COLUMN_NAME_QUESTION,
            AppSQLiteOpenHelper.COLUMN_ID_TYPE,
            AppSQLiteOpenHelper.COLUMN_ID_QCM};

    public QuestionDataSource(Context context) {
        dbHelper = new AppSQLiteOpenHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Question createQuestion(String textQuestion, long id,long idQcm) {
        Boolean exist = existQuestionWithId(id);

        if(exist == true){
            Question existQuestion = getQuestionWithId(idQcm);
            Question updateQuestion = updateQuestion(id, existQuestion);
            return updateQuestion;
        }
        else {
            ContentValues values = new ContentValues();
            values.put(AppSQLiteOpenHelper.COLUMN_NAME_QUESTION, textQuestion);
            //values.put(QuestionSQLiteOpenHelper.COLUMN_ID_TYPE, idType);
            values.put(AppSQLiteOpenHelper.COLUMN_ID_QCM, idQcm);

            long insertId = database.insert(AppSQLiteOpenHelper.TABLE_QUESTION, null,
                    values);
            Cursor cursor = database.query(AppSQLiteOpenHelper.TABLE_QUESTION,
                    allColumns, AppSQLiteOpenHelper.COLUMN_ID_QUESTION + " = " + insertId, null,
                    null, null, null);
            cursor.moveToFirst();
            Question newQuestion = cursorToQuestion(cursor);
            cursor.close();
            return newQuestion;
        }
    }

    public Question updateQuestion(long id, Question question){
        ContentValues values = new ContentValues();

        values.put(AppSQLiteOpenHelper.COLUMN_NAME_QUESTION, question.getTextQuestion());
        values.put(AppSQLiteOpenHelper.COLUMN_ID_TYPE, question.getIdType());
        values.put(AppSQLiteOpenHelper.COLUMN_ID_QCM, question.getIdQcm());

        database.update(AppSQLiteOpenHelper.TABLE_QUESTION, values, AppSQLiteOpenHelper.COLUMN_ID_QUESTION + " = " + question.getId(), null);

        return getQuestionWithId(question.getId());
    }

    public void deleteQcm(Question qcm) {
        long id = qcm.getId();
        System.out.println("Contact deleted with id: " + id);
        database.delete(AppSQLiteOpenHelper.TABLE_QUESTION, AppSQLiteOpenHelper.COLUMN_ID_QUESTION
                + " = " + id, null);
    }

    public List<Question> getAllQuestion() {
        List<Question> questions = new ArrayList<Question>();

        Cursor cursor = database.query(AppSQLiteOpenHelper.TABLE_QUESTION,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Question question = cursorToQuestion(cursor);
            questions.add(question);
            cursor.moveToNext();
        }

        cursor.close();
        return questions;
    }

    public Question getQuestionWithId(Long id){
        Cursor cursor = database.query(AppSQLiteOpenHelper.TABLE_QUESTION, allColumns, AppSQLiteOpenHelper.COLUMN_ID_QCM + " = \"" + id +"\"", null, null, null, null);
        cursor.moveToFirst();
        Question question = cursorToQuestion(cursor);
        cursor.close();
        return question;
    }

    public List<Question> getQuestionWithIdList(Long id){
        List<Question> questionList = new ArrayList<Question>();
        Cursor cursor = database.query(AppSQLiteOpenHelper.TABLE_QUESTION, allColumns, AppSQLiteOpenHelper.COLUMN_ID_QCM + " = \"" + id + "\"", null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Question question = cursorToQuestion(cursor);
            questionList.add(question);
            cursor.moveToNext();
        }
        cursor.close();
        return questionList;
    }

    public Boolean existQuestionWithId(Long id){
        Cursor c = database.query(AppSQLiteOpenHelper.TABLE_QUESTION, allColumns, AppSQLiteOpenHelper.COLUMN_ID_QUESTION + " = \"" + id +"\"", null, null, null, null);
        if(c.getCount()>0){
            c.close();
            return true;
        }
        else {
            c.close();
            return false;
        }
    }

    private Question cursorToQuestion(Cursor cursor) {
        Question question = new Question();
        question.setId(cursor.getLong(0));
        question.setTextQuestion(cursor.getString(1));
        question.setIdType(cursor.getLong(2));
        question.setIdQcm(cursor.getLong(3));
        return question;
    }
}
