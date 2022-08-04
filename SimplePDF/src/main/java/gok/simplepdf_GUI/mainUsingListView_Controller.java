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
import java.util.List;

public class mainUsingListView_Controller {
    @FXML
    ListView<String> myListView;
    @FXML
    Button addFileBtn, deleteFileBtn, moveUpBtn, moveDownBtn, mergeFilesBtn;
    AllFiles files;

    public void mergeFiles(ActionEvent actionEvent) {
        boolean fileSkipped = false;
        File outputPdfFile;
        PdfMerger pdfMerger = null;
        PdfDocument pdfDocument = null;
        FileChooser fileChooser = new FileChooser();
        try {
            String[] filePaths = files.getPaths();
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("PDF", "*.pdf");
            fileChooser.setInitialFileName("merged.pdf");
            fileChooser.getExtensionFilters().add(filter);
            fileChooser.setTitle("Choose a location to save your merged outputPdffile...");
            outputPdfFile = fileChooser.showSaveDialog(null);
            pdfDocument = new PdfDocument(new PdfWriter(outputPdfFile));
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
                    case "txt" -> {
                        tempPdf = toPdf.txtToPdf(new File(path));
                        pdfMerger.merge(tempPdf, 1, tempPdf.getNumberOfPages());
                        tempPdf.close();
                    }
                    default -> fileSkipped = true;
                }
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Choose Some Files First!!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Selected Files error!!");
        } finally {
            if (pdfMerger != null) pdfMerger.close();
            if (pdfDocument != null) pdfDocument.close();
            if (!(myListView.getItems().size() <= 0)) myListView.getItems().remove(0, myListView.getItems().size());
            files = null;
            if (fileSkipped) {
                JOptionPane.showMessageDialog(null, "Some Files were Skipped because of incompatible File Format!!");
            }
        }
    }

    private String checkPath(String path) {
        String[] arr = path.split("\\.");
        int li = arr.length - 1;
        if (arr[li].equalsIgnoreCase("pdf")) {
            return "pdf";
        }
        if (arr[li].equalsIgnoreCase("doc") || arr[li].equalsIgnoreCase("docx")) {
            return "doc";
        }

        if (arr[li].equalsIgnoreCase("jpg") || arr[li].equalsIgnoreCase("jpeg") || arr[li].equalsIgnoreCase("png") || arr[li].equalsIgnoreCase("bmp")) {
            return "img";
        }
        if (arr[li].equalsIgnoreCase("txt")) {
            return "txt";
        }
        return "other";
    }

    public void getFiles(ActionEvent actionEvent) {
        try {
            FileChooser fileChooser = new FileChooser();
            if (files == null) {
                files = new AllFiles(fileChooser.showOpenMultipleDialog(null));
                myListView.getItems().addAll(files.getNames());
            } else {
                List<File> list = fileChooser.showOpenMultipleDialog(null);
                files.appendMoreFiles(list);
                myListView.getItems().remove(0, myListView.getItems().size());
                myListView.getItems().addAll(files.getNames());
            }
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
