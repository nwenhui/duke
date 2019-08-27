import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.util.regex.Pattern;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Duke {
    public static void main(String[] args) throws IOException, ParseException {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);

        //lvl 8: dates and times
        File dataFile = new File("/Users/wenhui/dukeyduke/data");
        File data = new File("/Users/wenhui/dukeyduke/data/duke.txt");
        ArrayList<Task> userList = new ArrayList<>(100);
        if (data.exists()) {
            readData read = new readData();
            read.openFile();
            userList = read.readFile();
            read.closeFile();
        } else {
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
                DukeException.checkCommand(userInput);
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
                    DukeException.checkTask(taskNum, userList);
                    Task doneTask = userList.get(taskNum);
                    doneTask.markAsDone();
                    status = doneTask.getType();
                    inputData newCompleted = new inputData();
                    String type = "";
                    if (doneTask.getType().contains("T")) {
                        type = "todo";
                    } else if (doneTask.getType().contains("E")) {
                        type = "event";
                    } else {
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
                        DukeException.checkDescription(userInput);
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
                    userList.add(newDeadline);
                    tokens[1] = convertDate(tokens[1]);
                    System.out.println("Got it. I've added this task:\n\t[D][\u2718] " + tokens[0].trim() + " (by: " + tokens[1].trim() + ")");
                    newData.addIncompleteDeadline(tokens[0].trim(), tokens[1].trim());
                    System.out.println("Now you have " + userList.size() + " tasks in the list");
                } else if (userInput.equalsIgnoreCase("event")) {
                    userInput = scan.nextLine();
                    String[] tokens = userInput.split(Pattern.quote(" /at "));
                    Task newEvent = new Event(tokens[0], tokens[1]);
                    userList.add(newEvent);
                    tokens[1] = convertDate(tokens[1]);
                    System.out.println("Got it. I've added this task:\n\t[E][\u2718] " + tokens[0].trim() + " (at: " + tokens[1].trim() + ")");
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

    //function to convert input string into the specified date format
    static String convertDate(String s) throws ParseException {
        String[] tokens = s.split(Pattern.quote("/"));
        SimpleDateFormat sourceFormat = new SimpleDateFormat("d/MM/yyyy HHmm");
        SimpleDateFormat targetFormatDate = new SimpleDateFormat("d");
        SimpleDateFormat targetFormat = new SimpleDateFormat("' of' MMMM yyyy, hh:mm aa");
        Date sourceDate = sourceFormat.parse(s);
        String converted = targetFormatDate.format(sourceDate) + getSuffix(Integer.parseInt(tokens[0])) + targetFormat.format(sourceDate);
        return converted;
    }

    //function to get the suffix for the date format
    static String getSuffix(int n) {
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }
}