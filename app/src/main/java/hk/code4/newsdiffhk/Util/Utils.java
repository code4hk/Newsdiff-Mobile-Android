package hk.code4.newsdiffhk.Util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;

import hk.code4.newsdiffhk.Model.News;
import hk.code4.newsdiffhk.R;

public class Utils {

    @TargetApi(19)
    public static void setTranslucentStatus(Activity activity, Window win, boolean on) {
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);

        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);
        // set a custom tint color for all system bars
        tintManager.setTintColor(activity.getResources().getColor(R.color.primary));
        // set a custom navigation bar resource
//        tintManager.setNavigationBarTintResource(R.drawable.my_tint);
        // set a custom status bar drawable
//        tintManager.setStatusBarTintDrawable(MyDrawable);
    }

    public static ArrayList<News> getFeedProperty(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getApplicationInfo().name, Activity.MODE_PRIVATE);
        final String data = sharedPreferences.getString(key, null);
        if (TextUtils.isEmpty(data))
            return null;
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonArray arr = parser.parse(data).getAsJsonArray();
//        FeedItem[] feedItems = new FeedItem[arr.size()];
        ArrayList<News> newses = new ArrayList<News>();
//        int i=0;
        for (JsonElement jsonElement : arr)
            newses.add(gson.fromJson(jsonElement, News.class));
        return newses;
    }

    public static void setFeedProperty(Context context, String key, ArrayList<News> news) {
        SharedPreferences mPrefs=context.getSharedPreferences(context.getApplicationInfo().name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=mPrefs.edit();
        Gson gson = new Gson();
        ed.putString(key, gson.toJson(news));
        ed.apply();
    }
}