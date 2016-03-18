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
import iia.com.qcmapp.crud.QuestionDataSource;
import iia.com.qcmapp.entity.Question;

/**
 * Created by kevin-pc on 07/02/2016.
 */
public class QuestionBackTask extends AsyncTask<Void, Integer, Void> {

    private Context context;

    private static final String URL_QUESTION = "http://192.168.1.14/app_dev.php/api/all/question";

    private static final String TAG_QUESTION = "questions";
    private static final String TAG_ID = "idQuestion";
    private static final String TAG_TEXTQUESTION = "textQuestion";
    private static final String TAG_IDQCM = "idQcm";

    static JSONObject jObj = null;

    public String readQuestion() {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL_QUESTION);
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

    public QuestionBackTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(context, "Début du traitement asynchrone", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        String textQuestion = readQuestion();
        JSONObject jsonQuestion = parseQuestion(textQuestion);
        Boolean result  = recQuestion(jsonQuestion);
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

    public JSONObject parseQuestion(String question){
        try {
            jObj = new JSONObject(question);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jObj;
    }

    public Boolean recQuestion(JSONObject jsonContact) {
        QuestionDataSource datasource = new QuestionDataSource(context);
        datasource.open();

        try {
            JSONArray qcmJson = jsonContact.getJSONArray(TAG_QUESTION);

            for (int i = 0; i < qcmJson.length(); i++) {
                Question question = null;
                JSONObject c = qcmJson.getJSONObject(i);

                Long id = c.getLong(TAG_ID);
                String textQuestion = c.getString(TAG_TEXTQUESTION);
                Long idQcm = c.getLong(TAG_IDQCM);
                //boolean isActive = c.getString(TAG_ISACTIVE).equals("true");

                question = datasource.createQuestion(textQuestion,id,idQcm);
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
