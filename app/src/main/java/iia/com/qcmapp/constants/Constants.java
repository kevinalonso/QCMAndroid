package iia.com.qcmapp.constants;

import java.util.ArrayList;
import java.util.List;

import iia.com.qcmapp.entity.UserAnswer;

/**
 * File for constant value
 * Created by kevin-pc on 02/03/2016.
 */
public abstract class Constants {
    public static final String END_QCM = "Fin du QCM";

    public static List<UserAnswer> resList;

    public static void listAdd(UserAnswer userAnswer){
        resList.add(userAnswer);
    }

    /**
     * Is use to the post methods
     */
    public static void inst(){
        resList = new ArrayList<UserAnswer>();
    }
}
