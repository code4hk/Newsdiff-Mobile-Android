package hk.code4.newsdiffhk.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import hk.code4.newsdiffhk.Constance;
import hk.code4.newsdiffhk.Interface.Api;
import retrofit.RestAdapter;

/**
 * Created by allen517 on 27/6/15.
 */
public class NetworkUtils {

    private static Api api;

    public static Api createApi() {

        if (api == null) {
            api = new RestAdapter.Builder().setEndpoint(Constance.BASE_URL).build().create(Api.class);
        }
        return api;
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }

        return false;
    }
}
