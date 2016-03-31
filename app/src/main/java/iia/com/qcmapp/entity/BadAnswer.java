package iia.com.qcmapp.entity;

/**
 * Created by kevin-pc on 30/03/2016.
 */
public class BadAnswer {

    private long id;

    private String badAnswerQuestion;

    private long idQuestion;

    //region GETTER AND SETTER
    public String getBadAnswerQuestion() {
        return badAnswerQuestion;
    }

    public void setBadAnswerQuestion(String badAnswerQuestion) {
        this.badAnswerQuestion = badAnswerQuestion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(long idQuestion) {
        this.idQuestion = idQuestion;
    }
    //endregion
}
