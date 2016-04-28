package iia.com.qcmapp.entity;

/**
 * Created by kevin-pc on 04/04/2016.
 */
public class UserAnswer {

    private int idQuestion;
    private int idAnswer;
    private int idQcm;

    //region GETTER AND SETTER
    public int getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(int idAnswer) {
        this.idAnswer = idAnswer;
    }

    public int getIdQcm() {
        return idQcm;
    }

    public void setIdQcm(int idQcm) {
        this.idQcm = idQcm;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }
    //endregion
}
