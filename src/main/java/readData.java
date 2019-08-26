import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.util.Formatter;
import java.util.regex.Pattern;

public class readData {
    private Scanner scan;

    public void openFile() throws FileNotFoundException {
        //scan = new Scanner(new File("/Users/wenhui/dukeyduke/data/duke.txt")).useDelimiter("\\s\u007C\\s");
        scan = new Scanner(new File("/Users/wenhui/dukeyduke/data/duke.txt"));
    }

    public ArrayList<Task> readFile(){
        ArrayList<Task> userList = new ArrayList<>(100);
        while (scan.hasNextLine()) {
            String userInput = scan.nextLine();
            String[] tokens = userInput.split(Pattern.quote(" | "));
            if (tokens.length == 3) {
                Todo newTodo = new Todo(tokens[2]);
                if (tokens[1].equals("completed")) {
                    newTodo.isDone = true;
                } else {
                    newTodo.isDone = false;
                }
                userList.add(newTodo);
            } else {
                if (tokens[0].equals("deadline")) {
                    Deadline newDeadline = new Deadline(tokens[2], tokens[3]);
                    if (tokens[1].equals("completed")) {
                        newDeadline.isDone = true;
                    } else {
                        newDeadline.isDone = false;
                    }
                    userList.add(newDeadline);
                } else {
                    Event newEvent = new Event(tokens[2], tokens[3]);
                    if (tokens[1].equals("completed")) {
                        newEvent.isDone = true;
                    } else {
                        newEvent.isDone = false;
                    }
                    userList.add(newEvent);
                }
            }
        }
        return userList;
    }


    public void closeFile(){
        scan.close();
    }
}
