package iia.com.qcmapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import iia.com.qcmapp.backtask.UserBackTask;
import iia.com.qcmapp.entity.User;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_USER = "users";
    private static final String TAG_ID = "id";
    private static final String TAG_LOGIN = "login";
    private static final String TAG_PASSWORD = "password";
    private EditText loginText;
    private EditText passText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * My element graphic
         */
        Button btnConn = (Button)findViewById(R.id.btnconnction);
        loginText = (EditText)findViewById(R.id.edtlogin);
        passText = (EditText)findViewById(R.id.edtpwd);

        /**
         * Go to the welcome page
         */
        btnConn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login = loginText.getText().toString();
                String password = passText.getText().toString();
                boolean exist = false;

                if (!login.isEmpty() && !password.isEmpty()) {

                    //Get result from web service to add element in list
                    UserBackTask userBackTask = new UserBackTask(getApplicationContext());

                    //Waiting value return from asynckTask
                    JSONObject userObject = null;
                    try {
                        userObject = userBackTask.execute().get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    if (userObject != null) {
                        try {
                            JSONArray userJson = userObject.getJSONArray(TAG_USER);

                            for (int i = 0; i < userJson.length(); i++) {
                                User user = new User();
                                JSONObject userJsonObject = userJson.getJSONObject(i);

                                user.setId(userJsonObject.getInt(TAG_ID));
                                user.setLogin(userJsonObject.getString(TAG_LOGIN));
                                user.setPassword(userJsonObject.getString(TAG_PASSWORD));

                                if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                                    exist = true;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //If user exist
                        if (exist == true) {
                            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.notexist), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.emptyloginpass), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
