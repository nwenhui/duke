import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.regex.Pattern;

public class inputData {

    private Formatter print;
    private Scanner scan;
    private Writer output;

    public void openFile() throws FileNotFoundException {
        print = new Formatter(new File("/Users/wenhui/dukeyduke/data/duke.txt"));
    }

    public void addIncompleteTodo(String s) throws IOException {
        output = new BufferedWriter((new FileWriter("/Users/wenhui/dukeyduke/data/duke.txt", true)));
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

    public void completedTask(String type, String description) throws IOException {
        File oldFile = new File("/Users/wenhui/dukeyduke/data/duke.txt");
        File newFile = new File("/Users/wenhui/dukeyduke/data/tempduke.txt");

        //FileWriter writer = new FileWriter("/Users/wenhui/dukeyduke/data/duke.txt");
        FileWriter fw = new FileWriter("/Users/wenhui/dukeyduke/data/tempduke.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        scan = new Scanner(new File("/Users/wenhui/dukeyduke/data/duke.txt"));
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
        newFile.renameTo(new File("/Users/wenhui/dukeyduke/data/duke.txt"));
    }

    public void deleteTask(String type, String description) throws IOException {
        File oldFile = new File("/Users/wenhui/dukeyduke/data/duke.txt");
        File newFile = new File("/Users/wenhui/dukeyduke/data/tempduke.txt");
        FileWriter fw = new FileWriter("/Users/wenhui/dukeyduke/data/tempduke.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        scan = new Scanner(new File("/Users/wenhui/dukeyduke/data/duke.txt"));
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
        newFile.renameTo(new File("/Users/wenhui/dukeyduke/data/duke.txt"));
    }

    static void clear() throws IOException {
        File oldFile = new File("/Users/wenhui/dukeyduke/data/duke.txt");
        File newFile = new File("/Users/wenhui/dukeyduke/data/tempduke.txt");
        oldFile.delete();
        FileWriter fw = new FileWriter("/Users/wenhui/dukeyduke/data/tempduke.txt");
        newFile.renameTo(new File("/Users/wenhui/dukeyduke/data/duke.txt"));
    }

    public void closeFile(){
        print.close();
    }
}