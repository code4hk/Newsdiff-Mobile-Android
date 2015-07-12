package hk.code4.newsdiffhk.Interface;

import java.util.List;

import hk.code4.newsdiffhk.Model.News;
import hk.code4.newsdiffhk.Model.Publisher;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by allen517 on 8/7/15.
 */
public interface API {

    @GET("/publishers")
    Observable<List<Publisher>> getPublisher();

    @GET("/news?sort_by=time&order=desc") // /news?page=" + page + "&sort_by=time&order=desc
    Observable<News> getAllNews(@Query("page") int page);

    @GET("/publisher/{id}/news?sort_by=time&order=desc")
    Observable<News>  getPublisherNews(@Path("id") String id, @Query("page") int page);
}
