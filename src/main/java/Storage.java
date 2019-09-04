import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Formatter;
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

    //creating scanner and formatter
    public void openFile() throws IOException {
        //print = new Formatter(data);
        scan = new Scanner(data);
        //output = new BufferedWriter((new FileWriter(data, true)));
    }

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

    //create userList from existing data stored
    public void readFile(ArrayList<Task> userList) throws IOException {
        //putting data into the list
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

    public void addIncompleteTodo(String s) throws IOException {
        output = new BufferedWriter((new FileWriter(data, true)));
        output.append("todo | incomplete | " + s + "\n");
        output.close();
    }

    public void addIncompleteDeadline(String s1, String s2) throws IOException {
        output = new BufferedWriter((new FileWriter("/Users/wenhui/dukeyduke/data/duke.txt", true)));
        output.append("deadline | incomplete | " + s1 + " | " + s2 + "\n");
        output.close();
    }

    public void addIncompleteEvent(String s1, String s2) throws IOException {
        output = new BufferedWriter((new FileWriter("/Users/wenhui/dukeyduke/data/duke.txt", true)));
        output.append("event | incomplete | " + s1 + " | " + s2 + "\n");
        output.close();
    }

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

    public void clear() throws IOException {
        File oldFile = data;
        File newFile = new File("/Users/wenhui/dukeyduke/data/temp.txt");
        oldFile.delete();
        FileWriter fw = new FileWriter(newFile);
        newFile.renameTo(new File(data.getAbsolutePath()));
        System.out.println("task list cleared! :)");
    }

    public void closeFile(){
        //print.close();
        scan.close();
    }
}