package gok.simplepdf_GUI;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;
import gok.data_logic.AllFiles;
import gok.data_logic.toPdf;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class mainUsingListView_Controller {
    @FXML
    ListView<String> myListView;
    @FXML
    Button addFileBtn, deleteFileBtn, moveUpBtn, moveDownBtn, mergeFilesBtn;
    AllFiles files;

    public void mergeFiles(ActionEvent actionEvent) {
        boolean fileSkipped = false;
        PdfMerger pdfMerger = null;
        PdfDocument pdfDocument = null;
        try {
            String[] filePaths = files.getPaths();
            File file = new File(files.getParent() + "\\merged.pdf");
            pdfDocument = new PdfDocument(new PdfWriter(file));
            pdfMerger = new PdfMerger(pdfDocument);
            for (String path : filePaths) {
                PdfDocument tempPdf;
                String s = checkPath(path);
                switch (s) {
                    case "doc" -> {
                        tempPdf = toPdf.docToPdf(new File(path));
                        pdfMerger.merge(tempPdf, 1, tempPdf.getNumberOfPages());
                        tempPdf.close();
                    }
                    case "img" -> {
                        tempPdf = toPdf.imgToPdf(new File(path));
                        pdfMerger.merge(tempPdf, 1, tempPdf.getNumberOfPages());
                        tempPdf.close();
                    }
                    case "pdf" -> {
                        tempPdf = new PdfDocument(new PdfReader(path));
                        pdfMerger.merge(tempPdf, 1, tempPdf.getNumberOfPages());
                        tempPdf.close();
                    }
                    default -> {
                        fileSkipped = true;
                    }
                }
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Choose Some Files First!!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Selected Files error!!");
        }
        if (pdfMerger != null) pdfMerger.close();
        if (pdfDocument != null) pdfDocument.close();
        if (!(myListView.getItems().size() <= 0)) myListView.getItems().remove(0, myListView.getItems().size());
        System.out.println("file saved at " + files.getParent());
        files = null;
        if (fileSkipped) {
            JOptionPane.showMessageDialog(null, "Some Files were Skipped because of incompatible File Format!!");
        }

    }

    private String checkPath(String path) {
        String[] arr = path.split("\\.");
        int li = arr.length - 1;
        if (arr[li].equalsIgnoreCase("pdf")) {
            return "pdf";
        }
        if (arr[li].equalsIgnoreCase("doc")
                || arr[li].equalsIgnoreCase("docx")) {
            return "doc";
        }

        if (arr[li].equals("jpg")
                || arr[li].equalsIgnoreCase("jpeg")
                || arr[li].equalsIgnoreCase("png")
                || arr[li].equalsIgnoreCase("bmp")) {
            return "img";
        }
        return "other";
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
        files.remove(selected);
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
