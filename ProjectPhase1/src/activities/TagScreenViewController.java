package activities;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import managers.PrimaryStageManager;
import managers.TagManager;
import model.Tag;
import utils.Alerts;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class TagScreenViewController implements Initializable {

    @FXML
    Button add;

    @FXML
    Button delete;

    @FXML
    Button back;

    @FXML
    TextField tagInput;

    @FXML
    ListView<Tag> tagView;

    @FXML
    Pane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources){

        // If TagManager is empty, show example tag
        if (TagManager.getTagList().isEmpty()){
            tagView.getItems().clear();
            tagView.getItems().add(new Tag("June"));
        }

        // else TagManager isn't empty, display all tags.
        else {
            tagView.getItems().clear();
            for (Tag tag : TagManager.getTagList()) {
                tagView.getItems().add(tag);
            }
        }
    }

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

    @FXML
    public void deleteButtonClicked(){
        int index = tagView.getSelectionModel().getSelectedIndex();
        if (index > -1){
            Tag thisTag = tagView.getItems().remove(index);
            TagManager.getTagList().remove(thisTag);
        }
    }

    @FXML
    public void backButtonClicked(){
        PrimaryStageManager.setScreen("Cheap Tags", "/activities/home_screen_view.fxml");
    }

    @FXML
    public void handleEnterPressed(KeyEvent ke){
        if (ke.getCode() == KeyCode.ENTER){
            addButtonClicked();
        }
    }

    /*
    TODO: no duplicate tags,
    TODO: no empty tags,
    TODO: set initial focus on tagInput
    TODO: decide if we want example tag on opening tag screen, if it is empty.
     */
}
