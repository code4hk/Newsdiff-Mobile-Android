package hk.code4.newsdiffhk.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class News {

    private List<NewsItem> news = new ArrayList<NewsItem>();
    private Meta meta = new Meta();

    public Meta getMeta() {
        return meta;
    }

    class NewsItem {
        String publisher, urlte;
        CustomDateHolder updated_at;
        @SerializedName("oid")
        String _id;
        double changes;
        String title, lang;
        CustomDateHolder last_check;
    }

    class Meta {
        int count, total_count;
        String next;

        public int getCount() {
            return count;
        }
    }

    public class CustomDateHolder {
        @SerializedName("$date")
        private long date;
        // Getters & Setters...
    }

}