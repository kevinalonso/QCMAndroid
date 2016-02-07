package iia.com.qcmapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import iia.com.qcmapp.R;
import iia.com.qcmapp.entity.Qcm;

/**
 * Created by kevin-pc on 02/02/2016.
 */
public class QcmListAdapter extends ArrayAdapter<Qcm> {

    private List<Qcm> qcmList;
    private Context context;

    public QcmListAdapter ( Context ctx, int resourceId, List<Qcm> qcm) {
        super( ctx, resourceId, qcm);
        this.qcmList = qcm;
        this.context = ctx;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_list_qcm, parent, false);
        }

        // Now we can fill the layout with the right values
        TextView tvName = (TextView) convertView.findViewById(R.id.txtVNameQcm);

        Qcm qcm = this.qcmList.get(position);

        tvName.setText(qcm.getNameQcm());

       return convertView;
    }
}
