package gok.simplepdf;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;

public class mainController implements Initializable {

    @FXML
    TextArea txt;
    @FXML
    Button addBtn;
    @FXML
    Button delBtn;
    @FXML
    Button mergeBtn;

    public void getFiles(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Attach a file");
        List<File> selectedFile = fc.showOpenMultipleDialog(null);//Gets input files
        ListIterator<File> fileListIterator = selectedFile.listIterator();//Iterates the files list
        LinkedList<String> filePaths = new LinkedList<>();
//        ListIterator<String> filepathsListIterator= filePaths.listIterator();
        while (fileListIterator.hasNext()) {
            filePaths.add(fileListIterator.next().getAbsolutePath());
        }
        for (int i = 0; i < filePaths.size(); i++) {
            String text = txt.getText();
            txt.setText(text.equals("") ? filePaths.pop() + "\n" : text + filePaths.pop() + "\n");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txt.setText("""
                #
                -- Use Add file button to add multiple pdf files.
                \n-- Use Delete button to delete latest added file i.e. file listed on last line will be deleted.
                \n-- Use Merge button to merge selected files in top to bottom order.
                #""");

    }

    public void mergeFiles(ActionEvent actionEvent) {
    }

    public void deleteFile(ActionEvent actionEvent) {
    }

    public void clearTxtArea(MouseEvent mouseEvent) {
        if(txt.getText().equals("")){
            return;
        }
        if (txt.getText().charAt(0) == '#') {
            txt.setText("");
        }
    }
}
