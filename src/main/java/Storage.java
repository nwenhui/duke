import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Storage {

    //private Formatter print;
    private Scanner scan;
    private Writer output;
    private File dataFile; //file to store data txt files
    private File data; //path for each .txt file


    //constructor: set path names for storage files
    public Storage(File dataFile, File data){
        this.dataFile = dataFile;
        this.data = data;
    };

    //creating scanner
    public void openFile() throws IOException {
        scan = new Scanner(data);
    }

    /**
     * to load any previous data stored in the hard disk
     * @return the task list with previous data
     * @throws IOException
     */
    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> userList = new ArrayList<>(100);
        if (data.exists()) {
            openFile();
            readFile(userList);
            closeFile(); //might need to delete and add to bye function later oki
        } else {
            dataFile.mkdir();
            FileWriter writer = new FileWriter(data);
        }
        return userList;
    }

    /**
     * to add previous data into an arraylist
     * @param userList the arraylist to be edited with previous data
     * @throws IOException
     */
    public void readFile(ArrayList<Task> userList) throws IOException {
        while (scan.hasNextLine()) {
            String userInput = scan.nextLine();
            String[] tokens = userInput.split(Pattern.quote(" | "));
            if (tokens.length == 3) {
                Todo newTodo = new Todo(tokens[2]);
                newTodo.isDone = tokens[1].equals("completed");
                userList.add(newTodo);
            } else {
                if (tokens[0].equals("deadline")) {
                    Deadline newDeadline = new Deadline(tokens[2], tokens[3]);
                    newDeadline.isDone = tokens[1].equals("completed");
                    userList.add(newDeadline);
                } else {
                    Event newEvent = new Event(tokens[2], tokens[3]);
                    newEvent.isDone = tokens[1].equals("completed");
                    userList.add(newEvent);
                }
            }
        }
    }

    //adding tasks from userInput//

    /**
     * adds a new todo as specified by the user
     * @param s description of the task
     * @throws IOException
     */
    public void addIncompleteTodo(String s) throws IOException {
        output = new BufferedWriter((new FileWriter(data, true)));
        output.append("todo | incomplete | " + s + "\n");
        output.close();
    }

    /**
     * adds a new deadline as specified by the user
     * @param s1 description of deadline
     * @param s2 date and time of deadline
     * @throws IOException
     */
    public void addIncompleteDeadline(String s1, String s2) throws IOException {
        output = new BufferedWriter((new FileWriter("/Users/wenhui/dukeyduke/data/duke.txt", true)));
        output.append("deadline | incomplete | " + s1 + " | " + s2 + "\n");
        output.close();
    }

    /**
     * adds a new event as specified by the user
     * @param s1 description of event
     * @param s2 date and time of event
     * @throws IOException
     */
    public void addIncompleteEvent(String s1, String s2) throws IOException {
        output = new BufferedWriter((new FileWriter("/Users/wenhui/dukeyduke/data/duke.txt", true)));
        output.append("event | incomplete | " + s1 + " | " + s2 + "\n");
        output.close();
    }

    /**
     * edit data in hard disk to reflect the completion of a task
     * @param type the type of task completed (todo/deadline/event)
     * @param description description of task completed
     * @throws IOException
     */
    public void setCompletedTask(String type, String description) throws IOException {
        File oldFile = data;
        File newFile = new File("/Users/wenhui/dukeyduke/data/temp.txt");

        FileWriter fw = new FileWriter(newFile, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        scan = new Scanner(data);

        while (scan.hasNextLine()) {
            String userInput = scan.nextLine();
            String[] tokens = userInput.split(Pattern.quote(" | "));
            if (tokens[0].equals(type) && tokens[2].equals(description)) {
                if (tokens.length == 3) {
                    pw.println(tokens[0] + " | completed | " + tokens[2]);
                } else {
                    pw.println(tokens[0] + " | completed | " + tokens[2] + " | " + tokens[3]);
                }
            } else {
                pw.println(userInput);
            }
        }
        scan.close();
        pw.flush();
        pw.close();
        oldFile.delete();
        newFile.renameTo(new File(data.getAbsolutePath()));
    }

    /**
     * deletes task from data stored in hard disk as specified by user
     * @param type type of task to be deleted (todo/deadline/event)
     * @param description description of task to be deleted
     * @throws IOException
     */
    public void deleteTask(String type, String description) throws IOException {
        File oldFile = data;
        File newFile = new File("/Users/wenhui/dukeyduke/data/temp.txt");

        FileWriter fw = new FileWriter(newFile, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        scan = new Scanner(data);

        while (scan.hasNextLine()) {
            String userInput = scan.nextLine();
            String[] tokens = userInput.split(Pattern.quote(" | "));
            if (!tokens[0].equals(type) && !tokens[2].equals(description)) {
                pw.println(userInput);
            }
        }
        scan.close();
        pw.flush();
        pw.close();
        oldFile.delete();
        newFile.renameTo(new File(data.getAbsolutePath()));
    }

    /**
     * clears all data from hard disk (extra)
     * @throws IOException
     */
    public void clear() throws IOException {
        File oldFile = data;
        File newFile = new File("/Users/wenhui/dukeyduke/data/temp.txt");
        oldFile.delete();
        FileWriter fw = new FileWriter(newFile);
        newFile.renameTo(new File(data.getAbsolutePath()));
        System.out.println("task list cleared! :)");
    }

    public void closeFile(){
        scan.close();
    }
}