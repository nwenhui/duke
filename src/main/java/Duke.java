import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);

        //lvl 2: add, list
        System.out.println("Hello! I'm Duke \nWhat can I do for you?");
        Scanner scan = new Scanner(System.in);
        String userInput = scan.nextLine();
        String bye = "bye";
        String listString = "list";
        ArrayList<String> userList = new ArrayList<>(100);
        while (!userInput.equalsIgnoreCase(bye)){
            if (userInput.equalsIgnoreCase(listString)){
                printList(userList);
            } else {
                System.out.println("added: " + userInput);
                userList.add(userInput);
            }
            userInput = scan.nextLine();
        }
        System.out.println("Bye. Hope to see you again soon!");
    }
    //function to print list out -- prints error message when list is empty
    public static void printList (ArrayList<String> l){
        if (l.isEmpty()) {
            System.out.println("e r r o r: list is currently empty!! \npls enter any input and try again");
        } else{
            int count = 0;
            for (int i = 0; i < l.size(); i++){
                System.out.println(++count + ". " + l.get(i));
            }
        }
    }
}
