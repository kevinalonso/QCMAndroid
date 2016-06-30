package iia.com.qcmapp.backtask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

import iia.com.qcmapp.R;
import iia.com.qcmapp.WelcomeActivity;
import iia.com.qcmapp.crud.QcmDataSource;
import iia.com.qcmapp.entity.Qcm;
import iia.com.qcmapp.webservice.WebService;

/**
 * Created by kevin-pc on 31/01/2016.
 */
public class BackTask extends AsyncTask<Void, Integer, Void> {

    private Context context;

    private static final String URL_QCM = "http://192.168.1.14/app_dev.php/api/all/qcm";

    private static final String TAG_QCM = "qcms";
    private static final String TAG_ID = "id";
    private static final String TAG_NAMEQCM = "nameQcm";
    private static final String TAG_DATESTART = "dateStart";
    private static final String TAG_DATEEND = "dateEnd";
    private static final String TAG_ISACTIVE = "isActive";

    //static JSONObject jObj = null;

    public BackTask(Context context) {

        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(context, context.getString(R.string.startasynch), Toast.LENGTH_SHORT).show();
    }

    @Override
    /**
     * Call the web service to get data into json flow
     */
    protected Void doInBackground(Void... params) {
        WebService webService = new WebService();
        String textQcm = webService.readFlow(URL_QCM);//readQcm();
        JSONObject jsonQcm = webService.parseFlow(textQcm);//parseQcm(textQcm);
        try {

            Boolean result  = resQcm(jsonQcm);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values){
    }

    @Override
    /**
     * refresh list in WelcomeActivity
     */
    protected void onPostExecute(Void result) {
        ((WelcomeActivity) context).refreshList();
    }

    /**
     * Check if the data from json exist in databases else adding in databases
     * @param jsonContact json from webservice
     * @return if the qcm exist in database
     * @throws SQLException
     */
    public Boolean resQcm(JSONObject jsonContact) throws SQLException {
        QcmDataSource datasource = new QcmDataSource(context);
        datasource.open();

        boolean action = false;

        try {
            JSONArray qcmJson = jsonContact.getJSONArray(TAG_QCM);

            for(int i = 0; i < qcmJson.length(); i++){
                Qcm qcm = null;
                JSONObject c = qcmJson.getJSONObject(i);

                Long id = c.getLong(TAG_ID);
                String nameQcm = c.getString(TAG_NAMEQCM);
                String DateStart = c.getString(TAG_DATESTART);
                String DateEnd = c.getString(TAG_DATEEND);

                qcm = datasource.createQcm(nameQcm,DateStart,DateEnd,true,0,id);
            }
            datasource.close();
            action = true;

        } catch (JSONException e) {
            e.printStackTrace();
            datasource.close();
        }
        return action;
    }
}

