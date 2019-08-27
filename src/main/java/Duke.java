import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Duke {
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);

        //lvl 4: to dos, events, deadlines
        System.out.println("Hello! I'm Duke \nWhat can I do for you?");
        Scanner scan = new Scanner(System.in);
        String userInput = scan.next();
        String status;
        char trash;
        ArrayList<Task> userList = new ArrayList<>(100);
        while (!userInput.equalsIgnoreCase("bye")) {
            if (userInput.equalsIgnoreCase("list")) {
                printList(userList);
            } else if (userInput.contains("done")){
                userInput = scan.nextLine();
                String[] tokens = userInput.split(Pattern.quote(" "));
                int taskNum = Integer.parseInt(tokens[1]) - 1;
                if (taskNum > userList.size()){
                    System.out.println("e r r o r: not a valid task!! \npls try another input");
                } else {
                    Task doneTask = userList.get(taskNum);
                    doneTask.markAsDone();
                    System.out.println("Nice! I've marked this task as done: \n[\u2713] " + doneTask.description);
                }
            } else {
                if (userInput.equalsIgnoreCase("todo")) {
                    userInput = scan.nextLine();
                    Task newTodo = new Todo(userInput);
                    System.out.println("Got it. I've added this task:\n\t[T][\u2718] " + userInput);
                    userList.add(newTodo);
                    System.out.println("Now you have " + userList.size() + " tasks in the list");
                } else if (userInput.equalsIgnoreCase("deadline")) {
                    userInput = scan.nextLine();
                    String[] tokens = userInput.split(" /by ");
                    Task newDeadline = new Deadline(tokens[0], tokens[1]);
                    System.out.println("Got it. I've added this task:\n\t[D][\u2718] " + tokens[0] + " (by: " + tokens[1] + ")");
                    userList.add(newDeadline);
                    System.out.println("Now you have " + userList.size() + " tasks in the list");
                } else if (userInput.equalsIgnoreCase("event")) {
                    userInput = scan.nextLine();
                    String[] tokens = userInput.split(" /at ");
                    Task newEvent = new Event(tokens[0], tokens[1]);
                    System.out.println("Got it. I've added this task:\n\t[E][\u2718] " + tokens[0] + " (at: " + tokens[1] + ")");
                    userList.add(newEvent);
                    System.out.println("Now you have " + userList.size() + " tasks in the list");
                } else{
                    System.out.println("e r r o r: this is not a valid input!!\npls try another command");
                }
            }
            userInput = scan.next();
        }
        System.out.println("Bye. Hope to see you again soon!");
    }

    //function to print list out -- prints error message when list is empty
    public static void printList(ArrayList<Task> l) {
        if (l.isEmpty()) {
            System.out.println("e r r o r: list is currently empty!! \npls enter any input and try again");
        } else {
            System.out.println("Here are the tasks in your list:");
            int count = 0;
            String status;
            String type;
            for (int i = 0; i < l.size(); i++) {
                Task t = l.get(i);
                status = t.getStatusIcon();
                type = t.getType();
                if (type.contains("E")) {
                    System.out.println(++count + "." + type + status + t.description + " (at: " + t.extra + ")");
                } else if (type.contains("D")) {
                    System.out.println(++count + "." + type + status + t.description + " (by: " + t.extra + ")");
                } else {
                    System.out.println(++count + "." + type + status + t.description + t.extra);
                }
            }
        }
    }
}
