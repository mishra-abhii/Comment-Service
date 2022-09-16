import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    static BufferedReader in;
    static int SelectedOption = 0;
    static int commentKey = 0;
    static int replyKey = 0;
    static Map<String, Comment_Detail> CommentMap;
    static Map<CustomKey, Comment_Detail> RepliesMap;

    public static void main(String[] args) throws IOException {

        // creating object of BufferReader class to take user input
        in = new BufferedReader (new InputStreamReader(System.in));

        CommentMap = new HashMap<>();
        RepliesMap = new HashMap<>();

        DisplayMenu(); // calling display menu
    }

    private static void DisplayMenu() throws IOException {

        do {
            System.out.print("""
                    ========= Comment Service ========
                    1:- Add a new comment
                    2:- Delete an old comment
                    3:- Upvote/Downvote any Comment
                    4:- Add a reply to any comment
                    5:- Give Upvote/Downvote to any Reply
                    6:- Exit the Comment Service
                    Which action you want to perform ? :\s""");

            do {
                SelectedOption = Integer.parseInt(in.readLine());
                System.out.println((SelectedOption < 1 || SelectedOption > 6) ? "Enter valid option" : " ");

            }while (SelectedOption < 1 || SelectedOption > 6);

            switch (SelectedOption) {
                case 1 -> {
                    String comment;
                    System.out.print("Enter the Comment : ");
                    comment = in.readLine();
                    System.out.println();

                    Add_Comment(comment);
                }
                case 2 -> {
                    DisplayComments();
                    int key;
                    System.out.print("Enter the Key of comment you want to delete : ");
                    key = Integer.parseInt(in.readLine());

                    Delete_Comment(key);
                }
                case 3 -> {
                    DisplayComments();
                    int key2;
                    System.out.print("Enter the Key of comment you want to Upvote/Downvote : ");
                    key2 = Integer.parseInt(in.readLine());

                    Upvote_Downvote(key2);
                }
                case 4 -> {
                    DisplayComments();
                    int key3;
                    System.out.print("Wanna Reply!! \n" +"Enter the Key of comment you want to Reply : ");
                    key3 = Integer.parseInt(in.readLine());

                    Add_Reply(key3);
                }
                case 5 -> {
                    DisplayReplies();
                    int key1, key2;
                    System.out.println("Enter the KeySet of any Reply (commentKey, replyKey)");
                    System.out.print(" commentKey : "); key1 = Integer.parseInt(in.readLine());
                    System.out.print(" replyKey : "); key2 = Integer.parseInt(in.readLine());

                    Up_DownVote_Reply(key1, key2);

                }
                case 6 -> {
                    System.out.println("Thanks for your involvement, Have a nice day!!");
                    System.exit(0);
                }
                default -> System.out.println("\nEnter valid option");
            }

        }while(SelectedOption != 6);
    }

    private static void Add_Comment(String comment) {

        int count = 0;
        String time = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy").format(Calendar.getInstance().getTime());

        Comment_Detail detail = new Comment_Detail(comment, count, time);
        CommentMap.put("Key="+ (commentKey + 1) + " ", detail);

        DisplayComments();
        commentKey++;

    }

    private static void Delete_Comment(int key) {

        boolean found = false;
        String k = String.valueOf(key);
        for (Map.Entry<String, Comment_Detail> set : CommentMap.entrySet()) {
            if (("Key=" + key + " ").equals(set.getKey())) {
                found = true;
                break;
            }
        }
        if(found){

            CommentMap.remove("Key="+ key + " ");
            DeleteReplies(k);

            System.out.println("Comment deleted successfully!!");
            System.out.println();
            DisplayComments();
        }
        else{
            System.out.println("No comment found with this key!!");
            System.out.println();
        }

    }

    private static void Upvote_Downvote(int key) throws IOException {

        boolean found = false;
        Comment_Detail c_detail = null;
        for (Map.Entry<String, Comment_Detail> set : CommentMap.entrySet()) {
            if(("Key=" + key + " ").equals(set.getKey())){
                c_detail = set.getValue();
                found = true;
                break;
            }
        }

        if(found) {
            int option;
            System.out.println
                    ("""
                            ====== Select your choice : ======
                            1:- Upvote
                            2:- Downvote""");
            System.out.print("Enter choice: ");
            option = Integer.parseInt(in.readLine());

            int new_count;
            assert c_detail != null;
            new_count = c_detail.getCount();

            if (option == 1) {
                Comment_Detail new_detail = new Comment_Detail(c_detail.getMessage(), new_count + 1, c_detail.getTime());
                CommentMap.replace("Key=" + key + " ", new_detail);
            } else if (option == 2) {
                Comment_Detail new_detail = new Comment_Detail(c_detail.getMessage(), new_count - 1, c_detail.getTime());
                CommentMap.replace("Key=" + key + " ", new_detail);
            } else {
                System.out.println("Invalid selection");
            }

            System.out.println("Points updated !!");
            DisplayComments();
        }
        else{
            System.out.println("No comment found with this key!!");
            System.out.println();
        }
    }

    private static void Add_Reply(int comment_key) throws IOException {

        boolean found = false;

        for (Map.Entry<String, Comment_Detail> set : CommentMap.entrySet()) {
            if(("Key=" + comment_key + " ").equals(set.getKey())){
                found = true;
                break;
            }
        }
        if(found){
            int count2 = 0;
            String reply;
            System.out.print("Enter your reply : "); reply = in.readLine();
            String time = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy")
                    .format(Calendar.getInstance().getTime());

            Comment_Detail reply_detail = new Comment_Detail(reply, count2, time);
            CustomKey keySet = new CustomKey(String.valueOf(comment_key), String.valueOf(replyKey+1));

            RepliesMap.put(keySet, reply_detail);
            System.out.println();
            DisplayReplies();

            replyKey++;
        }
        else{
            System.out.println("No comment found with this key!!");
            System.out.println();
        }
    }

    private static void Up_DownVote_Reply(int key1, int key2) throws IOException {

        boolean found = false;
        CustomKey key = new CustomKey(String.valueOf(key1), String.valueOf(key2));

        Comment_Detail reply_detail = null;

        for (Map.Entry<CustomKey, Comment_Detail> set : RepliesMap.entrySet()) {

            if(Objects.equals(set.getKey().commentKey, key.commentKey) && Objects.equals(set.getKey().replyKey, key.replyKey)){
                reply_detail = set.getValue();
                found = true;
                break;
            }
        }

        if(found) {
            int option;
            System.out.println
                    ("""
                            ====== Select your choice : ======
                            1:- Upvote
                            2:- Downvote""");
            System.out.print("Enter choice: ");
            option = Integer.parseInt(in.readLine());

            int new_count;
            assert reply_detail != null;
            new_count = reply_detail.getCount();

            if (option == 1) {
                Comment_Detail new_detail = new Comment_Detail(reply_detail.getMessage(), new_count+1, reply_detail.getTime());
                RepliesMap.replace(key, new_detail);

                System.out.println("Points updated !!");
                DisplayReplies();

            } else if (option == 2) {
                Comment_Detail new_detail = new Comment_Detail(reply_detail.getMessage(), new_count-1, reply_detail.getTime());
                RepliesMap.replace(key, new_detail);

                System.out.println("Points updated !!");
                DisplayReplies();

            } else {
                System.out.println("Invalid selection");
            }

        }
        else{
            System.out.println("No Reply found with this key!!");
            System.out.println();
        }

    }

    private static void DisplayComments() {
        CommentMap.forEach((key, value) -> System.out.println(key + ":" + value));
        System.out.println();
    }

    private static void DisplayReplies() {

        System.out.println("Comments :- ");
        CommentMap.forEach((key, value) -> {
            System.out.println("  " + key + ":" + value);
        });

        System.out.println("Replies :- ");
        RepliesMap.forEach((key, value) -> {
            System.out.println("  " + key + ":" + value);
        });
        System.out.println();
    }

    private static void DeleteReplies(String k){
        Iterator<CustomKey> it = RepliesMap.keySet().iterator();
        while (it.hasNext()) {
            CustomKey keySet = it.next();
            if(Objects.equals(keySet.commentKey, k)) {
                it.remove();
            }
        }
    }

}