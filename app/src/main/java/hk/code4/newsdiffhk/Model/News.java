package hk.code4.newsdiffhk.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
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

    class NewsItem {
        String publisher, url;
        CustomDateHolder created_at;
        int comments_no;
        CustomDateHolder updated_at;
        CustomOidHolder _id;
        double changes;
        String title, lang;
        CustomDateHolder last_check;

        public String getPublisher() {
            return publisher;
        }

        public String getUrl() {
            return url;
        }

        public Date getCreatedAt() {
            return created_at.getDate();
        }

        public int getCommentsNo() {
            return comments_no;
        }

        public Date getUpdatedAt() {
            return updated_at.getDate();
        }

        public long getUpdatedAtLong() {
            return updated_at.getLong();
        }

        public String getId() {
            return (_id == null)? "": _id.getOid();
        }

        public double getChanges() {
            return changes;
        }

        public String getTitle() {
            return title;
        }

        public String getLang() {
            return lang;
        }

        public Date getLastCheck() {
            return last_check.getDate();
        }
    }

    class Meta {
        int count, total_count;
        String next;

        public int getCount() {
            return count;
        }

        public int getTotalCount() {
            return total_count;
        }

        public String getNext() {
            return next;
        }
    }

    public class CustomOidHolder {
        @SerializedName("$oid")
        private String oid;

        public String getOid() {
            return oid;
        }
    }

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

}