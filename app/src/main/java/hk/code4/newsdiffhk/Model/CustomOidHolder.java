package hk.code4.newsdiffhk.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by allen517 on 22/6/15.
 */
public class CustomOidHolder {
    @SerializedName("$oid")
    private String oid;

    public String getOid() {
        return oid;
    }
}