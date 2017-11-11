package activities;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import managers.PrimaryStageManager;
import managers.TagManager;
import model.Tag;

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
    public void initialize(URL location, ResourceBundle resources) {
        Tag tag1 = new Tag("June");
        TagManager.addTag(tag1);
        tagView.getItems().clear();
        for (Tag tag : TagManager.getTagList()) {
            tagView.getItems().add(tag);
        }
    }

    @FXML
    public void addButtonClicked(){
        if (tagInput.getText() != null){
            Tag newTag = new Tag(tagInput.getText());
            TagManager.addTag(newTag);
            tagView.getItems().add(newTag);
            tagInput.clear();
            for (Tag t : TagManager.getTagList()){
                System.out.println(t);
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
        PrimaryStageManager.setScreen("Cheap Tags", "activities/home_screen_view.fxml");
    }
}
