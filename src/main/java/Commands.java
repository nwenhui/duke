import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Commands {
    private Storage storage;
    private Ui ui;
    private String type;

    //constructor: to get a storage instance to be used throughout for all commands
    public Commands(Storage storage, Ui ui, String type){
        this.storage = storage;
        this.ui = ui;
        this.type = type;
    }

    /**
     * sets a task status to "complete"
     * @param userInput user input in the command line
     * @param userList current task list
     */
    public void doneTask(String userInput, ArrayList<Task> userList){
        String[] tokens = userInput.split(Pattern.quote(" "));
        int taskNum = Integer.parseInt(tokens[1]) - 1;
        try {
            DukeException.checkTask(taskNum, userList);
            Task doneTask = userList.get(taskNum);
            doneTask.markAsDone();
            String status = doneTask.getType();
            String type = "";
            if (doneTask.getType().contains("T")) {
                type = "todo";
            } else if (doneTask.getType().contains("E")) {
                type = "event";
            } else {
                type = "deadline";
            }
            storage.setCompletedTask(type, doneTask.description);
            System.out.println("Nice! I've marked this task as done: \n\t" + status + "[\u2713] " + doneTask.description);
        } catch (DukeException | IOException e) {
            System.out.println("e r r o r   f o u n d\n" + e);
        }
    }

    /**
     * deletes a specific task
     * @param userInput user input in the command line
     * @param userList current task list
     */
    public void deleteTask(String userInput, ArrayList<Task> userList) {
        String[] tokens = userInput.split(Pattern.quote(" "));
        int taskNum = Integer.parseInt(tokens[1]) - 1;
        try {
            DukeException.checkDelete(taskNum, userList);
            Task deleteTask = userList.get(taskNum);
            String type = deleteTask.getType();
            String status = deleteTask.getStatusIcon();
            System.out.println("Noted. I've removed this task:");
            if (type.contains("T")) {
                System.out.println("\t" + type + status + " " + deleteTask.description);
            } else if (type.contains("E")) {
                System.out.println("\t" + type + status + " " + deleteTask.description + " (at: " + deleteTask.extra + ")");
            } else if (type.contains("D")) {
                System.out.println("\t" + type + status + " " + deleteTask.description + " (by: " + deleteTask.extra + ")");
            }
            storage.deleteTask(type, deleteTask.description);
            userList.remove(taskNum);
        } catch (DukeException | IOException e) {
            System.out.println("e r r o r   f o u n d\n" + e);
        }
    }

    /**
     * finds task using keyword
     * @param userInput keyword the user is searching for
     * @param userList current task list
     */
    public void findTask(String userInput, ArrayList<Task> userList){
        System.out.println("searching for... " + userInput);
        boolean flag = true;
        boolean first = true;
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).description.contains(userInput)) {
                flag = false;
                if (first) {
                    System.out.println("Here are the matching tasks in your list:");
                    first = false;
                }
                String type = userList.get(i).getType();
                String status = userList.get(i).getStatusIcon();
                if (type.contains("T")) {
                    System.out.println(i + 1 + ". " + type + status + userList.get(i).description);
                } else if (type.contains("E")) {
                    System.out.println(i + 1 + ". " + type + status + userList.get(i).description + "(at: " + userList.get(i).extra + ")");
                } else if (type.contains("D")) {
                    System.out.println(i + 1 + ". " + type + status + userList.get(i).description + "(by: " + userList.get(i).extra + ")");
                }
            }
        }
        if (flag) {
            System.out.println("there are no matching tasks in ur list :(");
        }
    }

    /**
     * clears all current data everywhere (extra)
     * @param userList current task list
     * @throws IOException
     */
    public void clear(ArrayList<Task> userList) throws IOException {
        userList.clear();
        storage.clear();
    }

    /**
     * prints out the list of commands (extra)
     */
    public void printCommands(){
        System.out.println("todo: enter a task that needs to completed\ntodo <description>\n");
        System.out.println("event: enter a reminder for a upcoming event\nevent <description> /at <date> <time>\n");
        System.out.println("deadline: enter a reminder for a upcoming deadline\n deadline <description> /by <date> <time>\n");
        System.out.println("list: to list out all the tasks that have been saved\nlist\n");
        System.out.println("completed: to check off a completed task\ndone <task number>\n");
        System.out.println("clear: to clear off all task currently stored\nclear\n");
        System.out.println("\tformat for date: d/mm/yyy     format for time: 24hr\n" );
    }

    /**
     * executes the correct commands accordingly to user input
     * @param tasks
     * @param ui
     * @param storage
     * @throws IOException
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        if (type.equalsIgnoreCase("help")){
            printCommands();
        } else if (type.equalsIgnoreCase("clear")){
            clear(tasks.getList());
        } else if (type.equalsIgnoreCase("list")){
            System.out.println("Here are the tasks in your list:");
            tasks.print();
        } else if (type.equalsIgnoreCase("done")){
            doneTask(ui.readDescription(), tasks.getList());
        } else if (type.equalsIgnoreCase("delete")){
            deleteTask(ui.readDescription(), tasks.getList());
        } else if (type.equalsIgnoreCase("find")){
            findTask(ui.readDescription(), tasks.getList());
        } else{
            if (type.equalsIgnoreCase("todo")){
                Todo.addTodo(ui.readDescription(), tasks.getList(), storage);
            } else if (type.equalsIgnoreCase("deadline")){
                tasks.addDeadline(ui.readDescription(), tasks.getList(), storage);
            } else if (type.equalsIgnoreCase("event")){
                tasks.addEvent(ui.readDescription(), tasks.getList(), storage);
            }
        }
    }
}
