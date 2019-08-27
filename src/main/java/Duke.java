import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.util.Formatter;
import java.io.FileWriter;
import java.io.Writer;
import java.io.BufferedWriter;
import java.util.regex.Pattern;

public class Duke {
    public static void main(String[] args) throws IOException {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);

        //lvl 7: save
        File dataFile = new File("/Users/wenhui/dukeyduke/data");
        File data = new File("/Users/wenhui/dukeyduke/data/duke.txt");
        ArrayList<Task> userList = new ArrayList<>(100);
        if (data.exists()){
            readData read = new readData();
            read.openFile();
            userList = read.readFile();
            read.closeFile();
        } else{
            System.out.println("no");
            dataFile.mkdir();
            FileWriter writer = new FileWriter("/Users/wenhui/dukeyduke/data/duke.txt");
        }
        System.out.println("Hello! I'm Duke \nWhat can I do for you?\n*-*-*-*-*-*-*-*-*-*-*-*-*\nHere is your current list:");
        printList(userList);
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*");
        Scanner scan = new Scanner(System.in);
        String userInput = scan.next();
        String status;
        char trash;
        while (!userInput.equalsIgnoreCase("bye")) {
            try {
                checkCommand(userInput);
            } catch (DukeException e) {
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
                    inputData newCompleted = new inputData();
                    String type = "";
                    if (doneTask.getType().contains("T")){
                        type = "todo";
                    } else if (doneTask.getType().contains("E")){
                        type = "event";
                    } else{
                        type = "deadline";
                    }
                    newCompleted.completedTask(type, doneTask.description);
                    System.out.println("Nice! I've marked this task as done: \n\t" + status + "[\u2713] " + doneTask.description);
                } catch (DukeException e) {
                    System.out.println("e r r o r   f o u n d\n" + e);
                }
            } else {
                inputData newData = new inputData();
                if (userInput.equalsIgnoreCase("todo")) {
                    userInput = scan.nextLine();
                    try {
                        checkDescription(userInput);
                        Task newTodo = new Todo(userInput);
                        System.out.println("Got it. I've added this task:\n\t[T][\u2718] " + userInput.trim());
                        userList.add(newTodo);
                        newData.addIncompleteTodo(userInput.trim());
                        System.out.println("Now you have " + userList.size() + " tasks in the list");
                    } catch (DukeException e) {
                        System.out.println("e r r o r   f o u n d\n" + e);
                    }
                } else if (userInput.equalsIgnoreCase("deadline")) {
                    userInput = scan.nextLine();
                    String[] tokens = userInput.split(Pattern.quote(" /by "));
                    Task newDeadline = new Deadline(tokens[0], tokens[1]);
                    System.out.println("Got it. I've added this task:\n\t[D][\u2718] " + tokens[0].trim() + " (by: " + tokens[1].trim() + ")");
                    userList.add(newDeadline);
                    newData.addIncompleteDeadline(tokens[0].trim(), tokens[1].trim());
                    System.out.println("Now you have " + userList.size() + " tasks in the list");
                } else if (userInput.equalsIgnoreCase("event")) {
                    userInput = scan.nextLine();
                    String[] tokens = userInput.split(Pattern.quote(" /at "));
                    Task newEvent = new Event(tokens[0], tokens[1]);
                    System.out.println("Got it. I've added this task:\n\t[E][\u2718] " + tokens[0].trim() + " (at: " + tokens[1].trim() + ")");
                    userList.add(newEvent);
                    newData.addIncompleteEvent(tokens[0].trim(), tokens[1].trim());
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
            System.out.println("ur list is empty. pls input some tasks first");
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
                    System.out.println(++count + "." + type + status + " " + t.description + " (at: " + t.extra + ")");
                } else if (type.contains("D")) {
                    System.out.println(++count + "." + type + status + " " + t.description + " (by: " + t.extra + ")");
                } else {
                    System.out.println(++count + "." + type + status + " " + t.description + t.extra);
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
    static void checkDescription(String input) throws DukeException {
        if (input.isEmpty()) {
            throw new DukeException("ohno u did not enter a description :( pls try again");
        }
    }

    //function to check whether task "done" is valid
    static void checkTask(int a, ArrayList<Task> l) throws DukeException {
        if (a >= l.size() || a < 0) {
            throw new DukeException("ohno u entered an invalid task no. :( pls try again");
        } else {
            Task checkTask = l.get(a);
            if (checkTask.checkStatus()) {
                throw new DukeException("ohno the task is already completed :( pls try with another task number");
            }
        }
    }
}