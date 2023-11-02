package gok.simplepdf_GUI;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import gok.data_logic.AllFiles;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainController {
    @FXML
    AnchorPane mainframe;

    @FXML
    ListView<String> myListView;

    @FXML
    Button addFileBtn, deleteFileBtn, moveUpBtn, moveDownBtn, mergeFilesBtn;

    @FXML
    FontAwesomeIcon exitBtn, minimizeBtn;

    AllFiles files;

    public void mergeFiles() {
        FileChooser fileChooser = new FileChooser();

        try {
            String[] filePaths = files.getPaths();
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("PDF", "*.pdf");
            fileChooser.setInitialFileName("merged.pdf");
            fileChooser.getExtensionFilters().add(filter);
            fileChooser.setTitle("Choose a location to save your merged Pdf File...");

            File outputPdfFile = fileChooser.showSaveDialog(null);
            if (outputPdfFile != null) {
                PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outputPdfFile));
                PdfMerger pdfMerger = new PdfMerger(pdfDocument);

                for (String path : filePaths) {
                    String s = checkPath(path);
                    if ("pdf".equals(s)) {
                        PdfDocument tempPdf = new PdfDocument(new PdfReader(path));
                        pdfMerger = pdfMerger.merge(tempPdf, 1, tempPdf.getNumberOfPages());
                        tempPdf.close();
                    }
                }

                pdfMerger.close();
                pdfDocument.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("File Saved");
                alert.setHeaderText(null);
                alert.setContentText(outputPdfFile.getName() + " file Saved at " + outputPdfFile.getAbsolutePath());
                alert.showAndWait();
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Files Selected");
            alert.setHeaderText(null);
            alert.setContentText("Choose some files first!!");
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error while processing selected files.");
            alert.showAndWait();
        } finally {
            if (myListView.getItems().size() > 0) {
                myListView.getItems().clear();
            }
            files = null;
        }
    }

    private String checkPath(String path) {
        String[] arr = path.split("\\.");
        int li = arr.length - 1;
        if (arr[li].equalsIgnoreCase("pdf")) {
            return "pdf";
        }
        return "null";
    }

    public void getFiles() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));

            if (files == null) {
                files = new AllFiles(fileChooser.showOpenMultipleDialog(null));
                myListView.getItems().addAll(files.getNames());
            } else {
                List<File> list = fileChooser.showOpenMultipleDialog(null);
                files.appendMoreFiles(list);
                myListView.getItems().clear();
                myListView.getItems().addAll(files.getNames());
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Files Selected");
            alert.setHeaderText(null);
            alert.setContentText("Choose some files first!!");
            alert.showAndWait();
        }
    }

    public void deleteFile() {
        if (files == null || myListView.getSelectionModel().getSelectedIndex() == -1) {
            return;
        }

        int selected = myListView.getSelectionModel().getSelectedIndex();
        myListView.getItems().remove(selected);
        files.remove(selected);
    }

    public void moveDown() {
        if (files == null || myListView.getSelectionModel().getSelectedIndex() == -1) {
            return;
        }

        int index = myListView.getSelectionModel().getSelectedIndex();
        myListView.getItems().clear();
        index = files.move(index, 'd');
        myListView.getItems().addAll(files.getNames());
        myListView.getSelectionModel().select(index);
    }

    public void moveUp() {
        if (files == null || myListView.getSelectionModel().getSelectedIndex() == -1) {
            return;
        }

        int index = myListView.getSelectionModel().getSelectedIndex();
        myListView.getItems().clear();
        index = files.move(index, 'u');
        myListView.getItems().addAll(files.getNames());
        myListView.getSelectionModel().select(index);
    }

    public void exit(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void minimize(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}
