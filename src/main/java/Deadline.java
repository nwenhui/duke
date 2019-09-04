public class Deadline extends Task {

    public Deadline(String description, String due) {
        super(description); //this is to get description from parent class
        this.extra = due;
    }

    public String getType(){
        return ("[D]");
    }
}