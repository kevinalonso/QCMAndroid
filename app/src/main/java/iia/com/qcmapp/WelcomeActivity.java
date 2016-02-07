package iia.com.qcmapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import iia.com.qcmapp.adapter.QcmListAdapter;
import iia.com.qcmapp.backtask.BackTask;
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

        BackTask backtask=new BackTask(this);
        backtask.execute();
        //refreshList();
    }

    public void refreshList(){
        List<Qcm> qcmList = recupContact();

        /*ArrayAdapter<Qcm> adapter = new ArrayAdapter<Qcm>(this,
                android.R.layout.activity_list_item, qcmList);*/


        /*ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.row_list_qcm, R.id.txtVNameQcm, qcmList);*/

        lv.setAdapter(new QcmListAdapter(this, R.layout.row_list_qcm, qcmList));

    }

    public List<Qcm> recupContact() {
        datasource = new QcmDataSource(this);
        datasource.open();

        List<Qcm> values = datasource.getAllQcm();


        return values;

    }
}
