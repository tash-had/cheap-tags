import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TagScreenViewController implements Initializable {

    public static ArrayList<String> myData = new ArrayList<>();

    @FXML
    Button add;

    @FXML
    Button delete;

    @FXML
    Button back;

    @FXML
    TextField tagInput;

    @FXML
    ListView<String> tagView;

    @FXML
    Pane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tagView.getItems().add("Example");
    }

    @FXML
    public void addButtonClicked(){
        if (tagInput.getText() != null){
            tagView.getItems().add(tagInput.getText());
            tagInput.clear();
        }
    }

    @FXML
    public void deleteButtonClicked(){
        int index = tagView.getSelectionModel().getSelectedIndex();
        if (index > -1){
            tagView.getItems().remove(index);
        }
    }

    @FXML
    public void backButtonClicked(){
        PrimaryStageManager.setScreen("Cheap Tags", "home_screen_view.fxml");
    }
}
