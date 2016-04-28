package iia.com.qcmapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import iia.com.qcmapp.constants.Constants;
import iia.com.qcmapp.entity.UserAnswer;
import iia.com.qcmapp.post.PostWebService;

public class QuestionActivity extends AppCompatActivity implements QuestionFragment.OnFragmentInteractionListener{

    private static int i = 0;
    public List<UserAnswer> userAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        userAnswers = new ArrayList<UserAnswer>();
        //QuestionDataSource questionDataSource = new QuestionDataSource(getApplicationContext());
        //questionDataSource.open();

        //questionList = questionDataSource.getQuestionWithIdList(QuestionFragment.ID_QCM_FROM_LIST);
        //Collections.shuffle(questionList);

        Button btnNext = (Button)findViewById(R.id.buttonNext);
        Button btnPrev = (Button)findViewById(R.id.buttonPrev);


        //region BUTTON NEXT
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Call element from fragment or method
                FragmentManager fm = getFragmentManager();
                QuestionFragment frag = (QuestionFragment)fm.findFragmentById(R.id.frgmentQuestion);

                //List wih answer user
                userAnswers.add(frag.getAnswerUser());
                //Constants.resList = new ArrayList<UserAnswer>();
                if(Constants.resList == null){
                    Constants.inst();
                    Constants.listAdd(frag.getAnswerUser());
                }else {
                    Constants.listAdd(frag.getAnswerUser());
                }
                //Initialise fragment and refresh their values
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                QuestionFragment questionFragment = new QuestionFragment();
                i++;
                questionFragment.POINTOR_I = i;
                fragmentTransaction.replace(R.id.frgmentQuestion,questionFragment);
                fragmentTransaction.commit();

            }
        });
        //endregion

        //region BUTTON PREVIOUS
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i != 0){
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    QuestionFragment questionFragment = new QuestionFragment();
                    i--;
                    questionFragment.POINTOR_I = i;
                    fragmentTransaction.replace(R.id.frgmentQuestion,questionFragment);
                    fragmentTransaction.commit();
                }else{
                    Toast.makeText(getApplicationContext(),R.string.infoprevious,Toast.LENGTH_SHORT).show();
                }
            }
        });
        //endregion
    }

    public void addAnswerToWebService(){
        PostWebService postWebService = new PostWebService();
        //postWebService.postData(Constants.resList);
        postWebService.postList = new ArrayList<UserAnswer>();
        postWebService.postList = Constants.resList;
        postWebService.execute();
        //Toast.makeText(this,R.string.infosendqcm,Toast.LENGTH_LONG).show();

        /*Intent intent = new Intent(QuestionActivity.this, WelcomeActivity.class);
        startActivity(intent);*/

    }




    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
