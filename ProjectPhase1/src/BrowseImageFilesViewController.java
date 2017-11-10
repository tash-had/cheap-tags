import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;

import java.io.BufferedReader;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


public class BrowseImageFilesViewController implements Initializable {
    private static File targetDirectory;


    @FXML
    ListView<String> allTagsListView;

    @FXML
    ListView<String> existingTags;

    @FXML
    Button ChangedDirectory;

    @FXML
    ImageView selecetedImageView;

    @FXML
    Button back;

    @FXML
    Label Tags;

    @FXML
    Button Add;

    @FXML
    Button Delete;

    @FXML
    Label NameOfFile;


    ArrayList<String> a = new ArrayList<>();



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        a.add("a");
        a.add("b");


        System.out.println(targetDirectory.getPath());
        ConfigureJFXControl.setFontOfLabeled("resources/fonts/Roboto-Regular.ttf", 20, Tags );
        ConfigureJFXControl.populateListViewWithArrayList(allTagsListView, a);



    }

    public static File getTargetDirectory() {
        return targetDirectory;
    }

    public static void setTargetDirectory(File directory) {
        targetDirectory = directory;
    }


    @FXML
    public void backButtonClick(){
        PrimaryStageManager.setScreen("Cheap Tags", "home_screen_view.fxml");
    }

    @FXML
    public void addButtonClick(){
        String selectedTag = allTagsListView.getSelectionModel().getSelectedItem();
        if (allTagsListView.getItems().indexOf(selectedTag) > -1)
            allTagsListView.getItems().remove(selectedTag);
            existingTags.getItems().add(selectedTag);

        }


}






