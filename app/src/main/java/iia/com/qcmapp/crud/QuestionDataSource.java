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

    /**
     * Use to create question in databases if this not exist else check if the column is update
     * @param id question
     * @param idQcm id of qcm
     * @return
     */
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

    /**
     * Use to update qcm in databases
     * @param id id question
     * @param question all value for the question
     * @return
     */
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

    /**
     * Get all question
     * @return all question in database
     */
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

    /**
     * Get one Qcm
     * @param id id for one qcm
     * @return the question where id = id_qcm
     */
    public Question getQuestionWithId(Long id){
        Cursor cursor = database.query(AppSQLiteOpenHelper.TABLE_QUESTION, allColumns, AppSQLiteOpenHelper.COLUMN_ID_QCM + " = \"" + id +"\"", null, null, null, null);
        cursor.moveToFirst();
        Question question = cursorToQuestion(cursor);
        cursor.close();
        return question;
    }

    /**
     * Get one Qcm
     * @param id id for one qcm
     * @return list question where id = id_qcm
     */
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

    /**
     * Get if Question exist
     * @param id of question
     * @return if question exist in databases
     */
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

    /**
     * Return Question
     * @param cursor result comlumn from databases
     * @return entity question completed
     */
    private Question cursorToQuestion(Cursor cursor) {
        Question question = new Question();
        question.setId(cursor.getLong(0));
        question.setTextQuestion(cursor.getString(1));
        question.setIdType(cursor.getLong(2));
        question.setIdQcm(cursor.getLong(3));
        return question;
    }
}
