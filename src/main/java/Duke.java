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

        //lvl 3: mark as done
        System.out.println("Hello! I'm Duke \nWhat can I do for you?");
        Scanner scan = new Scanner(System.in);
        String userInput = scan.nextLine();
        String bye = "bye";
        String listString = "list";
        ArrayList<Task> userList = new ArrayList<>(100);
        while (!userInput.equalsIgnoreCase(bye)){
            if (userInput.equalsIgnoreCase(listString)){
                printList(userList);
            } else if (userInput.contains("done")){
                char taskNum = userInput.charAt(userInput.length() - 1);
                if (Character.getNumericValue(taskNum) > userList.size()){
                    System.out.println("e r r o r: not a valid task!! \npls try another input");
                } else {
                    Task doneTask = userList.get(Character.getNumericValue(taskNum) - 1);
                    doneTask.markAsDone();
                    System.out.println("Nice! I've marked this task as done: \n[\u2713] " + doneTask.description);
                }
            } else{
                Task newTask = new Task(userInput);
                System.out.println("added: " + userInput);
                userList.add(newTask);
            }
            userInput = scan.nextLine();
        }
        System.out.println("Bye. Hope to see you again soon!");
    }
    //function to print list out -- prints error message when list is empty
    public static void printList (ArrayList<Task> l){
        if (l.isEmpty()) {
            System.out.println("e r r o r: list is currently empty!! \npls enter any input and try again");
        } else{
            System.out.println("Here are the tasks in your list:");
            int count = 0;
            String status;
            for (int i = 0; i < l.size(); i++){
                Task t = l.get(i);
                status = t.getStatusIcon();
                System.out.println(++count + ". [" + status + "] " + t.description);
            }
        }
    }
}
