package activities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import managers.ImageFileOperationsManager;
import managers.TagManager;
import model.Tag;
import utils.Log;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * the {@code RevisionLogViewController} class is the controller of activities/revison_log_view.fxml
 *
 * @author Caroline Ming
 */
public class RevisionLogViewController implements Initializable {

    /**
     * A table view of revision history
     */
    @FXML
    TableView<Log> revisionLog;

    /**
     * the column for current name in the table view
     */
    @FXML
    TableColumn<Log, String> currentName;

    /**
     * the column for old name in the table view
     */
    @FXML
    TableColumn<Log, String> oldName;

    /**
     * the column for time stamp in the table view
     */
    @FXML
    TableColumn<Log, String> timeStamp;

    /**
     * the text field for user to search for revision history
     */
    @FXML
    TextField revisionHistorySearchBar;

    /**
     * Revert selected image to the selected old name
     */
    @FXML
    Button revertTo;


    private ObservableList<Log> allLogsListView = FXCollections.observableArrayList();

    static BrowseImageFilesViewController browseController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allLogsListView.clear();

        for (ArrayList<String> al : browseController.selectedImageFile.getOldName()) {
            Log log = new Log(al.get(0), al.get(1), al.get(2));
            allLogsListView.add(log);
        }

        currentName.setCellValueFactory(new PropertyValueFactory<>("currentName"));
        oldName.setCellValueFactory(new PropertyValueFactory<>("oldName"));
        timeStamp.setCellValueFactory(new PropertyValueFactory<>("timeStamp"));

        revisionLog.setItems(allLogsListView);

    }

    /**
     * Save an instance of the BrowseImageFilesViewController
     *
     * @param controller
     * @return BrowseImageFilesViewController
     */
    public static BrowseImageFilesViewController setBrowseController(BrowseImageFilesViewController controller) {
        browseController = controller;
        return browseController;
    }

    /**
     * Revert the current image to the selected image file
     */
    public void setRevertTo() {
        int indexOfRevision = revisionLog.getSelectionModel().getSelectedIndex();
        if (indexOfRevision != -1) {
            String specificRevision = revisionLog.getSelectionModel().getSelectedItem().getOldName();
            browseController.selectedImageFile.updateTagHistory(browseController.selectedImageFile.getTagList());
            browseController.selectedImageFile = ImageFileOperationsManager.renameImageFile(browseController.selectedImageFile, specificRevision);

            browseController.selectedImageFile.getTagList().clear();


            String[] beginningName = browseController.selectedImageFile.getCurrentName().split("\\s");
            for (String i : beginningName) {
                if (i.startsWith("@")) {
                    String withoutSymbol = i.substring(1, i.length());
                    Tag findTheTag = TagManager.getTagByString(withoutSymbol);
                    if (findTheTag == null) {
                        Tag tempTag = new Tag(withoutSymbol);
                        browseController.selectedImageFile.getTagList().add(tempTag);
                        tempTag.images.add(browseController.selectedImageFile);
                        TagManager.addTag(tempTag);
                    } else {
                        findTheTag.images.add(browseController.selectedImageFile);
                        browseController.selectedImageFile.getTagList().add(findTheTag);
                    }
                }
            }

            browseController.nameOfSelectedFile.setText(browseController.selectedImageFile.getCurrentName());

            browseController.populateImageFileTagListViews();


        }
    }

    public void revisionLogSearchBar() {
        String input = revisionHistorySearchBar.getText().toLowerCase();

    }

}
