package activities;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.*;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import java.util.Collection;
import java.util.Collections;

import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;

import managers.ImageFileOperationsManager;
import managers.PrimaryStageManager;
import managers.TagManager;
import managers.UserDataManager;
import model.ImageFile;
import model.Tag;
import utils.Alerts;
import utils.ConfigureJFXControl;


public class BrowseImageFilesViewController implements Initializable {

    @FXML
    ListView<String> imageSidePane;

    /**
     * A ListView of String representing all tags in the system (displayed on right pane).
     */
    @FXML
    ListView<Tag> allTagsListView;

    /**
     * A ListView of String displaying all tags associated with the chosen file.
     */
    @FXML
    ListView<Tag> existingTags;

    /**
     * A ListView of String displaying the past names that the File has had.
     */
    @FXML
    ListView<ArrayList<String>> revisionLog;

    /**
     * Displays the currently selected file.
     */
    @FXML
    ImageView selectedImageView;

    /**
     * Allows user to pick a new directory and put the chosen file in that directory.
     */
    @FXML
    Button changedDirectory;

    /**
     * Adds the selected tags to the files name.
     */
    @FXML
    Button rename;

    /**
     * Removes selected tag from the file's name. Once removed, it appears in allTagsListView again.
     */
    @FXML
    Button Delete;

    /**
     * Takes user back to home screen.
     */
    @FXML
    Button back;

    /**
     * Reverts selected image to the selected old name.
     */
    @FXML
    Button revert;

    /**
     * Labels allTagsListView as "Tags".
     */
    @FXML
    Label Tags;

    /**
     * Displays name of the currently selected file above itself.
     */
    @FXML
    TextField imageSearchBar;

    @FXML
    TilePane imageTilePane;

    @FXML
    Label nameOfSelectedFile;


    /**
     * Stores the selected directory File object.
     */
    private static File targetDirectory;

    /**
     * An ArrayList of File objects of all the images in the chosen directory.
     */
    private ArrayList<File> fileObjectsInDirectory = new ArrayList<>();
    
    private ObservableList<Tag> availableTagOptions;
    private ObservableList<Tag> existingTagsOnImageFile;
    private ObservableList<ArrayList<String>> selectedImageLog;
    private Collection<String> imageNames;
    private Collection<ImageFile> imagesToLoad;
    private boolean unsavedChanges = false;

    /**
     * A String array containing accepted image file types.
     */
    private static String[] acceptedExtensions = new String[]{"jpg"};

    private StringBuilder imageSearchPatternEnd;

    /**
     * The File object that is the currently displayed image.
     */
    private ImageFile selectedImageFile = null;

    /**
     * A FilenameFilter which filters out files that are not accepted image types.
     */
    private static FilenameFilter imgFilter = (dir, name) -> {
        for (String ext : acceptedExtensions)
            if (name.endsWith("." + ext)) {
                return true;
            }
        return false;
    };


    /**
     * TODO; imageFile rename oldName to revisionLog
     */


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // clear
//        stringsOfTags.clear();


        // import all tags from tagList to the scene

//        for (Tag tag : TagManager.getTagList()) {
//
//            stringsOfTags.add(tag.toString());
//        }

        ConfigureJFXControl.setFontOfLabeled("resources/fonts/Roboto-Regular.ttf", 20, Tags );
        ConfigureJFXControl.setFontOfLabeled("/resources/fonts/Roboto-Regular.ttf", 20, Tags );

        ConfigureJFXControl.setListViewToDisplayCustomObjects(existingTags);
        ConfigureJFXControl.setListViewToDisplayCustomObjects(allTagsListView);
        availableTagOptions = ConfigureJFXControl.populateListViewWithArrayList(allTagsListView,TagManager.getTagList());
        if (targetDirectory.isDirectory()) {
            Collections.addAll(fileObjectsInDirectory, targetDirectory.listFiles(imgFilter));
    }

//        if (targetDirectory.isDirectory()){
//            for (File imgFile : targetDirectory.listFiles(imgFilter)){
//                allImages.add(imgFile);
//            }
//        }

