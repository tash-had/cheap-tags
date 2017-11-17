package activities;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import managers.ImageFileOperationsManager;
import managers.PrimaryStageManager;
import managers.TagManager;
import managers.UserDataManager;
import model.ImageFile;
import model.Tag;
import utils.Alerts;
import utils.ConfigureJFXControl;


public class BrowseImageFilesViewController implements Initializable {
    private static File targetDirectory;

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

    @FXML
    Button ChooseImage;

    ArrayList<File> allImages = new ArrayList<>();
    ArrayList<String> allTags = new ArrayList<>();
    ArrayList<String> selectedTags = new ArrayList<>();
    static String[] acceptedExtensions = new String[]{"jpg"};

    /**
     * The file object that is the currently displayed image
     */
    File selectedFile = null;

    static FilenameFilter imgFilter = (dir, name) -> {
        for (String ext : acceptedExtensions)
            if (name.endsWith("." + ext)){
            return true;
            }
        return false;
    };

    @FXML
    Button rename;

    @FXML
    ListView<String> RevisionLog;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // clear
        allTags.clear();
        // import all tags from taglist to the scene

        for (Tag tag: TagManager.getTagList()){
            allTags.add(tag.toString());
        }

//        System.out.println(targetDirectory.getPath());

        ConfigureJFXControl.setFontOfLabeled("resources/fonts/Roboto-Regular.ttf", 20, Tags );

        ConfigureJFXControl.setFontOfLabeled("/resources/fonts/Roboto-Regular.ttf", 20, Tags );

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
        PrimaryStageManager.setScreen("Cheap Tags", "/activities/home_screen_view.fxml");
    }

    /**
     * When the user click the add button under Tags, the selected tag should be removed from Tags and added to exitingTags
     */

    @FXML
    public void addButtonClick() {
        String selectedTag = allTagsListView.getSelectionModel().getSelectedItem();
        if (selectedFile == null){
            Alerts.chooseFileAlert();
        }
        else if (allTagsListView.getItems().indexOf(selectedTag) > -1){
            allTagsListView.getItems().remove(selectedTag);
            existingTags.getItems().add(selectedTag);
            selectedTags.add(selectedTag);
        }

    }

    /**
     * Handle the selection of image from the imageSidePane. Show the image view of to screen
     * connect the image with choosing tags system
     */
    @FXML
    public void ChooseImageClick(){
        String selectedImage = imageSidePane.getSelectionModel().getSelectedItem();
        if (imageSidePane.getItems().indexOf(selectedImage) > -1){
            for (int i = 0; i < imageSidePane.getItems().size(); i++){
                if (selectedImage.equals(allImages.get(i).getName())){
                   Image image = new Image(allImages.get(i).toURI().toString());
                   selectedImageView.setImage(image);
                   NameOfFile.setText(selectedImage);
                   selectedFile = allImages.get(i);
                   break;
                }
            }
        }
    }

    /**
     * Rename the file name in users operating system
     */

    @FXML
    public void renameButtonClick(){
        if (selectedFile == null){
            Alerts.chooseFileAlert();
        }
        else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < selectedTags.size() - 1; i++) {
                sb.append("@" + selectedTags.get(i) + " ");
            }
            sb.append("@" + selectedTags.get(selectedTags.size() - 1) + ".jpg");

            //todo: need to track UDI, instead of name
            ImageFileOperationsManager.renameImageFile(UserDataManager.getImageFileWithName(selectedFile.getName()), sb.toString());
        }
    }

    /**
     * Delete the tags from selected tags
     * Put it back to all tags list
     */
    @FXML
    public void deleteButtonClick(){
        String selectedTag = existingTags.getSelectionModel().getSelectedItem();
        if (selectedFile == null){
            Alerts.chooseFileAlert();
        }
        else if (existingTags.getItems().size() > -1){
            existingTags.getItems().remove(selectedTag);
            selectedTags.remove(selectedTag);
            allTagsListView.getItems().add(selectedTag);
            allTags.add(selectedTag);
        }
    }

    @FXML
    public void moveImageButtonClick(){
        ImageFileOperationsManager.moveImageFile(UserDataManager.getImageFileWithName(selectedFile.getName()));
        /* TODO: after image is moved from this directory, remove its name from image pane on the right.

         */
        imageSidePane.getItems().remove(selectedFile.getName());
    }

}






