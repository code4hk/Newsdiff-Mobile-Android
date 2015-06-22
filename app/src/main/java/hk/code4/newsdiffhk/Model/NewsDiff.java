package hk.code4.newsdiffhk.Model;

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

    class Revision {
        DiffDetail from;
        DiffDetail to;

        public DiffDetail getFrom() {
            return from;
        }

        public DiffDetail getTo() {
            return to;
        }
    }

    class DiffDetail {
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

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public CustomDateHolder getArchiveTime() {
            return archive_time;
        }
    }
}
