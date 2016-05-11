package iia.com.qcmapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import iia.com.qcmapp.adapter.QcmListAdapter;
import iia.com.qcmapp.backtask.BackTask;
import iia.com.qcmapp.backtask.BadAnswerBackTask;
import iia.com.qcmapp.backtask.GoodnswerBackTask;
import iia.com.qcmapp.backtask.QuestionBackTask;
import iia.com.qcmapp.crud.QcmDataSource;
import iia.com.qcmapp.entity.Qcm;

public class WelcomeActivity extends Activity {

    private QcmDataSource datasource;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

         lv = (ListView)findViewById(R.id.listQcm);

        /**
         * Get the Qcm from the webservice
         */
        BackTask backtask = new BackTask(this);
        backtask.execute();

        /**
         * Get the Question from the webservice
         */
        QuestionBackTask questionBackTask = new QuestionBackTask(this);
        questionBackTask.execute();

        /**
         * Get the GoodAnswer from the webservice
         */
        GoodnswerBackTask goodnswerBackTask = new GoodnswerBackTask(this);
        goodnswerBackTask.execute();

        /**
         * Get the BadAnswer from the webservice
         */
        BadAnswerBackTask badAnswerBackTask = new BadAnswerBackTask(this);
        badAnswerBackTask.execute();

        //refreshList();
    }

    public void refreshList(){
        List<Qcm> qcmList = recupQcm();

        lv.setAdapter(new QcmListAdapter(this, R.layout.row_list_qcm, qcmList));

        /**
         * get id from qcm selected
         */
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<Qcm> resQcm = new ArrayList<Qcm>();
                resQcm = recupQcm();

                long itemID = resQcm.get(position).getId();
                Intent intent = new Intent(WelcomeActivity.this, QuestionActivity.class);
                long seed = System.nanoTime();
                intent.putExtra("id", itemID);
                startActivity(intent);

            }
        });

    }

    public List<Qcm> recupQcm(){
        datasource = new QcmDataSource(this);
        datasource.open();

        List<Qcm> values = datasource.getAllQcm();


        return values;

    }
}
