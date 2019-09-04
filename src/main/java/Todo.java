import java.io.IOException;
import java.util.ArrayList;

public class Todo extends Task {
    Todo(String description) {
        super(description); //this is to get description from parent class
    }

    public String getType(){
        return ("[T]");
    }

    static void addTodo(String userInput, ArrayList<Task> userList, Storage newData){
        try {
            DukeException.checkDescription(userInput);
            Task newTodo = new Todo(userInput);
            System.out.println("Got it. I've added this task:\n\t[T][\u2718] " + userInput.trim());
            userList.add(newTodo);
            newData.addIncompleteTodo(userInput.trim());
            System.out.println("Now you have " + userList.size() + " tasks in the list");
        } catch (DukeException | IOException e) {
            System.out.println("e r r o r   f o u n d\n" + e);
        }
    }
}