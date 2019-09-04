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
}
