package iia.com.qcmapp;


import android.content.Context;
import android.test.mock.MockContext;

import com.loopj.android.http.HttpGet;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import iia.com.qcmapp.backtask.UserBackTask;

import static org.junit.Assert.assertEquals;

/**
 * Created by kevin-pc on 11/05/2016.
 */
public class WebServiceTest {

    public String HTTP_URL = "http://192.168.100.169";
    /**
     * Code 200 http
     */
    private static final String CODE_200 = "HTTP/1.1 200 OK";

    public HttpResponse getResponse(String url) throws Exception{
        HttpClient httpclient = HttpClientBuilder.create().build();

        HttpGet httpGet = new HttpGet(url);

        HttpResponse httpResponse = httpclient.execute(httpGet);

        return  httpResponse;
    }

    /**
     * Test url json to get Qcm
     * @throws Exception
     */
    @Test
    public void myWebService() throws  Error {
        Context ctx;
        ctx = new MockContext();
        UserBackTask userBackTask = new UserBackTask(ctx);
        try {
            userBackTask.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void webServiceQcm() throws Exception {

        String url = HTTP_URL+"/app_dev.php/api/all/qcm";

        assertEquals(CODE_200, this.getResponse(url).getStatusLine().toString());
    }
    /**
     * Test url json to get one Qcm
     * @throws Exception
     */
    @Test
    public void webServiceQcmId() throws Exception {

        String url = HTTP_URL+"/app_dev.php/api/qcms/1";

        assertEquals(CODE_200, this.getResponse(url).getStatusLine().toString());
    }
    /**
     * Test url json to get Question
     * @throws Exception
     */
    @Test
    public void webServiceQuestion() throws Exception {
        String url = HTTP_URL+"/app_dev.php/api/all/question";

        assertEquals(CODE_200, this.getResponse(url).getStatusLine().toString());
    }

    /**
     * Test url json to get GoodAnswer
     * @throws Exception
     */
    @Test
    public void webServiceGoodAnswer() throws Exception {
        String url = HTTP_URL+"/app_dev.php/api/all/good/answer";

        assertEquals(CODE_200, this.getResponse(url).getStatusLine().toString());
    }

    /**
     * Test url json to get BadAnswer
     * @throws Exception
     */
    @Test
    public void webServiceBadAnswer() throws Exception {
        String url = HTTP_URL+"/app_dev.php/api/all/bad/answer";

        assertEquals(CODE_200, this.getResponse(url).getStatusLine().toString());
    }
}
