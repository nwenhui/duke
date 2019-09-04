import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class TaskList {
    private ArrayList<Task> userList;

    public TaskList(ArrayList<Task> inputList){
        userList = inputList;
    }

    public ArrayList<Task> getList(){
        return userList;
    }

    //function to print list out -- prints error message when list is empty
    public void print() {
        if (userList.isEmpty()) {
            System.out.println("ur list is empty. pls input some tasks first");
        } else {
            int count = 0;
            String status;
            String type;
            for (Task t : userList) {
                status = t.getStatusIcon();
                type = t.getType();
                if (type.contains("E")) {
                    System.out.println(++count + "." + type + status + " " + t.description + " (at: " + t.extra + ")");
                } else if (type.contains("D")) {
                    System.out.println(++count + "." + type + status + " " + t.description + " (by: " + t.extra + ")");
                } else {
                    System.out.println(++count + "." + type + status + " " + t.description);
                }
            }
        }
    }

    //function to convert input string into the specified date format
    public String convertDate(String s) throws ParseException {
        String[] tokens = s.split(Pattern.quote("/"));
        SimpleDateFormat sourceFormat = new SimpleDateFormat("d/MM/yyyy HHmm");
        SimpleDateFormat targetFormatDate = new SimpleDateFormat("d");
        SimpleDateFormat targetFormat = new SimpleDateFormat("' of 'MMMM yyyy, hh:mm aa");
        Date sourceDate = sourceFormat.parse(s);
        return (targetFormatDate.format(sourceDate) + getSuffix(Integer.parseInt(tokens[0])) + targetFormat.format(sourceDate));
    }

    //function to get the suffix for the date format
    private String getSuffix(int n) {
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

    public void addDeadline(String userInput, ArrayList<Task> userList, Storage newData){
        try {
            DukeException.checkDeadlineInput(userInput);
            String[] tokens = userInput.split(Pattern.quote(" /by "));
            Task newDeadline = new Deadline(tokens[0], tokens[1]);
            userList.add(newDeadline);
            DukeException.checkDateFormat(tokens[1]);
            tokens[1] = convertDate(tokens[1]);
            System.out.println("Got it. I've added this task:\n\t[D][\u2718] " + tokens[0].trim() + " (by: " + tokens[1].trim() + ")");
            newData.addIncompleteDeadline(tokens[0].trim(), tokens[1].trim());
            System.out.println("Now you have " + userList.size() + " tasks in the list");
        } catch (DukeException | ParseException | IOException e) {
            System.out.println("e r r o r   f o u n d\n" + e);
        }
    }

    public void addEvent(String userInput, ArrayList<Task> userList, Storage newData){
        try {
            DukeException.checkEventInput(userInput);
            String[] tokens = userInput.split(Pattern.quote(" /at "));
            Task newEvent = new Event(tokens[0], tokens[1]);
            userList.add(newEvent);
            DukeException.checkDateFormat(tokens[1]);
            tokens[1] = convertDate(tokens[1]);
            System.out.println("Got it. I've added this task:\n\t[E][\u2718] " + tokens[0].trim() + " (at: " + tokens[1].trim() + ")");
            newData.addIncompleteEvent(tokens[0].trim(), tokens[1].trim());
            System.out.println("Now you have " + userList.size() + " tasks in the list");
        } catch (DukeException | ParseException | IOException e) {
            System.out.println("e r r o r   f o u n d\n" + e);
        }
    }
}
