package gok.simplepdf_GUI;

import gok.data_logic.AllFiles;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import javax.swing.*;

public class mainUsingListView_Controller {
    @FXML
    ListView<String> myListView;
    @FXML
    Button addFileBtn, deleteFileBtn, moveUpBtn, moveDownBtn, mergeFilesBtn;
    AllFiles files;
    String currentFood;

    public void mergeFiles(ActionEvent actionEvent) {
    }

    public void getFiles(ActionEvent actionEvent) {
        try {
            FileChooser fileChooser = new FileChooser();
            files = new AllFiles(fileChooser.showOpenMultipleDialog(null));
            String[] filesNames = files.getNames();
            myListView.getItems().addAll(filesNames);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Select One File At Least!!");
        }
    }

    public void deleteFile(ActionEvent actionEvent) {
        if (files == null) {
            return;
        }
        if (myListView.getSelectionModel().getSelectedIndex() == -1) {
            return;
        }
        int selected = myListView.getSelectionModel().getSelectedIndex();
        myListView.getItems().remove(selected);
    }

    public void moveDown(ActionEvent actionEvent) {
        if (files == null) {
            return;
        }
        if (myListView.getSelectionModel().getSelectedIndex() == -1) {
            return;
        }
        int index = myListView.getSelectionModel().getSelectedIndex();
        myListView.getItems().remove(0, myListView.getItems().size());
        index = files.move(index, 'd');
        myListView.getItems().addAll(files.getNames());
        myListView.getSelectionModel().select(index);
    }

    public void moveUp(ActionEvent actionEvent) {
        if (files == null) {
            return;
        }
        if (myListView.getSelectionModel().getSelectedIndex() == -1) {
            return;
        }
        int index = myListView.getSelectionModel().getSelectedIndex();
        myListView.getItems().remove(0, myListView.getItems().size());
        index = files.move(index, 'u');
        myListView.getItems().addAll(files.getNames());
        myListView.getSelectionModel().select(index);
    }

}
