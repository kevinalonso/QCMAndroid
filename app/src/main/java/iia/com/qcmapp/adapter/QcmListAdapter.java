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
 * This class is using to completed list
 */
public class QcmListAdapter extends ArrayAdapter<Qcm> {

    private List<Qcm> qcmList;
    private Context context;

    /**
     *
     * @param ctx is the context your applicatin
     * @param resourceId is the selected item
     * @param qcm element from the list
     */
    public QcmListAdapter ( Context ctx, int resourceId, List<Qcm> qcm) {
        super( ctx, resourceId, qcm);
        this.qcmList = qcm;
        this.context = ctx;
    }

    /**
     *
     * @param position get position elment in list
     * @param convertView
     * @param parent
     * @return elment string to list her is name to diplay in list.
     */
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
