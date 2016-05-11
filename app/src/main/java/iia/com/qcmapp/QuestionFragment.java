package iia.com.qcmapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import iia.com.qcmapp.constants.Constants;
import iia.com.qcmapp.crud.BadAnswerDataSource;
import iia.com.qcmapp.crud.GoodAnswerDataSource;
import iia.com.qcmapp.crud.QuestionDataSource;
import iia.com.qcmapp.entity.BadAnswer;
import iia.com.qcmapp.entity.Question;
import iia.com.qcmapp.entity.UserAnswer;


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
    private CheckBox answerText, badAnswerOne, badAnswerTwo;
    private long idQuestion, resIntent;

    /**
     * Id from list (this id from qcm selected and is use to get the mapped question.
     */
    public static long ID_QCM_FROM_LIST;

    /**
     * Get the number of tap to the button Next
     * It is using to get index in List of questions
     */
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

        View v = inflater.inflate(R.layout.fragment_question, container, false);

        //Fields
        Button btnSend = (Button)v.findViewById(R.id.btnSend);
        TextView questionTextView = (TextView)v.findViewById(R.id.textViewQuestion);
        answerText = (CheckBox)v.findViewById(R.id.chkAnswer2);
        badAnswerOne = (CheckBox)v.findViewById(R.id.chkAnswer1);
        badAnswerTwo = (CheckBox)v.findViewById(R.id.chkAnswer3);

        Intent intent = getActivity().getIntent();
        resIntent = intent.getLongExtra("id", ID_QCM_FROM_LIST);

        QuestionDataSource questionDataSource = new QuestionDataSource(getActivity());
        GoodAnswerDataSource goodAnswerDataSource = new GoodAnswerDataSource(getActivity());
        BadAnswerDataSource badAnswerDataSource = new BadAnswerDataSource(getActivity());

        //Open connection database
        questionDataSource.open();

        List<Question> questionList = new ArrayList<Question>();

        questionList = questionDataSource.getQuestionWithIdList(resIntent);

        goodAnswerDataSource.open();
        badAnswerDataSource.open();

        if(POINTOR_I == questionList.size()){
            //Visibl  = true to the button to send data
            btnSend.setVisibility(View.VISIBLE);
            //Replace text in view to display "end qcm"
            questionTextView.setText("");
            questionTextView.setText(Constants.END_QCM);

        }else {
            /**
             * Get question in graphic elment
             */
            questionTextView.setText(questionList.get(POINTOR_I).getTextQuestion());
            idQuestion = questionList.get(POINTOR_I).getId();
            answerText.setText(goodAnswerDataSource.getGoodAnswerWithId(idQuestion).getAnswerQuestion());

            List<BadAnswer> badAnswerlist = new ArrayList<BadAnswer>();
            badAnswerlist = badAnswerDataSource.getBadAnswerWithIdList(idQuestion);

            /**
             * Get answer bad or good in graphic element
             */
           for(int index = 0; index <badAnswerlist.size(); index++)
           {
               badAnswerOne.setText(badAnswerlist.get(0).getBadAnswerQuestion());
               badAnswerOne.setId((int)badAnswerlist.get(0).getId());

               badAnswerTwo.setText(badAnswerlist.get(1).getBadAnswerQuestion());
               index++;
           }
           //Close connection databases after execute query
            badAnswerDataSource.close();
            goodAnswerDataSource.close();

        }
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Button to post data from addAnswerToWebService() in QuestionActivity
                 */
                QuestionActivity questionActivity = new QuestionActivity();
                questionActivity.addAnswerToWebService();
            }
        });
        return  v;
    }

    /**
     * Get answer selected by the user
     * @return userAnswer
     */
    public UserAnswer getAnswerUser(){
        UserAnswer userAnswer = new UserAnswer();

        if(answerText.isChecked()) {
            userAnswer.setIdAnswer(answerText.getId());
            /*userAnswer.setIdQuestion((int) idQuestion);
            userAnswer.setIdQcm((int)resIntent);*/
        }
        if(badAnswerOne.isChecked()){
            userAnswer.setIdAnswer(badAnswerOne.getId());
            /*userAnswer.setIdQuestion((int) idQuestion);
            userAnswer.setIdQcm((int)resIntent);*/
        }
        if(badAnswerTwo.isChecked()){
            userAnswer.setIdAnswer(badAnswerTwo.getId());
            /*userAnswer.setIdQuestion((int) idQuestion);
            userAnswer.setIdQcm((int)resIntent);*/
        }
        userAnswer.setIdQuestion((int) idQuestion);
        userAnswer.setIdQcm((int)resIntent);
        return userAnswer;
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