        imageNames = UserDataManager.getSessionImageFileNames();
        prepImageSearchRegex();
        imagesToLoad = ImageFileOperationsManager.fetchImageFiles(targetDirectory);
        imageTilePane.setOrientation(Orientation.HORIZONTAL);
        imageTilePane.setMaxWidth(Region.USE_PREF_SIZE);
        populateImageTilePane();

    }

    // Button click handlers
    /**
     * Handles when a tag is clicked and add the selected tag under the selected image and removes the tag from
     * allTagsListView.
     */
    @FXML
    public void addButtonClick() {
        Tag selectedTag = allTagsListView.getSelectionModel().getSelectedItem();
        if (selectedImageFile == null) {
            Alerts.chooseFileAlert();
        }else if (allTagsListView.getItems().indexOf(selectedTag) > -1) {
            if (selectedImageFile.getTagList().contains(selectedTag)){
                Alerts.fileContainsTagAlert();
            }
            else {
                availableTagOptions.remove(selectedTag);
                existingTagsOnImageFile.add(selectedTag);
                selectedImageFile.getTagList().add(selectedTag);
                unsavedChanges = true;
            }
        }
    }

    /**
     * Deletes the tag from selected tags when user clicks the button or clicks the tag. Deleted tag will reappear in
     * allTagsListView.
     */
    @FXML
    public void deleteButtonClick() {
        Tag selectedTag = existingTags.getSelectionModel().getSelectedItem();
        if (selectedImageFile == null) {
            Alerts.chooseFileAlert();
        } else if (existingTags.getItems().size() > 0 && selectedTag != null) {
            existingTagsOnImageFile.remove(selectedTag);
            availableTagOptions.add(selectedTag);
            selectedImageFile.getTagList().remove(selectedTag);
        }
    }

    /**
     * Renames the file name in the user's operating system.
     */
    @FXML
    public void renameButtonClick() {
        if (selectedImageFile == null) {
            Alerts.chooseFileAlert();
        }
        else {
            StringBuilder sb = new StringBuilder();
            for (Tag tag : existingTagsOnImageFile) {
                sb.append("@" + tag + " ");
            }
            sb.append(selectedImageFile.getOriginalName());
            selectedImageFile = ImageFileOperationsManager.renameImageFile(selectedImageFile, sb.toString());
            updateImageLog();
            unsavedChanges = false;
        }
    }

    private void updateImageLog(){
        selectedImageLog.clear();
        selectedImageLog.addAll(selectedImageFile.getOldName());
    }

    /*
    TODO: bug after you move file, go to new directory, moved file doesn't show up.
     */
    /**
     * Moves the image to a new directory which the user selects. After moving an image, the user can go to that new
     * directory or stay in the current directory.
     */
    @FXML
    public void moveImageButtonClick() {
        File movedFile = ImageFileOperationsManager.moveImageFile(selectedImageFile);
        File newDirectoryLocation = movedFile.getParentFile();
        ButtonType response = Alerts.showYesNoAlert("Go To Directory", null, "Would you like to go " +
                "to the new directory?");
        if (response == ButtonType.YES){
            // set screen to new directory
            setTargetDirectory(newDirectoryLocation);
            PrimaryStageManager.setScreen("Browse Images - [~" + newDirectoryLocation.getPath() + "]",
                    "/activities/browse_imagefiles_view.fxml");
            // update recently viewed on homescreen
            UserDataManager.addPathToVisitedList(newDirectoryLocation.toString());
        }
        else{
            setTargetDirectory(targetDirectory);
            PrimaryStageManager.setScreen("Browse Images - [~" + targetDirectory.getPath() + "]",
                    "/activities/browse_imagefiles_view.fxml");
        }
    }

    /**
     * Takes user to the home screen when back button is clicked.
     */
    @FXML
    public void backButtonClick() {
        PrimaryStageManager.setScreen("Cheap Tags", "/activities/home_screen_view.fxml");
    }

    // Miscellaneous

    static void setTargetDirectory(File directory) {
        UserDataManager.setSession(directory.getPath());
        targetDirectory = directory;
    }

    private void prepImageSearchRegex(){
        imageSearchPatternEnd = new StringBuilder(".*\\b(");
        for (String extension :ImageFileOperationsManager.ACCEPTED_EXTENSIONS){
            imageSearchPatternEnd.append(extension);
            imageSearchPatternEnd.append("|");
        }
        imageSearchPatternEnd.deleteCharAt(imageSearchPatternEnd.lastIndexOf("|"));
        imageSearchPatternEnd.append(")\\b");
    }

