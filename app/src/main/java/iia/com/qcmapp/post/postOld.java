package iia.com.qcmapp.post;

/**
 * Created by kevin-pc on 12/04/2016.
 */
public class postOld {

    /*InputStream inputStream = null;
    String result = "";

    try {

        // Create HttpClient
        HttpClient httpclient = new DefaultHttpClient();

        //Make POST request to the given URL
        HttpPost httpPost = new HttpPost("http://192.168.1.14/app_dev.php/api/new_answer_user");

        for(UserAnswer item : postList){
            String json = "";

            // Build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("idQuestion",item.getIdQuestion());
            jsonObject.accumulate("idAnswer", item.getIdAnswer());
            jsonObject.accumulate("idQcm", item.getIdQcm());

            //Convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            //Set json to StringEntity
            StringEntity se = new StringEntity(json);

            //Set httpPost Entity
            httpPost.setEntity(se);

            //Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            //Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            //Eeceive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            //Convert inputstream to string
            if (inputStream != null){
                result = convertInputStreamToString(inputStream);
            }
            inputStream = null;
        }

    } catch (Exception e) {
        Log.d("InputStream", e.getLocalizedMessage());
    }*/
}
