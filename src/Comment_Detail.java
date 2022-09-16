
public class Comment_Detail {

    String message; //comment
    String time;
    int count; // Upvote/DownVote

    public Comment_Detail(String message, int count, String time) {
        this.message = message;
        this.count = count;
        this.time = time;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return " Comment{" +
                " Message = " + message +
                ", Points = " + count  +
                ", Time = " + time +
                '}';
    }
}

