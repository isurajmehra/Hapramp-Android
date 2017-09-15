package bxute.config;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Ankit on 7/22/2017.
 */

public class RemoteDataType {

    public static final String TYPE_MESSAGE = "message";
    public static final String TYPE_RECEIVING = "receiving";
    public static final String TYPE_SEEN = "seen";
    public static final String TYPE_TYPING = "typing";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({TYPE_MESSAGE,TYPE_RECEIVING,TYPE_SEEN,TYPE_TYPING})
    public @interface RemoteData{}

}