//
//    @FXML
//    public void ChooseImageClick(){
//        String selectedImage = imageSidePane.getSelectionModel().getSelectedItem();
//        if (imageSidePane.getItems().indexOf(selectedImage) > -1){
//            for (int i = 0; i < imageSidePane.getItems().size(); i++){
//                if (selectedImage.equals(allImages.get(i).getName())){
//                   Image image = new Image(allImages.get(i).toURI().toString());
//                   selectedImageView.setImage(image);
//                   NameOfFile.setText(selectedImage);
//                   selectedFile = allImages.get(i);
//                   break;
//                }
//            }
//        }
//    }


    // ImageTile Pane Methods

    public void populateImageTilePane(){
        for (ImageFile imageFile : imagesToLoad){
            addImageToTilePane(imageFile);
        }
    }

    private void addImageToTilePane(ImageFile imageFile){
        Image image = null;
        try {
            image = new Image(imageFile.getThisFile().toURI().toURL().toString(), 100, 100, true, true); //imageFile.getThisFile().toURI().toURL().toString(), 100, 100, true, false);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setOnMouseClicked(event -> imageClicked(imageFile, imageView));
        imageTilePane.getChildren().add(imageView);
    }

    private void imageClicked(ImageFile imageFile, ImageView sidePaneImageView){
        try {
            checkForUnsavedChanges();
            // Keep a reference to the selected image and set up right pane attributes for selected image
            selectedImageFile = imageFile;
            selectedImageView.setImage(new Image(selectedImageFile.getThisFile().toURI().toURL().toString(), true));
            nameOfSelectedFile.setText(selectedImageFile.getCurrentName());
            populateImageFileTagListViews();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void checkForUnsavedChanges(){
        if (unsavedChanges){
            ButtonType saveChangesResponse = Alerts.showYesNoAlert("Save Your Changes", "Unsaved Changes",
                    "You forgot to hit Set Tags! Would you like us to set your new tags?");
            if (saveChangesResponse == ButtonType.YES){
                renameButtonClick();
            }
            unsavedChanges = false;
        }
    }

    private void populateImageFileTagListViews(){
        if (existingTagsOnImageFile != null){
            existingTagsOnImageFile.clear();
        }
        existingTagsOnImageFile = ConfigureJFXControl.populateListViewWithArrayList(existingTags, selectedImageFile.getTagList());

        availableTagOptions.clear();
        availableTagOptions.addAll(TagManager.getTagList());
        for (Tag tag : existingTagsOnImageFile){
            availableTagOptions.remove(tag);
        }
        if (selectedImageLog != null){
            selectedImageLog.clear();
        }
        // Populate selectedImageLog with pre-existing logs
        selectedImageLog = ConfigureJFXControl.populateListViewWithArrayList(revisionLog,
                selectedImageFile.getOldName());
    }

    public void imageSearchTextChanged(){
        String input = imageSearchBar.getText();
        ArrayList<ImageFile> searchResultImageFileList = new ArrayList<>();

        String imageSearchPatternStart = ".*\\b(" + input + ")";
        Pattern imageSearchPattern = Pattern.compile(imageSearchPatternStart +imageSearchPatternEnd);
        Matcher imageSearchMatcher;
        if (input.equals("")){
            searchResultImageFileList.clear();
            populateImageTilePane();
        }else {
            for (String name:imageNames){
                imageSearchMatcher = imageSearchPattern.matcher(name);
                if (imageSearchMatcher.find()){
                    searchResultImageFileList.add(UserDataManager.getNameToImageFileSessionMap().get(name));
                }
            }
        }
        imageTilePane.getChildren().clear();
        for (ImageFile imf : searchResultImageFileList){
            addImageToTilePane(imf);
        }
    }

    /**
     * Reverts the current image to the selected previous name.
     */
    @FXML
    public void revertButtonClick(){
        ArrayList<String> specificRevision = revisionLog.getSelectionModel().getSelectedItem();
        selectedImageFile = ImageFileOperationsManager.renameImageFile(selectedImageFile, specificRevision.get(1));
        updateImageLog();
    }

}






