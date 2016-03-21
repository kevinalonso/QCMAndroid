package iia.com.qcmapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import iia.com.qcmapp.constants.Constants;
import iia.com.qcmapp.crud.GoodAnswerDataSource;
import iia.com.qcmapp.crud.QuestionDataSource;
import iia.com.qcmapp.entity.Question;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    /**
     * Id from list (this id from qcm selected and is use to get the mapped question.
     */
    public static long ID_QCM_FROM_LIST;
    public static int POINTOR_I;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mId;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionFragment newInstance(long idQcm, String param1,String param2) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putLong(String.valueOf(ID_QCM_FROM_LIST), idQcm);
        fragment.setArguments(args);
        return fragment;
    }

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mId = getArguments().getString(String.valueOf(ID_QCM_FROM_LIST));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_question, container, false);

        //Variable
        TextView questionTextView = (TextView)v.findViewById(R.id.textViewQuestion);
        CheckBox answerText = (CheckBox)v.findViewById(R.id.chkAnswer2);

        Intent intent = getActivity().getIntent();
        long resIntent = intent.getLongExtra("id", ID_QCM_FROM_LIST);

        QuestionDataSource questionDataSource = new QuestionDataSource(getActivity());
        List<Question> questionList = new ArrayList<Question>();
        questionDataSource.open();

        GoodAnswerDataSource goodAnswerDataSource = new GoodAnswerDataSource(getActivity());

        questionList = questionDataSource.getQuestionWithIdList(resIntent);
        if(POINTOR_I == questionList.size()){
            questionTextView.setText("");
            questionTextView.setText(Constants.END_QCM);
        }else {
            questionTextView.setText(questionList.get(POINTOR_I).getTextQuestion());
            long idQuestion = questionList.get(POINTOR_I).getId();
            answerText.setText(goodAnswerDataSource.getGoodAnswerWithId(idQuestion).getAnswerQuestion());

        }

        return  v;
    }

    //region TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    //endregion
}
