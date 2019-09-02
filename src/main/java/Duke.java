import java.io.*;
import java.text.ParseException;
import java.util.Scanner;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
//import org.w3c.dom.Text;
import javafx.scene.text.Text;

public class Duke extends Application{

    Scene welcomeScene, inputScene;

    public static void main(String[] args) throws IOException, ParseException, DukeException {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);

        //for storing data
        File dataFile = new File("/Users/wenhui/dukeyduke/data");
        File data = new File("/Users/wenhui/dukeyduke/data/duke.txt");
        ArrayList<Task> userList = new ArrayList<>(100);
        if (data.exists()) {
            readData read = new readData();
            read.openFile();
            userList = read.readFile();
            read.closeFile();
        } else {
            dataFile.mkdir();
            FileWriter writer = new FileWriter("/Users/wenhui/dukeyduke/data/duke.txt");
        }

        System.out.println("Hello! I'm Duke \nWhat can I do for you?\nenter help for a list of commands available\n\n*-*-*-*-*-*-*-*-*-*-*-*-*\nHere is your current task list:");
        TaskList.printList(userList);
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*\n");
        Scanner scan = new Scanner(System.in);
        String userInput = scan.next();
        char trash;
        while (!userInput.equalsIgnoreCase("bye")) {
            try {
                DukeException.checkCommand(userInput);
            } catch (DukeException e) {
                System.out.println("e r r o r   f o u n d\n" + e);
            }
            if (userInput.equalsIgnoreCase("help")) {
                TaskList.printCommands();
            } else if (userInput.equalsIgnoreCase("clear")) {
                inputData.clear();
            } else if (userInput.equalsIgnoreCase("list")) {
                System.out.println("Here are the tasks in your list:");
                TaskList.printList(userList);
            } else if (userInput.equalsIgnoreCase("done")) {
                userInput = scan.nextLine();
                TaskList.doneTask(userInput, userList);
            } else if (userInput.equalsIgnoreCase("delete")) {
                userInput = scan.nextLine();
                TaskList.deleteTask(userInput, userList);
            } else if (userInput.equalsIgnoreCase("find")) {
                userInput = scan.nextLine().trim();
                TaskList.findTask(userInput, userList);
            } else {
                inputData newData = new inputData();
                if (userInput.equalsIgnoreCase("todo")) {
                    userInput = scan.nextLine();
                    Todo.addTodo(userInput, userList, newData);
                } else if (userInput.equalsIgnoreCase("deadline")) {
                    userInput = scan.nextLine();
                    Deadline.addDeadline(userInput, userList, newData);
                } else if (userInput.equalsIgnoreCase("event")) {
                    userInput = scan.nextLine();
                    Event.addEvent(userInput, userList, newData);
                }
            }
            userInput = scan.next();
        }
        System.out.println("Bye. Hope to see you again soon!");
    }

    @Override
    public void start(Stage stage) throws IOException {
        File dataFile = new File("/Users/wenhui/dukeyduke/data");
        File data = new File("/Users/wenhui/dukeyduke/data/duke.txt");
        ArrayList<Task> userList = new ArrayList<>(100);
        if (data.exists()) {
            readData read = new readData();
            read.openFile();
            userList = read.readFile();
            read.closeFile();
        } else {
            dataFile.mkdir();
            FileWriter writer = new FileWriter("/Users/wenhui/dukeyduke/data/duke.txt");
        }

        stage.setTitle("duke :)");

        Label helloduke = new Label("Hello from\n____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n");
        Button start = new Button("start");
        start.setOnAction(e -> stage.setScene(inputScene));

        VBox layout1 = new VBox();
        layout1.getChildren().addAll(helloduke, start);
        welcomeScene = new Scene(layout1, 500, 500);


        Label taskLabel = new Label();
        taskLabel.setText("here is ur current list:");
        ScrollPane scrollPane = new ScrollPane();
        VBox showList = new VBox();
        Text title = new Text();
        title.setText((GUICommands.printList(userList)).toString());
        scrollPane.setContent(title);
        showList.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
        Button bye = new Button("bye");
        bye.setOnAction(e -> stage.close());

        showList.getChildren().addAll(taskLabel, scrollPane, bye);
        inputScene = new Scene (showList,500,500);


        stage.setScene(welcomeScene);
        stage.show();
    }

}

