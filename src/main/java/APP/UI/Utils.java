package APP.UI;

import javafx.scene.control.Alert;

public class Utils {
    public static void ShowAlertDialogue(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static String checkPath(String path) {
        String[] arr = path.split("\\.");
        int li = arr.length - 1;
        if (arr[li].equalsIgnoreCase("pdf")) {
            return "pdf";
        }
        return "null";
    }
}
