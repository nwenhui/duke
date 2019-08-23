import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);

        //lvl 1 greet, echo, exit
        System.out.println("Hello! I'm Duke \nWhat can I do for you?");
        Scanner scan = new Scanner(System.in);
        String userInput = scan.nextLine();
        String bye = "bye";
        while (!userInput.equalsIgnoreCase(bye)){
            System.out.println(userInput);
            userInput = scan.nextLine();
        }
        if (userInput.equalsIgnoreCase(bye)){
            System.out.println("Bye. Hope to see you again soon!");
        }
    }
}
