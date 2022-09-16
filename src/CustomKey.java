
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
        return " ReplyKeySet {" +
                "commentKey='" + commentKey + '\'' +
                ", replyKey='" + replyKey + '\'' +
                '}';
    }
}
