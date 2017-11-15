import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


public class BrowseImageFilesViewController implements Initializable {
    private static File targetDirectory;
    private static File currentDisplayedImage;

    @FXML
    ListView<String> allTagsListView;

    @FXML
    ListView<String> existingTags;

    @FXML
    Button ChangedDirectory;

    @FXML
    ImageView selectedImageView;

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

    @FXML
    ListView<String> imageSidePane;


    ArrayList<File> allImages = new ArrayList<>();
    ArrayList<String> allTags = new ArrayList<>();
    ArrayList<String> exitingTags = new ArrayList<>();
    static String[] acceptedExtensions = new String[]{"jpg"};

    static FilenameFilter imgFilter = (dir, name) -> {
        for (String ext : acceptedExtensions)
            if (name.endsWith("." + ext)){
            return true;
            }
        return false;
    };


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (Tag tag: TagManager.getTagList()){
            allTags.add(tag.toString());
        }


        System.out.println(targetDirectory.getPath());

        ConfigureJFXControl.setFontOfLabeled("resources/fonts/Roboto-Regular.ttf", 20, Tags );
        ConfigureJFXControl.populateListViewWithArrayList(allTagsListView, allTags);

        if (targetDirectory.isDirectory()){
            for (File imgFile : targetDirectory.listFiles(imgFilter)){
                allImages.add(imgFile);
            }
        }

        for (File file : allImages){
            imageSidePane.getItems().add(file.getName());
        }

        
    }

    public static File getTargetDirectory() {
        return targetDirectory;
    }

    public static void setTargetDirectory(File directory) {
        targetDirectory = directory;
    }


    /**
     * when the user click the back button, it should take users back to the main screen
     */
    @FXML
    public void backButtonClick(){
        PrimaryStageManager.setScreen("Cheap Tags", "home_screen_view.fxml");
    }

    /**
     * When the user click the add button under Tags, the selected tag should be removed from Tags and added to exitingTags
     */

    @FXML
    public void addButtonClick() {
        String selectedTag = allTagsListView.getSelectionModel().getSelectedItem();
        if (allTagsListView.getItems().indexOf(selectedTag) > -1)
            allTagsListView.getItems().remove(selectedTag);
            existingTags.getItems().add(selectedTag);
    }

    @FXML
    public void changeDirectoryButtonClicked(){
//        File selectedFile = PrimaryStageManager.getDirectoryWithChooser();
//        if (selectedFile != null) {
//            FileOperationsManager.changeImageDirectory(currentDisplayedImage, selectedFile.getPath());
//        }
    }



}






