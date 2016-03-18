package iia.com.qcmapp.entity;

/**
 * Created by kevin-pc on 06/03/2016.
 */
public class GoodAnswer {
    /**
     * id good answer
     */
    private long id;
    /**
     * answer for a question
     */
    private String answerQuestion;
    /**
     * answer from question
     */
    private long idQuestion;

    //region GETTER & SETTER

    /**
     *
     * @return
     */
    public String getAnswerQuestion() {
        return answerQuestion;
    }

    /**
     *
     * @param answerQuestion
     */
    public void setAnswerQuestion(String answerQuestion) {
        this.answerQuestion = answerQuestion;
    }

    /**
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public long getIdQuestion() {
        return idQuestion;
    }

    /**
     *
     * @param idQuestion
     */
    public void setIdQuestion(long idQuestion) {
        this.idQuestion = idQuestion;
    }

    //endregion
}
