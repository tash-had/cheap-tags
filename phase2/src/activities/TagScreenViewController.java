package activities;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import managers.ImageFileOperationsManager;
import managers.PrimaryStageManager;
import managers.TagManager;
import model.ImageFile;
import model.Tag;
import org.omg.CORBA.INTERNAL;
import utils.Alerts;
import utils.ConfigureJFXControl;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        tagView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
     * When the selected tag associated with existing images, the program will show a warning box to ask user for permission.
     */
    @FXML
    public void deleteButtonClicked(){
        ArrayList<Integer> intArray = new ArrayList<>();
                intArray.addAll(tagView.getSelectionModel().getSelectedIndices());
        int deleteNum = 0;
        for (int i : intArray) {

            if ( i-deleteNum > -1) {
                Tag thisTag = tagView.getItems().get(i-deleteNum);
                if(thisTag.images.size() != 0 ){
                ButtonType renameReqResponse = Alerts.showYesNoAlert("Could Not Delete The Tag","This Tag Associates With " +thisTag.images.size()+ " Image",
                        "Are You Sure You Want To Delete?");
                if (renameReqResponse == ButtonType.YES){
                    for(ImageFile j : thisTag.images){
                        j.getTagList().remove(thisTag);

                        ArrayList<Tag> tempList = new ArrayList<>();
                        tempList.addAll(j.getTagList());

                        StringBuilder sb = new StringBuilder();

                        j.getTagList().clear(); //clear all tags, since .addAll adds everything again.

                        j.getTagList().addAll(tempList);

                        for (Tag tag : tempList) {
                            sb.append("@").append(tag).append(" ");
                        }
                        sb.append(j.getOriginalName()); //.getOriginalName returns a name with .jpg at the end
                        ImageFileOperationsManager.renameImageFile(j, sb.toString());
                    }
                    tagView.getItems().remove(i-deleteNum);
                    TagManager.getTagList().remove(thisTag);
                    deleteNum++;
                }
            }
            else{
                    tagView.getItems().remove(i-deleteNum);
                    TagManager.getTagList().remove(thisTag);
                    deleteNum++;
                }
            }
        }
        repopulateTagView();
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
//        String input = tagSearch.getText().toLowerCase();
//        if (input.equals("")){
//            repopulateTagView();
//        }
//        else{
//            for (int i = 0; i < tagView.getItems().size(); i++){
//                Tag curr = tagView.getItems().get(i);
//                if (input.length() <= curr.name.length()) {
//                    if (!curr.name.substring(0, input.length()).equals(input)) {
//                        tagView.getItems().remove(i);
//                    }
//                }
//                else {
//                    tagView.getItems().remove(i);
//                }
//            }
        String input = tagSearch.getText().toLowerCase();
        ArrayList<Tag> searchResult = new ArrayList<>();
        Pattern tagSearchPattern = Pattern.compile(input);
        Matcher tagSearchMatcher;
        tagView.getItems().clear();
        if (input.isEmpty()){
            searchResult.clear();
            repopulateTagView();
        }else {
            for (Tag tag: TagManager.getTagList()){
                tagSearchMatcher = tagSearchPattern.matcher(tag.toString().toLowerCase());
                if (tagSearchMatcher.find()){
                    searchResult.add(tag);
                }
            }
            tagView.getItems().addAll(searchResult);

        }
    }

    public void repopulateTagView() {
        tagView.getItems().clear();
        for (Tag tag : TagManager.getTagList()) {
            tagView.getItems().add(tag);
        }
    }
}
