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
        return new Date(1220227200L * 1000);
    }

    public long getLong() {
        return date;
    }
}