package iia.com.qcmapp.backtask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

import iia.com.qcmapp.R;
import iia.com.qcmapp.crud.BadAnswerDataSource;
import iia.com.qcmapp.entity.BadAnswer;
import iia.com.qcmapp.webservice.WebService;

/**
 * Created by kevin-pc on 30/03/2016.
 */
public class BadAnswerBackTask extends AsyncTask<Void, Integer, Void> {

    private Context context;

    private static final String URL_BAD_ANSWER = "http://192.168.1.14/app_dev.php/api/all/bad/answer";

    private static final String TAG_BADANSWER = "badAnswers";
    private static final String TAG_ID = "id";
    private static final String TAG_NAMEBADANSWER = "badAnswerQuestion";
    private static final String TAG_IDQUESTION = "idQuestion";

    public BadAnswerBackTask(Context context) {

        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(context, context.getString(R.string.startasynch), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        WebService webService = new WebService();
        String textbadAnswer = webService.readFlow(URL_BAD_ANSWER);//readQcm();
        JSONObject jsonBadAnswer = webService.parseFlow(textbadAnswer);//parseQcm(textQcm);
        try {

            Boolean result  = resBadAnswer(jsonBadAnswer);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values){
    }

    @Override
    protected void onPostExecute(Void result) {
        Toast.makeText(context, context.getString(R.string.endasynch), Toast.LENGTH_SHORT).show();
    }

    public Boolean resBadAnswer(JSONObject jsonBadAnswer) throws SQLException {
        BadAnswerDataSource datasource = new BadAnswerDataSource(context);
        datasource.open();

        boolean action = false;

        try {
            JSONArray badAnswerJson = jsonBadAnswer.getJSONArray(TAG_BADANSWER);

            for(int i = 0; i < badAnswerJson.length(); i++){
                BadAnswer badAnswer = null;
                JSONObject c = badAnswerJson.getJSONObject(i);

                Long id = c.getLong(TAG_ID);
                String textBadAnswer = c.getString(TAG_NAMEBADANSWER);
                Long idQuestion = c.getLong(TAG_IDQUESTION);

                badAnswer = datasource.createBadAnwser(textBadAnswer, id, idQuestion);
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
