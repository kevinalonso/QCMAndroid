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
     * answer text
     */
    private String answerQuestion;
    /**
     * answer from question
     */
    private long idQuestion;

    //region GETTER & SETTER

    /**
     * get text GoodAnswer
     * @return
     */
    public String getAnswerQuestion() {
        return answerQuestion;
    }

    /**
     * set text GoodAnswer
     * @param answerQuestion
     */
    public void setAnswerQuestion(String answerQuestion) {
        this.answerQuestion = answerQuestion;
    }

    /**
     * get id GoodAnswer
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * set id GoodAnswer
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * get idQuestion
     * @return
     */
    public long getIdQuestion() {
        return idQuestion;
    }

    /**
     * set idQuestion
     * @param idQuestion
     */
    public void setIdQuestion(long idQuestion) {
        this.idQuestion = idQuestion;
    }

    //endregion
}
