import javafx.scene.control.Label;

import java.awt.*;
import java.util.ArrayList;

public class GUICommands {
    //gui command to print out current task list
    public static StringBuilder printList(ArrayList<Task> l) {
        StringBuilder sb = new StringBuilder();
        if (l.isEmpty()) {
            sb.append("ur list is empty. pls input some tasks first\n");
        } else {
            int count = 0;
            String status;
            String type;
            for (int i = 0; i < l.size(); i++) {
                Task t = l.get(i);
                status = t.getStatusIcon();
                type = t.getType();
                if (type.contains("E")) {
                    sb.append(++count + ". " + type + status + " " + t.description + " (at: " + t.extra + ")\n");
                } else if (type.contains("D")) {
                    sb.append(++count + ". " + type + status + " " + t.description + " (by: " + t.extra + ")\n");
                } else {
                    sb.append(++count + ". " + type + status + " " + t.description + "\n");
                }
            }
        }
        return sb;
    }
}
