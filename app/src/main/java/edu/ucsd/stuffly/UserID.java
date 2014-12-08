package edu.ucsd.stuffly;

/**
 * Created by Angie on 12/3/14.
 */
public class UserID {

    public static String id = "";

    public UserID(String id) {
        this.id = id;
    }

    public static String getUserId(){
        return id;
    }

}

