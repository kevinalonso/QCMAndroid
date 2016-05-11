package iia.com.qcmapp;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by kevin-pc on 11/05/2016.
 */
public class WebServiceTest {
    @Test
    public void testSomething() throws Exception {

        int statusCode = 0;

        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://192.168.100.169/app_dev.php/api/all/qcm");
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            statusCode = statusLine.getStatusCode();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        }

        assertEquals(200, statusCode);
    }
}
