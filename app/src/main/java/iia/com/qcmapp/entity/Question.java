package iia.com.qcmapp.entity;

/**
 * Created by kevin-pc on 07/02/2016.
 */
public class Question {
    /**
     * Question id
     */
    private long id;
    /**
     * Question name
     */
    private String textQuestion;
    /**
     * Type of question
     */
    private long idType;
    /**
     * Foreygn key qcm's quetion
     */
    private long idQcm;

    //region GETTER and SETTER

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdQcm() {
        return idQcm;
    }

    public void setIdQcm(long idQcm) {
        this.idQcm = idQcm;
    }

    public long getIdType() {
        return idType;
    }

    public void setIdType(long idType) {
        this.idType = idType;
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public void setTextQuestion(String textQuestion) {
        this.textQuestion = textQuestion;
    }

    //endregion
}
