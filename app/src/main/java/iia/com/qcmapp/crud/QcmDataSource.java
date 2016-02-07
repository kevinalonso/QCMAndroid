package iia.com.qcmapp.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import iia.com.qcmapp.data.QcmSQLiteOpenHelper;
import iia.com.qcmapp.entity.Qcm;

/**
 * Created by kevin-pc on 31/01/2016.
 */

public class QcmDataSource {
    // Database fields
    private SQLiteDatabase database;
    private QcmSQLiteOpenHelper dbHelper;
    private String[] allColumns = { QcmSQLiteOpenHelper.COLUMN_ID,
            QcmSQLiteOpenHelper.COLUMN_NAME_QCM,
            QcmSQLiteOpenHelper.COLUMN_DATE_START,
            QcmSQLiteOpenHelper.COLUMN_DATE_END,
            //QcmSQLiteOpenHelper.COLUMN_IS_ACTIVE,
            QcmSQLiteOpenHelper.COLUMN_ID_TYPE};

    public QcmDataSource(Context context) {
        dbHelper = new QcmSQLiteOpenHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Qcm createQcm(String nameQcm, String dateStart, String dateEnd, boolean isActive, int idType, long idQcm) {
        Boolean exist = existQcmWithId(idQcm);

        if(exist == true){
            Qcm existQcm = getQcmWithId(idQcm);
            Qcm updateQcm = updateQcm(idQcm, existQcm);
            return updateQcm;
        }
        else {
            ContentValues values = new ContentValues();
            values.put(QcmSQLiteOpenHelper.COLUMN_NAME_QCM, nameQcm);
            values.put(QcmSQLiteOpenHelper.COLUMN_DATE_START, dateStart);
            values.put(QcmSQLiteOpenHelper.COLUMN_DATE_END, dateEnd);
            values.put(QcmSQLiteOpenHelper.COLUMN_IS_ACTIVE, isActive);
            values.put(QcmSQLiteOpenHelper.COLUMN_ID_TYPE, idType);

            long insertId = database.insert(QcmSQLiteOpenHelper.TABLE_QCM, null,
                    values);
            Cursor cursor = database.query(QcmSQLiteOpenHelper.TABLE_QCM,
                    allColumns, QcmSQLiteOpenHelper.COLUMN_ID + " = " + insertId, null,
                    null, null, null);
            cursor.moveToFirst();
            Qcm newQcm = cursorToQcm(cursor);
            cursor.close();
            return newQcm;
        }
    }

    public Qcm updateQcm(long id, Qcm qcm){
        ContentValues values = new ContentValues();

        values.put(QcmSQLiteOpenHelper.COLUMN_NAME_QCM, qcm.getNameQcm());
        values.put(QcmSQLiteOpenHelper.COLUMN_DATE_START, qcm.getDateStart());
        values.put(QcmSQLiteOpenHelper.COLUMN_DATE_END, qcm.getDateEnd());
        values.put(QcmSQLiteOpenHelper.COLUMN_IS_ACTIVE, qcm.getIsActive());
        values.put(QcmSQLiteOpenHelper.COLUMN_ID_TYPE, qcm.getId_type());

        database.update(QcmSQLiteOpenHelper.TABLE_QCM, values, QcmSQLiteOpenHelper.COLUMN_ID + " = " + qcm.getId(), null);

        return getQcmWithId(qcm.getId());
    }

    public void deleteQcm(Qcm qcm) {
        long id = qcm.getId();
        System.out.println("Contact deleted with id: " + id);
        database.delete(QcmSQLiteOpenHelper.TABLE_QCM, QcmSQLiteOpenHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Qcm> getAllQcm() {
        List<Qcm> contacts = new ArrayList<Qcm>();

        Cursor cursor = database.query(QcmSQLiteOpenHelper.TABLE_QCM,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Qcm qcm = cursorToQcm(cursor);
            contacts.add(qcm);
            cursor.moveToNext();
        }

        cursor.close();
        return contacts;
    }

    public Qcm getQcmWithId(Long id){
        Cursor c = database.query(QcmSQLiteOpenHelper.TABLE_QCM, allColumns, QcmSQLiteOpenHelper.COLUMN_ID + " = \"" + id +"\"", null, null, null, null);
        c.moveToFirst();
        Qcm contact = cursorToQcm(c);
        c.close();
        return contact;
    }

    public Boolean existQcmWithId(Long id){
        Cursor c = database.query(QcmSQLiteOpenHelper.TABLE_QCM, allColumns, QcmSQLiteOpenHelper.COLUMN_ID + " = \"" + id +"\"", null, null, null, null);
        if(c.getCount()>0){
            c.close();
            return true;
        }
        else {
            c.close();
            return false;
        }
    }

    private Qcm cursorToQcm(Cursor cursor) {
        Qcm qcm = new Qcm();
        qcm.setId(cursor.getLong(0));
        qcm.setNameQcm(cursor.getString(1));
        qcm.setDateStart(cursor.getString(2));
        qcm.setDateEnd(cursor.getString(3));
        //qcm.setIsActive(cursor.getString(4).equals("1"));
        qcm.setId_type(cursor.getInt(4));
        return qcm;
    }
}
