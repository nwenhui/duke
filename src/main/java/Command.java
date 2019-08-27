import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class Command {

    //function to print list out -- prints error message when list is empty
    public static void printList(ArrayList<Task> l) {
        if (l.isEmpty()) {
            System.out.println("ur list is empty. pls input some tasks first");
        } else {
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

    //function for complete task
    static void doneTask(String userInput, ArrayList<Task> userList){
        String[] tokens = userInput.split(Pattern.quote(" "));
        int taskNum = Integer.parseInt(tokens[1]) - 1;
        try {
            DukeException.checkTask(taskNum, userList);
            Task doneTask = userList.get(taskNum);
            doneTask.markAsDone();
            String status = doneTask.getType();
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
        } catch (DukeException | IOException e) {
            System.out.println("e r r o r   f o u n d\n" + e);
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

    //function to print out command list
    static void printCommands(){
        System.out.println("todo: enter a task that needs to completed\ntodo <description>\n");
        System.out.println("event: enter a reminder for a upcoming event\nevent <description> /at <date> <time>\n");
        System.out.println("deadline: enter a reminder for a upcoming deadline\n deadline <description> /by <date> <time>\n");
        System.out.println("list: to list out all the tasks that have been saved\nlist\n");
        System.out.println("completed: to check off a completed task\ndone <task number>\n");
        System.out.println("clear: to clear off all task currently stored\nclear\n");
        System.out.println("\tformat for date: d/mm/yyy     format for time: 24hr\n" );
    }
}
