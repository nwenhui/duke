import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class DukeException extends Exception {
    private DukeException(String s) {
        super(s); //s is the error message
    }

    /**
     * checks if command is valid
     * @param command command input by user
     * @throws DukeException
     */
    public static void checkCommand(String command) throws DukeException {
        String[] commandList = {"todo", "deadline", "event", "done", "list", "help", "clear", "find", "delete"};
        boolean flag = false;
        for (String s : commandList) {
            if (command.equalsIgnoreCase(s)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            throw new DukeException("ohno u entered an invalid command :( pls try again");
        }
    }

    /**
     * checks if there is a description added for the todo
     * @param input user input in command line
     * @throws DukeException
     */
    public static void checkDescription(String input) throws DukeException {
        if (input.isEmpty()) {
            throw new DukeException("ohno u did not enter a description :( pls try again");
        }
    }

    /**
     * checks if the task number that the user keyed in is valid
     * @param a task number keyed in by user
     * @param l task list
     * @throws DukeException
     */
    public static void checkTask(int a, ArrayList<Task> l) throws DukeException {
        if (a >= l.size() || a < 0) {
            throw new DukeException("ohno u entered an invalid task no. :( pls try again");
        } else {
            Task checkTask = l.get(a);
            if (checkTask.checkStatus()) {
                throw new DukeException("ohno the task is already completed :( pls try with another task number");
            }
        }
    }

    /**
     * checks if input for event is done in the right format
     * "/at" must be included
     * must include both description and date/time
     * @param input user input in command line
     * @throws DukeException
     */
    public static void checkEventInput(String input) throws DukeException {
        if (!input.contains(" /at ")){
            throw new DukeException("ohno u entered the event incorrectly :( pls try again in the format below:\n\tevent <description> /at <date> <time>");
        } else{
            String[] tokens = input.split(Pattern.quote(" /at "));
            if (tokens.length != 2){
                throw new DukeException("ohno u entered the event incorrectly :( pls try again in the format below:\n\tevent <description> /at <date> <time>");
            } else if (tokens[0].isBlank()){
                throw new DukeException("ohno u did not enter a description for this event :( pls try again in the format below:\n\tevent <description> /at <date> <time>");
            }
        }
    }

    /**
     * checks if input for deadline is done in the right format
     * "/by" must be included
     * must include both description and date/time
     * @param input user input in command line
     * @throws DukeException
     */
    public static void checkDeadlineInput(String input) throws DukeException {
        if (!input.contains(" /by ")){
            throw new DukeException("ohno u entered the deadline incorrectly :( pls try again in the format below:\n\tdeadline <description> /by <date> <time>");
        } else{
            String[] tokens = input.split(Pattern.quote(" /by "));
            if (tokens.length != 2){
                throw new DukeException("ohno u entered the deadline incorrectly :( pls try again in the format below:\n\tdeadline <description> /by <date> <time>");
            } else if (tokens[0].isBlank()){
                throw new DukeException("ohno u did not enter a description for this deadline :( pls try again in the format below:\n\tdeadline <description> /by <date> <time>");
            }
        }
    }

    /**
     * checks if input date is in right format
     * right format: d/mm/yyyy 24hr time
     * @param input input date
     * @throws DukeException
     */
    public static void checkDateFormat(String input) throws DukeException{
        boolean isValid;
        SimpleDateFormat format = new SimpleDateFormat("d/MM/yyyy HHmm");
        format.setLenient(false);
        try {
            format.parse(input);
            isValid = true;
        } catch (ParseException e){
            isValid = false;
        }
        if (!isValid){
            throw new DukeException("ohno u entered the date and time incorrectly :( pls try again in the format below:\n\t<d/mm/yyy> <24hr time>\n\teg:2/12/2019 1430");
        }
    }

    /**
     * checks if the task number that the user keyed in is valid
     * @param a task number keyed in by user
     * @param l task list
     * @throws DukeException
     */
    //function to check whether task "done" is valid
    public static void checkDelete(int a, ArrayList<Task> l) throws DukeException {
        if (a >= l.size() || a < 0) {
            throw new DukeException("ohno u entered an invalid task no. :( pls try again");
        }
    }
}
