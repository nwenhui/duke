import java.util.Scanner;

public class Ui {
    private Scanner scan = new Scanner(System.in);


    public void showWelcome(){
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("Hello! I'm Duke \nWhat can I do for you?\nenter help for a list of commands available\n\n*-*-*-*-*-*-*-*-*-*-*-*-*\nHere is your current task list:");
    }

    public String readCommand(){
        return scan.next();
    }

    public String readDescription(){
        return scan.nextLine();
    }

    public void bye(){
        System.out.println("Bye. Hope to see you again soon!");
    }
}
