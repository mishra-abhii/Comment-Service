import java.util.Objects;

public class CustomKey {
    String commentKey;
    String replyKey;

    public CustomKey(String commentKey, String replyKey) {
        this.commentKey = commentKey;
        this.replyKey = replyKey;
    }

    public String getCommentKey() {
        return commentKey;
    }

    public void setCommentKey(String commentKey) {
        this.commentKey = commentKey;
    }

    public String getReplyKey() {
        return replyKey;
    }

    public void setReplyKey(String replyKey) {
        this.replyKey = replyKey;
    }

    @Override
    public String toString() {
        return "Reply-KeySet {" +
                "commentKey = " + commentKey +
                ", replyKey = " + replyKey +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomKey customKey = (CustomKey) o;
        return Objects.equals(commentKey, customKey.commentKey) && Objects.equals(replyKey, customKey.replyKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentKey, replyKey);
    }
}
