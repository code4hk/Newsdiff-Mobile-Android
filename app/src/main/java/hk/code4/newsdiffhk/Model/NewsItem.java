package hk.code4.newsdiffhk.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by allen517 on 22/6/15.
 */
public class NewsItem {
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