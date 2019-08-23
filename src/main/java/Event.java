public class Event extends Task {

    public Event(String description, String time) {
        super(description); //this is to get description from parent class
        this.extra = time;
    }

    public String getType(){
        return ("[E]");
    }
}