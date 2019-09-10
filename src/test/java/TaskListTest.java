import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;

public class TaskListTest {
    private ArrayList<Task> list = new ArrayList<>(100);
    private TaskList tasklist = new TaskList(list);

    @Test
    /**
     * junit test to test whether convertDate function works
     * should convert input date into the desired date format
     */
    public void convertDateTest() throws ParseException {
        assertEquals("2nd of December 2019, 06:00 PM", tasklist.convertDate("2/12/2019 1800"));
    }
}