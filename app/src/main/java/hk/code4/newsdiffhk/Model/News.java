package hk.code4.newsdiffhk.Model;

import java.util.ArrayList;
import java.util.List;

public class News {

    private List<NewsItem> news = new ArrayList<NewsItem>();
    private Meta meta = new Meta();

    public List<NewsItem> getNews() {
        return news;
    }

    public Meta getMeta() {
        return meta;
    }
}