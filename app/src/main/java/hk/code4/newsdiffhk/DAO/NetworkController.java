package hk.code4.newsdiffhk.DAO;

import android.os.AsyncTask;
import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import hk.code4.newsdiffhk.Model.News;
import hk.code4.newsdiffhk.Model.NewsDiff;
import hk.code4.newsdiffhk.Model.Publisher;
import hk.code4.newsdiffhk.Util.OkHTTPClient;

/**
 * Created by allen517 on 23/6/15.
 */
public class NetworkController {
    News allNews = new News();
    public static final String BASE_URL = "http://newsdiff.code4.hk:12345/api";
    public static final String ALL_NEWS_URL = BASE_URL + "/news";
    public static final String PUBLISHER_NEWS_URL = BASE_URL + "/publisher/"; //{publisher_code}/news
    public static final String ALL_PUBLISHER_URL = BASE_URL + "/publishers";
    public boolean mLoading = false;
    OkHTTPClient mHTTPClient = new OkHTTPClient();
    Gson mGson = new Gson();

    private static NetworkController ourInstance = new NetworkController();

    public static NetworkController getInstance() {
        return ourInstance;
    }

    private NetworkController() {
    }

    public String getJson(String url) {
        return mHTTPClient.get(url);
    }

    public News getNews(String json) {
        return mGson.fromJson(json, News.class);
    }

    public NewsDiff getNewsDiff(String json) {
        return mGson.fromJson(json, NewsDiff.class);
    }

    public List<Publisher> getPublisher(String json) {
        Type collectionType = new TypeToken<List<Publisher>>(){}.getType();
        return mGson.fromJson(json, collectionType);
    }

//    public void getAllNews() {
//        GetAllNews data = new GetAllNews();
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
//            data.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ALL_NEWS_URL);
//        else
//            data.execute(ALL_NEWS_URL);
//    }
//
//    private class GetAllNews extends AsyncTask<String, Integer, News> {
//
//        private OkHTTPClient mHTTPClient = new OkHTTPClient();
//
//        @Override
//        protected void onPreExecute() {
//            mLoading = true;
//        }
//
//        @Override
//        protected News doInBackground(String... param) {
//            Thread.currentThread().setName("GetData");
//
//            final String result = mHTTPClient.get(param[0]);
//
//            try {
//                Gson gson = new Gson();
//                News data = gson.fromJson(result, News.class);
//                return data;
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(News news) {
//            super.onPostExecute(news);
//            mLoading = false;
//            allNews = news;
//        }
//    }

    public boolean isLoading() {
        return mLoading;
    }
}
