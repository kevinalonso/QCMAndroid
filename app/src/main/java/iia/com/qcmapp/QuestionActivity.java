package iia.com.qcmapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import iia.com.qcmapp.crud.QuestionDataSource;
import iia.com.qcmapp.entity.Question;

public class QuestionActivity extends AppCompatActivity implements QuestionFragment.OnFragmentInteractionListener{

    private static int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        List<Question> questionList = new ArrayList<Question>();
        QuestionDataSource questionDataSource = new QuestionDataSource(getApplicationContext());
        questionDataSource.open();

        questionList = questionDataSource.getQuestionWithIdList(QuestionFragment.ID_QCM_FROM_LIST);
        Collections.shuffle(questionList);
        Button btnNext = (Button)findViewById(R.id.buttonNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                QuestionFragment questionFragment = new QuestionFragment();
                i++;
                questionFragment.POINTOR_I = i;
                fragmentTransaction.replace(R.id.frgmentQuestion,questionFragment);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
