package iia.com.qcmapp.entity;

/**
 * Created by kevin-pc on 06/06/2016.
 */
public class User {

    /**
     * User id
     */
    protected long id;
    /**
     * User login
     */
    protected String login;
    /**
     * User password
     */
    protected String password;
    /**
     * User id type
     */
    protected int idType;

    //region Getter and Setter

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //endregion
}
