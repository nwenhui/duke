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
}