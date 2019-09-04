import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {
    private Storage storage;
    private Ui ui;

    public Parser(Storage storage, Ui ui) {
        this.storage = storage;
        this.ui = ui;
    }

    public Commands parse(String userInput){
        return new Commands(storage, ui, userInput);
    }
}
