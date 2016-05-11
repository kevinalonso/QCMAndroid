package iia.com.qcmapp.entity;

/**
 * Created by kevin-pc on 30/03/2016.
 */
public class BadAnswer {

    /**
     * id badAnswer
     */
    private long id;

    /**
     * badAnswer text
     */
    private String badAnswerQuestion;

    /**
     * idQuestion fk baAnswer
     */
    private long idQuestion;

    //region GETTER AND SETTER

    /**
     * get text BadAnswer
     * @return
     */
    public String getBadAnswerQuestion() {
        return badAnswerQuestion;
    }

    /**
     * get id BadAnswer
     * @param badAnswerQuestion
     */
    public void setBadAnswerQuestion(String badAnswerQuestion) {
        this.badAnswerQuestion = badAnswerQuestion;
    }

    /**
     * get id BadAnswer
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * set id BadAnswer
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * set idQuestion fk BadAnswer
     * @return
     */
    public long getIdQuestion() {
        return idQuestion;
    }

    /**
     * get idQuestion fk BadAnswer
     * @param idQuestion
     */
    public void setIdQuestion(long idQuestion) {
        this.idQuestion = idQuestion;
    }
    //endregion
}
