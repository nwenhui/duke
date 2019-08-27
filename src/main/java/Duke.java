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

        //lvl 5: handle errors
        System.out.println("Hello! I'm Duke \nWhat can I do for you?");
        Scanner scan = new Scanner(System.in);
        String userInput = scan.next();
        String status;
        char trash;
        ArrayList<Task> userList = new ArrayList<>(100);
        while (!userInput.equalsIgnoreCase("bye")){
            try {
                checkCommand(userInput);
            } catch (DukeException e){
                System.out.println("e r r o r   f o u n d\n" + e);
            }
            if (userInput.equalsIgnoreCase("list")) {
                printList(userList);
            } else if (userInput.equalsIgnoreCase("done")) {
                userInput = scan.nextLine();
                String[] tokens = userInput.split(Pattern.quote(" "));
                int taskNum = Integer.parseInt(tokens[1]) - 1;
                try {
                    checkTask(taskNum, userList);
                    Task doneTask = userList.get(taskNum);
                    doneTask.markAsDone();
                    status = doneTask.getType();
                    System.out.println("Nice! I've marked this task as done: \n\t" + status + "[\u2713] " + doneTask.description);
                } catch (DukeException e){
                    System.out.println("e r r o r   f o u n d\n" + e);
                }
            } else {
                if (userInput.equalsIgnoreCase("todo")){
                    userInput = scan.nextLine();
                    try {
                        checkDescription(userInput);
                        Task newTodo = new Todo(userInput);
                        System.out.println("Got it. I've added this task:\n\t[T][\u2718] " + userInput);
                        userList.add(newTodo);
                        System.out.println("Now you have " + userList.size() + " tasks in the list");
                    } catch (DukeException e){
                        System.out.println("e r r o r   f o u n d\n" + e);
                    }
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

    //function to check whether input command is valid
    static void checkCommand(String command) throws DukeException {
        String[] commandList = {"todo", "deadline", "event", "done", "list"};
        boolean flag = false;
        for (int i = 0; i < commandList.length; i++) {
            if (command.equalsIgnoreCase(commandList[i])) {
                flag = true;
            }
        }
        if (!flag) {
            throw new DukeException("ohno u entered an invalid command :( pls try again");
        }
    }

    //function to check whether there is description added
    static void checkDescription(String input) throws DukeException{
        if (input.isEmpty()){
            throw new DukeException("ohno u did not enter a description :( pls try again");
        }
    }

    //function to check whether task "done" is valid
    static void checkTask(int a, ArrayList<Task> l) throws DukeException{
        if (a >= l.size() || a < 0){
            throw new DukeException("ohno u entered an invalid task no. :( pls try again");
        } else{
            Task checkTask = l.get(a);
            if (checkTask.checkStatus()){
                throw new DukeException("ohno the task is already completed :( pls try with another task number");
            }
        }
    }
}
