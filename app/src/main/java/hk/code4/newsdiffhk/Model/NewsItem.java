package hk.code4.newsdiffhk.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewsItem implements Parcelable {
    public static final Creator<NewsItem> CREATOR = new Creator<NewsItem>() {
        public NewsItem createFromParcel(Parcel in) {
            return new NewsItem(in);
        }

        public NewsItem[] newArray(int size) {
            return new NewsItem[size];
        }
    };
    private final static SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
    private final static SimpleDateFormat output = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.ENGLISH);
    private final Pattern p = Pattern.compile("<img class=\\\"imagefield imagefield-field_image\\\"[^>]+src=\\\"([^\\\">]+)[^>]+>");
    private String mTitle = "", mLink = "", mDate = "", mCreator = "", mDesc = "", mContent = "", mImageUrl = "";
    private int mId;

    public NewsItem() {

    }

    private NewsItem(Parcel in) {
        mTitle = in.readString();
        mLink = in.readString();
        mDate = in.readString();
        mCreator = in.readString();
        mDesc = in.readString();
        mContent = in.readString();
        mImageUrl = in.readString();
        mId = in.readInt();
    }

    public void recode() {

        mContent = mContent.replaceAll("<p>&nbsp;</p>", "");
        mContent =
                mContent.replaceAll("<iframe[^>]+src=\"//www.youtube.com/embed/([^\">]+)[^>]+></iframe>", "<a href=\"http://www.youtube.com/watch?v=$1\">http://www.youtube.com/watch?v=$1</a>");

        final Matcher m = p.matcher(mContent);
        if (m.find())
            mImageUrl = m.group(1);

        mContent = mContent.replaceAll("<img class=\\\"imagefield imagefield-field_image\\\"[^>]+src=\\\"([^\\\">]+)[^>]+>", "");

    }

    public void setData(String Title, String Link, String Date, String Creator, String Desc) {
        mTitle = Title;
        mLink = Link;
        mDate = praseDate(Date);
        mCreator = Creator;
//        final String s = Jsoup.parse(Desc).text();
//        if (!TextUtils.isEmpty(s) && s.length() >= 90)
//            mDesc = s.substring(0, 90) + "...";
//        else
//            mDesc = "";
//        mContent = Desc;
//        recode();
    }

    public int getId() {
        return mId;
    }

    public void setId(int s) {
        this.mId = s;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getLink() {
        return mLink;
    }

    public String getDate() {
        return mDate;
    }

    public String getCreator() {
        return mCreator;
    }

    public String getDesc() {
        return mDesc;
    }

    public String getContent() {
        return mContent;
    }

        public String getImageUrl() {
        return mImageUrl;
    };

    private String praseDate(String s) {
        try {
            final Date date = formatter.parse(s);
            return output.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mLink);
        dest.writeString(mDate);
        dest.writeString(mCreator);
        dest.writeString(mDesc);
        dest.writeString(mContent);
        dest.writeString(mImageUrl);
        dest.writeInt(mId);
    }
}