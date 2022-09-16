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
        RepliesMap = new HashMap<CustomKey, Comment_Detail>();

        DisplayMenu(); // calling display menu
    }

    private static void DisplayMenu() throws IOException {

        do {
            System.out.print("""
                    ========= Comment Service ========
                    1:- Add a new comment
                    2:- Delete an old comment
                    3:- Upvote/Downvote any comment
                    4:- Add a reply to any comment
                    5:- Exit the Comment Service
                    Which action you want to perform ? :\s""");

            do {
                SelectedOption = Integer.parseInt(in.readLine());
                System.out.println((SelectedOption < 1 || SelectedOption > 5) ? "Enter valid option" : " ");

            }while (SelectedOption < 1 || SelectedOption > 5);

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
                    System.out.println("Thanks for your involvement, Have a nice day!!");
                    System.exit(0);
                }
                default -> System.out.println("\nEnter valid option");
            }

        }while(SelectedOption != 5);
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
        for (Map.Entry<String, Comment_Detail> set : CommentMap.entrySet()) {
            if (("Key=" + key + " ").equals(set.getKey())) {
                found = true;
                break;
            }
        }
        if(found){
            CommentMap.remove("Key="+ key + " ");
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

//            ArrayList<Comment_Detail> list = new ArrayList<>();
//            list.add(reply_detail);

            CustomKey keySet = new CustomKey(String.valueOf(comment_key), String.valueOf(replyKey+1));

            RepliesMap.put(keySet, reply_detail);
            System.out.println();
            DisplayReplies(comment_key);

            replyKey++;
        }
        else{
            System.out.println("No comment found with this key!!");
            System.out.println();
        }
    }

    private static void DisplayComments() {
        CommentMap.forEach((key, value) -> System.out.println(key + ":" + value));
        System.out.println();
    }

    private static void DisplayReplies(int comment_key) {

        System.out.println(CommentMap.get("Key=" + comment_key + " "));
        System.out.println("  Replies :- ");
        RepliesMap.forEach((key, value) -> {
            System.out.println("  " + key + ":" + value);
        });
        System.out.println();
    }

}