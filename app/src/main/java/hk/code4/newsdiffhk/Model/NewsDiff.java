package hk.code4.newsdiffhk.Model;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by allen517 on 22/6/15.
 */
public class NewsDiff {
    int total_revisions, comments_no;
    CustomDateHolder updated_at;
    CustomOidHolder _id;
    Revision revisions;
    String url;
    CustomDateHolder last_check;
    String title;
    String publisher;
    CustomDateHolder created_at;
    double changes;
    String lang;

    public int getTotalRevisions() {
        return total_revisions;
    }

    public int getCommentsNo() {
        return comments_no;
    }

    public CustomDateHolder getUpdatedAt() {
        return updated_at;
    }

    public String getId() {
        return (_id == null)? "": _id.getOid();
    }

    public Revision getRevisions() {
        return revisions;
    }

    public String getUrl() {
        return url;
    }

    public CustomDateHolder getLastCheck() {
        return last_check;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public CustomDateHolder getCreatedAt() {
        return created_at;
    }

    public double getChanges() {
        return changes;
    }

    public String getLang() {
        return lang;
    }

    public class Revision {
        DiffDetail from;
        DiffDetail to;

        public DiffDetail getFrom() {
            return from;
        }

        public DiffDetail getTo() {
            return to;
        }
    }

    public class DiffDetail {
        int version;
        String body, published_at;
        String title, content;
        CustomDateHolder archive_time;

        public int getVersion() {
            return version;
        }

        public String getBody() {
            return body;
        }

        public String getPublishedAt() {
            return published_at;
        }

        public String formatHTML(String replacePattern, String color, String content) {

            Pattern pattern = Pattern.compile(replacePattern);
            Matcher matcher = pattern.matcher(content);
            int count = 0;
            while(matcher.find()) {
                count++;
                System.out.println("found: " + count + " : "
                        + matcher.start() + " - " + matcher.end());
            }
            return matcher.replaceAll("<font color=\""+color+"\">$1</font>");
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            String formatted = formatHTML("<o>(.*?)<\\/o>", "red", content);
            return formatHTML("<c>(.*?)<\\/c>", "#22aa22", formatted);
        }

        public Date getArchiveTime() {
            return archive_time.getDate();
        }
    }
}
