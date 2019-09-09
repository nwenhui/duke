public class Parser {
    private Storage storage;
    private Ui ui;

    public Parser(Storage storage, Ui ui) {
        this.storage = storage;
        this.ui = ui;
    }

    /**
     * creates a command according to user input
     * @param userInput
     * @return
     */
    public Commands parse(String userInput){
        return new Commands(storage, ui, userInput);
    }
}
