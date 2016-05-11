package iia.com.qcmapp.post;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.util.ArrayList;
import java.util.List;

import iia.com.qcmapp.entity.UserAnswer;

/**
 * Created by kevin-pc on 04/04/2016.
 */
public class PostWebService extends AsyncTask<String, Void, String> {

    public List<UserAnswer> postList;

    @Override
    protected String doInBackground(String... params) {
        /**
         * Add the answer to the qcm in database on the webservice
         */
        HttpResponse response = null;
        try
        {
            /**
             * the postList is the result of answer from the QuestionActivity
             * but this list add elment in QuestionFragment
             */
            for(UserAnswer item : postList) {

                HttpClient httpClient = new DefaultHttpClient();
                HttpContext localContext = new BasicHttpContext();
                HttpPost httpPost = new HttpPost("http://192.168.1.14/app_dev.php/api/answers/users/new");

                List<NameValuePair> param = new ArrayList<NameValuePair>(3);
                param.add(new BasicNameValuePair("idQuestion",Integer.toString(item.getIdQuestion())));
                param.add(new BasicNameValuePair("idAnswer", Integer.toString(item.getIdAnswer())));
                param.add(new BasicNameValuePair("idQcm", Integer.toString(item.getIdQcm())));
                httpPost.setEntity(new UrlEncodedFormEntity(param));
                /**
                 * Send data to the webservice
                 */
                response = httpClient.execute(httpPost, localContext);
                response = null;
            }

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.getMessage();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.getMessage();
        }
        return null;
    }
}
