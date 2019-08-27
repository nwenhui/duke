public class Todo extends Task {
    public Todo(String description) {
        super(description); //this is to get description from parent class
    }

    public String getType(){
        return ("[T]");
    }
}
