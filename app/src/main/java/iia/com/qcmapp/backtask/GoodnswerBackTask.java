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
import iia.com.qcmapp.crud.GoodAnswerDataSource;
import iia.com.qcmapp.entity.GoodAnswer;

/**
 * Created by kevin-pc on 06/03/2016.
 */
public class GoodnswerBackTask extends AsyncTask<Void, Integer, Void> {

    private Context context;

    private static final String URL_GOODANSWER = "http://192.168.1.14/app_dev.php/api/all/good/answer";

    private static final String TAG_GOODANSWER = "goodAnswers";
    private static final String TAG_ID = "id";
    private static final String TAG_TEXTANSWER = "answerQuestion";
    private static final String TAG_IDQUESTION = "idQuestion";

    static JSONObject jObj = null;

    public String readGoodAnswer() {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL_GOODANSWER);
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

    public GoodnswerBackTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(context, "Début du traitement asynchrone", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        String textGodAnswer = readGoodAnswer();
        JSONObject jsonGoodAnswer = parseGoodAnswer(textGodAnswer);
        Boolean result  = recGoodAnswer(jsonGoodAnswer);
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values){
    }

    @Override
    protected void onPostExecute(Void result) {
        //((WelcomeActivity) context).refreshList();
       Toast.makeText(context, "Le traitement asynchrone est terminé", Toast.LENGTH_SHORT).show();

    }

    public JSONObject parseGoodAnswer(String textAnswer){
        try {
            jObj = new JSONObject(textAnswer);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jObj;
    }

    public Boolean recGoodAnswer(JSONObject jsonGoodAnswer) {
        GoodAnswerDataSource datasource = new GoodAnswerDataSource(context);
        datasource.open();

        try {
            JSONArray goodAnswerJson = jsonGoodAnswer.getJSONArray(TAG_GOODANSWER);

            for (int i = 0; i < goodAnswerJson.length(); i++) {
                GoodAnswer goodAnswer = null;
                JSONObject c = goodAnswerJson.getJSONObject(i);

                Long id = c.getLong(TAG_ID);
                String textGoodAnswer = c.getString(TAG_TEXTANSWER);
                Long idQuestion = c.getLong(TAG_IDQUESTION);

                goodAnswer = datasource.createGoodAnwser(textGoodAnswer,id,idQuestion);
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
