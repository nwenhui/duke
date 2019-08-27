import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Event extends Task {

    public Event(String description, String time) {
        super(description); //this is to get description from parent class
        this.extra = time;
    }

    public String getType(){
        return ("[E]");
    }

    public static void addEvent(String userInput, ArrayList<Task> userList, inputData newData){
        try {
            DukeException.checkEventInput(userInput);
            String[] tokens = userInput.split(Pattern.quote(" /at "));
            Task newEvent = new Event(tokens[0], tokens[1]);
            userList.add(newEvent);
            tokens[1] = Command.convertDate(tokens[1]);
            System.out.println("Got it. I've added this task:\n\t[E][\u2718] " + tokens[0].trim() + " (at: " + tokens[1].trim() + ")");
            newData.addIncompleteEvent(tokens[0].trim(), tokens[1].trim());
            System.out.println("Now you have " + userList.size() + " tasks in the list");
        } catch (DukeException | ParseException | IOException e) {
            System.out.println("e r r o r   f o u n d\n" + e);
        }
    }
}
