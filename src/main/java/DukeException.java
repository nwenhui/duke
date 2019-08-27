import java.util.ArrayList;

public class DukeException extends Exception {
    public DukeException(String s) {
        super(s); //s is the error message
    }

    //function to check whether input command is valid
    public static void checkCommand(String command) throws DukeException {
        String[] commandList = {"todo", "deadline", "event", "done", "list"};
        boolean flag = false;
        for (int i = 0; i < commandList.length; i++) {
            if (command.equalsIgnoreCase(commandList[i])) {
                flag = true;
            }
        }
        if (!flag) {
            throw new DukeException("ohno u entered an invalid command :( pls try again");
        }
    }

    //function to check whether there is description added
    static void checkDescription(String input) throws DukeException {
        if (input.isEmpty()) {
            throw new DukeException("ohno u did not enter a description :( pls try again");
        }
    }

    //function to check whether task "done" is valid
    static void checkTask(int a, ArrayList<Task> l) throws DukeException {
        if (a >= l.size() || a < 0) {
            throw new DukeException("ohno u entered an invalid task no. :( pls try again");
        } else {
            Task checkTask = l.get(a);
            if (checkTask.checkStatus()) {
                throw new DukeException("ohno the task is already completed :( pls try with another task number");
            }
        }
    }
}
