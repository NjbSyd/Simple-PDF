package APP.UI;

import APP.LOGIC.AllFiles;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
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

import static APP.UI.Utils.ShowAlertDialogue;
import static APP.UI.Utils.checkPath;

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
        try {
            String[] filePaths = files.getPaths();
            if (filePaths.length == 0) {
                ShowAlertDialogue(Alert.AlertType.WARNING, "No Files Selected", "Choose some files first!!");
                return;
            }
            FileChooser fileChooser = new FileChooser();

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

                ShowAlertDialogue(Alert.AlertType.INFORMATION, "File Saved", "File Saved at " + outputPdfFile.getAbsolutePath());
            }
            if (!myListView.getItems().isEmpty()) {
                myListView.getItems().clear();
            }
            if (files != null) {
                files.clearFilesList();
            }
        } catch (NullPointerException e) {
            ShowAlertDialogue(Alert.AlertType.WARNING, "No Files Selected", "Choose some files first!!");
        } catch (Exception e) {
            ShowAlertDialogue(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }


    public void getFiles() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
            files = AllFiles.getInstance();
            files.AddFiles(fileChooser.showOpenMultipleDialog(null));
            myListView.getItems().clear();
            myListView.getItems().addAll(files.getNames());
        } catch (NullPointerException e) {
            ShowAlertDialogue(Alert.AlertType.WARNING, "No Files Selected", "Choose some files first!!");
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
