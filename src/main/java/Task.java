public class Task {
    protected String description;
    protected boolean isDone;
    protected String extra;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.extra = "";
    }

    String getStatusIcon() {
        return (isDone ? "[\u2713]" : "[\u2718]"); //return [tick] or [X] symbols
    }

    void markAsDone(){
        this.isDone = true;
    }

    public String getType(){
        return ("task");
    }

    boolean checkStatus(){
        return isDone;
    }
}
