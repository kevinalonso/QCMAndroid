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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import iia.com.qcmapp.entity.UserAnswer;

/**
 * Created by kevin-pc on 04/04/2016.
 */
public class PostWebService extends AsyncTask<String, Void, String> {

    public List<UserAnswer> postList;
    //help to method posthttp://stackoverflow.com/questions/23060482/how-get-data-in-symfony2-from-androids-post-method
    /*public void postData(List<UserAnswer> postList) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost("http://192.168.1.14/app_dev.php/api/new_answer_user");
        HttpResponse response = null;
        try
        {
            for(UserAnswer item : postList) {
                List<NameValuePair> params = new ArrayList<NameValuePair>(3);
                params.add(new BasicNameValuePair("idQuestion",Integer.toString(item.getIdQuestion())));
                params.add(new BasicNameValuePair("idAnswer", Integer.toString(item.getIdAnswer())));
                params.add(new BasicNameValuePair("idQcm", Integer.toString(item.getIdQcm())));
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                response = httpClient.execute(httpPost, localContext);
            }

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.getMessage();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.getMessage();
        }
    }*/

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    @Override
    protected String doInBackground(String... params) {
        /*HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost("http://192.168.1.14/app_dev.php/api/answers/users/new");*/
        HttpResponse response = null;
        try
        {
            for(UserAnswer item : postList) {

                HttpClient httpClient = new DefaultHttpClient();
                HttpContext localContext = new BasicHttpContext();
                HttpPost httpPost = new HttpPost("http://192.168.1.14/app_dev.php/api/answers/users/new");

                List<NameValuePair> param = new ArrayList<NameValuePair>(3);
                param.add(new BasicNameValuePair("idQuestion",Integer.toString(item.getIdQuestion())));
                param.add(new BasicNameValuePair("idAnswer", Integer.toString(item.getIdAnswer())));
                param.add(new BasicNameValuePair("idQcm", Integer.toString(item.getIdQcm())));
                httpPost.setEntity(new UrlEncodedFormEntity(param));
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
