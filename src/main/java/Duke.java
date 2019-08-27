import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Duke {
    public static void main(String[] args) throws IOException, ParseException, DukeException {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);

        //for storing data
        File dataFile = new File("/Users/wenhui/dukeyduke/data");
        File data = new File("/Users/wenhui/dukeyduke/data/duke.txt");
        ArrayList<Task> userList = new ArrayList<>(100);
        if (data.exists()) {
            readData read = new readData();
            read.openFile();
            userList = read.readFile();
            read.closeFile();
        } else {
            dataFile.mkdir();
            FileWriter writer = new FileWriter("/Users/wenhui/dukeyduke/data/duke.txt");
        }

        //level 9: find
        System.out.println("Hello! I'm Duke \nWhat can I do for you?\nenter help for a list of commands available uwu\n\n*-*-*-*-*-*-*-*-*-*-*-*-*\nHere is your current task list:");
        Command.printList(userList);
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*\n");
        Scanner scan = new Scanner(System.in);
        String userInput = scan.next();
        char trash;
        while (!userInput.equalsIgnoreCase("bye")) {
            try {
                DukeException.checkCommand(userInput);
            } catch (DukeException e) {
                System.out.println("e r r o r   f o u n d\n" + e);
            }
            if (userInput.equalsIgnoreCase("help")) {
                Command.printCommands();
            } else if (userInput.equalsIgnoreCase("clear")) {
                inputData.clear();
            } else if (userInput.equalsIgnoreCase("list")) {
                System.out.println("Here are the tasks in your list:");
                Command.printList(userList);
            } else if (userInput.equalsIgnoreCase("done")) {
                userInput = scan.nextLine();
                Command.doneTask(userInput, userList);
            } else if (userInput.equalsIgnoreCase("delete")) {
                userInput = scan.nextLine();
                Command.deleteTask(userInput, userList);
            } else if (userInput.equalsIgnoreCase("find")) {
                userInput = scan.nextLine().trim();
                Command.findTask(userInput, userList);
            } else {
                inputData newData = new inputData();
                if (userInput.equalsIgnoreCase("todo")) {
                    userInput = scan.nextLine();
                    Todo.addTodo(userInput, userList, newData);
                } else if (userInput.equalsIgnoreCase("deadline")) {
                    userInput = scan.nextLine();
                    Deadline.addDeadline(userInput, userList, newData);
                } else if (userInput.equalsIgnoreCase("event")) {
                    userInput = scan.nextLine();
                    Event.addEvent(userInput, userList, newData);
                }
            }
            userInput = scan.next();
        }
        System.out.println("Bye. Hope to see you again soon!");
    }
}

