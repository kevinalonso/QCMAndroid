package iia.com.qcmapp.backtask;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import iia.com.qcmapp.webservice.WebService;

/**
 * Created by kevin-pc on 06/06/2016.
 */
public class UserBackTask extends AsyncTask<Void, Integer, JSONObject> {

    private Context context;
    public JSONObject object;


    public UserBackTask(Context context) {

        this.context = context;
    }
    @Override
    protected JSONObject doInBackground(Void... params) {
        WebService webService = new WebService();
        String textUser = webService.readFlow("http://192.168.100.169/app_dev.php/api/all/users");
        JSONObject jsonUser = webService.parseFlow(textUser);
        //object = jsonUser;

        return jsonUser;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
    }
}
