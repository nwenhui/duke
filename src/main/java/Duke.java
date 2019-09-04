import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;

public class Duke{
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    //for storing data
    File dataFile = new File("/Users/wenhui/dukeyduke/data");
    File data = new File("/Users/wenhui/dukeyduke/data/duke.txt");

    public Duke() throws IOException {
        storage = new Storage(dataFile, data);
        ui = new Ui();
        tasks = new TaskList(storage.load());
        parser = new Parser(storage, ui);
    }

    public void run() throws IOException {
        ui.showWelcome();
        tasks.print();
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*\n");
        String userInput = ui.readCommand();
        while (!userInput.equalsIgnoreCase("bye")) {
            try {
                DukeException.checkCommand(userInput);
            } catch (DukeException e) {
                System.out.println("e r r o r   f o u n d\n" + e);
            }
            Commands c = parser.parse(userInput);
            c.execute(tasks, ui, storage);
            userInput = ui.readCommand();
        }
        ui.bye();
    }

    public static void main(String[] args) throws IOException{
        new Duke().run();
    }
}

