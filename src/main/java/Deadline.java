import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Deadline extends Task {

    public Deadline(String description, String due) {
        super(description); //this is to get description from parent class
        this.extra = due;
    }

    public String getType(){
        return ("[D]");
    }

    public static void addDeadline(String userInput, ArrayList<Task> userList, inputData newData){
        try {
            DukeException.checkDeadlineInput(userInput);
            String[] tokens = userInput.split(Pattern.quote(" /by "));
            Task newDeadline = new Deadline(tokens[0], tokens[1]);
            userList.add(newDeadline);
            tokens[1] = TaskList.convertDate(tokens[1]);
            System.out.println("Got it. I've added this task:\n\t[D][\u2718] " + tokens[0].trim() + " (by: " + tokens[1].trim() + ")");
            newData.addIncompleteDeadline(tokens[0].trim(), tokens[1].trim());
            System.out.println("Now you have " + userList.size() + " tasks in the list");
        } catch (DukeException | ParseException | IOException e) {
            System.out.println("e r r o r   f o u n d\n" + e);
        }
    }
}