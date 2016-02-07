package iia.com.qcmapp.entity;


/**
 * Created by kevin-pc on 27/01/2016.
 */
public class Qcm {

    /**
     * Qcm id
     */
    protected long id;
    /**
     * Qcm name
     */
    protected String nameQcm;
    /**
     * Qcm date start
     */
    protected String dateStart;
    /**
     * Qcm date end
     */
    protected String dateEnd;
    /**
     * Qcm if active qcm
     */
    protected boolean isActive;

    protected int id_type;

    public boolean isActive() {
        return isActive;
    }

    //region Getter and Setter

    /**
     * get id Qcm
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * set id Qcm
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * get id Qcm
     * @return id
     */
    public String getNameQcm() {
        return nameQcm;
    }

    /**
     * set name Qcm
     * @param nameQcm
     */
    public void setNameQcm(String nameQcm) {
        this.nameQcm = nameQcm;
    }

    /**
     * get date start Qcm
     * @return dateStart
     */
    public String getDateStart() {
        return dateStart;
    }

    /**
     * set date start Qcm
     * @param dateStart
     */
    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    /**
     * get date end Qcm
     * @return dateEnd
     */
    public String getDateEnd() {
        return dateEnd;
    }

    /**
     * set date end Qcm
     * @param dateEnd
     */
    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    /**
     * get if Qcm is active
     * @return bool
     */
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * set is active Qcm
     * @param isActive
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getId_type() {
        return id_type;
    }

    public void setId_type(int id_type) {
        this.id_type = id_type;
    }
    //endregion
}
