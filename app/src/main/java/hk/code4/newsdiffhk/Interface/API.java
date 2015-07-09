package hk.code4.newsdiffhk.Interface;

import java.util.List;

import hk.code4.newsdiffhk.Model.News;
import hk.code4.newsdiffhk.Model.NewsItem;
import hk.code4.newsdiffhk.Model.Publisher;
import retrofit.http.GET;
import rx.Observable;

/**
 * Created by allen517 on 8/7/15.
 */
public interface API {

    @GET("/news")
    Observable<List<News>> getNews();

    @GET("/publishers")
    Observable<List<Publisher>> getPublisher();

    @GET("/publishers/{}/news")
    Observable<List<NewsItem>> getPublisherNews();
}
