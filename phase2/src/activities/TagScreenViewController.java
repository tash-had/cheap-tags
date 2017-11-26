package activities;

import com.sun.xml.internal.xsom.impl.scd.Iterators;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import managers.PrimaryStageManager;
import managers.TagManager;
import model.Tag;
import org.omg.CORBA.INTERNAL;
import utils.Alerts;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class TagScreenViewController implements Initializable {

    /**
     * Adds a tag to the system with name corresponding to user input in the TextField. Can also be accessed by pressing
     * ENTER. Displays the added tag on the screen in tagView. Does not accept empty or duplicate tag names.
     */
    @FXML
    Button add;

    /**
     * Deletes the selected tag from the system. Removes the tag name from the screen.
     */
    @FXML
    Button delete;

    /**
     * Takes user back to home screen.
     */
    @FXML
    Button back;

    /**
     * A TextField where users can enter their desired tag names.
     */
    @FXML
    TextField tagInput;

    /**
     * A TextField for users to search tags
     */
    @FXML
    TextField tagSearch;

    /**
     * Displays currently existing tags on the screen.
     */
    @FXML
    ListView<Tag> tagView;

    /**
     * The base pane for the scene.
     */
    @FXML
    Pane pane;


    @Override
    public void initialize(URL location, ResourceBundle resources){
        Platform.runLater(() -> tagInput.requestFocus());
       // tagView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // clears tagView to prevent duplication after reinitializing the scene and re-adds all the tags from TagManager
            tagView.getItems().clear();
            for (Tag tag : TagManager.getTagList()) {
                tagView.getItems().add(tag);
            }
    }

    /**
     * Handles when add button is clicked. Creates a tag with name = text input and adds it to the tag list on screen.
     * Empty input cannot be added, also shows alert if user trying to create duplicate tag i.e. create another tag
     * with same name.
     */
    @FXML
    public void addButtonClicked(){
        // tagInput checks if text box is empty since we cant have a tag with empty string as name.
        if (tagInput.getText() != null && !tagInput.getText().equals("")) {

            // check if the input matches an already existing tag. If it exists, show an alert. Else, proceed
            // and add the tag.
            int duplicateExists = 0;
            for (Tag eachTag : TagManager.getTagList()) {
                if (eachTag.name.equals(tagInput.getText())) {
                    duplicateExists += 1;
                }
            }

            // duplicate is not 0, so there was an already existing tag which matched the name.
            if (duplicateExists != 0) {
                Alerts.showTagExistsAlert();
                tagInput.clear();
            }

            // else there are no duplicates, proceed with adding tag to the tag list.
            else {
                Tag newTag = new Tag(tagInput.getText());
                TagManager.addTag(newTag);
                tagView.getItems().add(newTag);
                tagInput.clear();
                tagInput.requestFocus();
            }
        }
    }

    /**
     * Handles when delete button is clicked. Removes selected Tag from the list on screen and removes selected Tag
     * from list in TagManager.
     */
    @FXML
    public void deleteButtonClicked(){
//        ObservableList<Integer> intArray = tagView.getSelectionModel().getSelectedIndices();
//        for (int i : intArray) {
            int i = tagView.getSelectionModel().getSelectedIndex();
            if ( i > -1) {
                Tag thisTag = tagView.getItems().remove(i);
                TagManager.getTagList().remove(thisTag);
            }
            repopulateTagView();
//        }
    }

    /**
     * Changes scene to home screen.
     */
    @FXML
    public void backButtonClicked(){
        PrimaryStageManager.setScreen("Cheap Tags", "/activities/home_screen_view.fxml");
    }

    /**
     * Calls add button if user presses ENTER key.
     *
     * @param ke key that the user has pressed.
     */
    @FXML
    public void handleEnterPressed(KeyEvent ke){
        if (ke.getCode() == KeyCode.ENTER){
            addButtonClicked();
        }
    }

    /**
     *
     */
    @FXML
    public void searchInputChanged(){
        String input = tagSearch.getText().toLowerCase();
        if (input.equals("")){
            repopulateTagView();
        }
        else{
            for (int i = 0; i < tagView.getItems().size(); i++){
                Tag curr = tagView.getItems().get(i);
                if (input.length() <= curr.name.length()) {
                    if (!curr.name.substring(0, input.length()).equals(input)) {
                        tagView.getItems().remove(i);
                    }
                }
                else {
                    tagView.getItems().remove(i);
                }
            }
        }
    }

    public void repopulateTagView() {
        tagView.getItems().clear();
        for (Tag tag : TagManager.getTagList()) {
            tagView.getItems().add(tag);
        }
    }
}
