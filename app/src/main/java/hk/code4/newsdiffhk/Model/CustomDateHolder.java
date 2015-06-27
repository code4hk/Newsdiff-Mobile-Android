package hk.code4.newsdiffhk.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by allen517 on 22/6/15.
 */
public class CustomDateHolder {
    @SerializedName("$date")
    private long date;

    public Date getDate() {
        return new Date(date);
    }

    public long getLong() {
        return date;
    }
}