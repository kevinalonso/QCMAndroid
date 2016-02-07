package iia.com.qcmapp.backtask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import iia.com.qcmapp.MainActivity;
import iia.com.qcmapp.WelcomeActivity;
import iia.com.qcmapp.crud.QcmDataSource;
import iia.com.qcmapp.entity.Qcm;

/**
 * Created by kevin-pc on 31/01/2016.
 */
public class BackTask extends AsyncTask<Void, Integer, Void> {

    private static final String TAG_QCM = "qcm";
    private static final String TAG_ID = "id";
    private static final String TAG_NAMEQCM = "nameQcm";
    private static final String TAG_DATESTART = "dateStart";
    private static final String TAG_DATEEND = "dateEnd";
    private static final String TAG_ISACTIVE = "isActive";

    static JSONObject jObj = null;

    public String readContactFeed() {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://192.168.1.14/app_dev.php/api/all/qcm");
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line + "\n");
                }
            } else {
                Log.e(MainActivity.class.toString(), "Failed to download file");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    Context context;
    public BackTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(context, "Début du traitement asynchrone", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        String textQcm = readContactFeed();
        JSONObject jsonQcm = parseQcm(textQcm);
        Boolean result  = recContact(jsonQcm);
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values){
    }

    @Override
    protected void onPostExecute(Void result) {
        ((WelcomeActivity) context).refreshList();
        Toast.makeText(context, "Le traitement asynchrone est terminé", Toast.LENGTH_SHORT).show();

    }

    public JSONObject parseQcm(String qcm){
        try {
            jObj = new JSONObject(qcm);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jObj;
    }

    public Boolean recContact(JSONObject jsonContact) {
        QcmDataSource datasource = new QcmDataSource(context);
        datasource.open();

        try {
            JSONArray qcmJson = jsonContact.getJSONArray(TAG_QCM);

            for(int i = 0; i < qcmJson.length(); i++){
                Qcm qcm = null;
                JSONObject c = qcmJson.getJSONObject(i);

                Long id = c.getLong(TAG_ID);
                String nameQcm = c.getString(TAG_NAMEQCM);
                String DateStart = c.getString(TAG_DATESTART);
                String DateEnd = c.getString(TAG_DATEEND);
                //boolean isActive = c.getString(TAG_ISACTIVE).equals("true");

                qcm = datasource.createQcm(nameQcm,DateStart,DateEnd,true,0,id);
            }
            datasource.close();
            return true;

        } catch (JSONException e) {
            e.printStackTrace();
            datasource.close();
            return false;
        }

    }
}

