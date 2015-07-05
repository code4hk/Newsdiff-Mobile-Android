package hk.code4.newsdiffhk.Model;

import java.util.ArrayList;
import java.util.List;

public class News {

    private List<NewsItem> news = new ArrayList<NewsItem>();
    private Meta meta = new Meta();

    public List<NewsItem> getNews() {
        return news;
    }

    public void add(NewsItem newsItem) {
        news.add(newsItem);
    }

    public void add(List<NewsItem> news) {
        for (NewsItem item:news) {
            this.news.add(item);
        }
    }

    public void clear() {
        news.clear();
    }

    public Meta getMeta() {
        return meta;
    }
}