package activities;

import StoreObject.UserDataGetter;
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

import managers.*;
import model.ImageFile;
import model.Tag;
import utils.Alerts;
import utils.ConfigureJFXControl;


public class BrowseImageFilesViewController implements Initializable {


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
     * Quick search for image files
     */
    @FXML
    TextField imageSearchBar;

    /**
     * Displays all image files in side bar
     */
    @FXML
    TilePane imageTilePane;


    /**
     * Displays name of the currently selected file above itself.
     */
    @FXML
    Label nameOfSelectedFile;

    @FXML
    ToggleButton toggleButton;


    @FXML
    ListView<String> imageNamesListView;

    ObservableList<String> imageFileNames;

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
        ConfigureJFXControl.setFontOfLabeled("resources/fonts/Roboto-Regular.ttf", 20, Tags);
        ConfigureJFXControl.setFontOfLabeled("/resources/fonts/Roboto-Regular.ttf", 20, Tags);

        ConfigureJFXControl.setListViewToDisplayCustomObjects(existingTags);
        ConfigureJFXControl.setListViewToDisplayCustomObjects(allTagsListView);
        availableTagOptions = ConfigureJFXControl.populateListViewWithArrayList(allTagsListView, TagManager.getTagList());
        if (targetDirectory.isDirectory()) {
            Collections.addAll(fileObjectsInDirectory, targetDirectory.listFiles(imgFilter));
        }
        toggleButton.setSelected(false);
        imagesViewToggle();


        prepImageSearchRegex();
        imagesToLoad = ImageFileOperationsManager.fetchImageFiles(targetDirectory);
        imageNames = StateManager.sessionData.getImageFileNames();

        imageTilePane.setOrientation(Orientation.HORIZONTAL);
        imageTilePane.setVgap(0);

        populateImageTilePane();
        rename.setDisable(true);

        if (existingTagsOnImageFile == null || existingTagsOnImageFile.size() == 0) {
            Delete.setDisable(true);
        }

//        if (imagesToLoad.size() != 0) {
//            UserDataManager.addPathToVisitedList(targetDirectory.getPath());
//        }
//
//        if (imagesToLoad.size() == 0) {
//            Alerts.showErrorAlert("No Files to Load", "Uh oh!", "We didn't find any image files" +
//                    " in the directory you loaded. Please select another");
//            File response = PrimaryStageManager.getDirectoryWithChooser();
//
//            if (response != null && ImageFileOperationsManager.fetchImageFiles(response).size() != 0) {
//                setTargetDirectory(response);
//                initialize(location, resources);
//                UserDataManager.addPathToVisitedList(response.getPath());
//            }
//
//            // response isn't null and is = 0
//            else if (response != null) {
//                while (response != null && ImageFileOperationsManager.fetchImageFiles(response).size() == 0) {
//                    Alerts.showErrorAlert("No Files to Load", "Uh oh!", "We didn't find any image files" +
//                            " in the directory you loaded. Please select another");
//                    response = PrimaryStageManager.getDirectoryWithChooser();
//                }
////                if (ImageFileOperationsManager.fetchImageFiles(response).size() != 0) {
//                // when while loop ends, they have chosen something that isn't null and empty
//                if (response == null){
//                    PrimaryStageManager.setScreen("Cheap Tags", "/activities/home_screen_view.fxml");
//                }
//                else{
//                    setTargetDirectory(response);
//                    initialize(location, resources);
//                    UserDataManager.addPathToVisitedList(response.getPath());
//                }
//            }
//            else {
////                PrimaryStageManager.setScreen("Cheap Tags", "/activities/home_screen_view.fxml");
////                HomeScreenViewController.initialize();
//                targetDirectory = null;
//                initialize(location, resources);
//                backButtonClick();
//            }
//        }
    }


    @FXML
    public void chooseImageClick(){
        checkForUnsavedChanges();
        String selectedImage = imageNamesListView.getSelectionModel().getSelectedItem();
        if (imageNamesListView.getItems().indexOf(selectedImage) > -1){
            selectedImageFile = StateManager.sessionData.getImageFileWithName(selectedImage);
            if (selectedImageFile != null){
                Image image = new Image(selectedImageFile.getThisFile().toURI().toString());
                selectedImageView.setImage(image);
                nameOfSelectedFile.setText(selectedImageFile.getCurrentName());
                populateImageFileTagListViews();
            }
        }
    }

    // Button click handlers
    /**
     * Handles when a tag is clicked and add the selected tag under the selected image and removes the tag from
     * allTagsListView.
     */
    @FXML
    public void addButtonClick(){
        Tag selectedTag = allTagsListView.getSelectionModel().getSelectedItem();
        if (selectedImageFile == null) {
            Alerts.chooseFileAlert();

        }
        else if (allTagsListView.getItems().indexOf(selectedTag) > -1) {
            if (selectedImageFile.getTagList().contains(selectedTag)){
                Alerts.fileContainsTagAlert();
            }
            else {
                availableTagOptions.remove(selectedTag);
                existingTagsOnImageFile.add(selectedTag);
                unsavedChanges = true;
                rename.setDisable(false);
                Delete.setDisable(false);
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
            // find the matching tag in the images tagList and remove that object
//            for (int i = 0; i < selectedImageFile.getTagList().size(); i++){
//                if (selectedTag.name.equals(selectedImageFile.getTagList().get(i).name)){
//                    selectedImageFile.getTagList().remove(i);
//                    break;
//                }
//            }
//            StringBuilder sb = new StringBuilder();
//            for (Tag tag : selectedImageFile.getTagList()){
//                sb.append("@" + tag + " ");
//            }
//            sb.append(selectedImageFile.getOriginalName());
//            selectedImageFile = ImageFileOperationsManager.renameImageFile(selectedImageFile, sb.toString());
//            updateImageLog();

            
            existingTagsOnImageFile.remove(selectedTag);
            availableTagOptions.add(selectedTag);

            if (existingTagsOnImageFile.size() == 0){
                Delete.setDisable(true);
            }
            unsavedChanges = true;
            rename.setDisable(false);
            Delete.setDisable(true);
        }
    }

    /**
     * Renames the file name in the user's operating system.
     * Modifies the tagsList of selected image, and stores the data
     */
    @FXML
    public void renameButtonClick() {
        if (selectedImageFile == null) {
            Alerts.chooseFileAlert();
        }
        else {
            selectedImageFile.updateTagHistory(selectedImageFile.getTagList()); //Add the tag list to the tag history before updating.
            System.out.println(selectedImageFile.getTagHistory());

            StringBuilder sb = new StringBuilder();

            selectedImageFile.getTagList().clear(); //clear all tags, since .addAll adds everything again.

            selectedImageFile.getTagList().addAll(existingTagsOnImageFile);
            for (Tag tag : existingTagsOnImageFile) {
                sb.append("@" + tag + " ");
            }
            sb.append(selectedImageFile.getOriginalName()); //.getOriginalName returns a name with .jpg at the end
            imageNames.remove(selectedImageFile.getCurrentName());
            if (imageFileNames != null){
                imageFileNames.remove(selectedImageFile.getCurrentName());
            }
            selectedImageFile = ImageFileOperationsManager.renameImageFile(selectedImageFile, sb.toString());
            updateImageLog();
            unsavedChanges = false;
            rename.setDisable(true);
            if (existingTagsOnImageFile.size() != 0) {
                Delete.setDisable(false);
            }
            nameOfSelectedFile.setText(selectedImageFile.getCurrentName());
            imageNames.add(selectedImageFile.getCurrentName());
            if (imageFileNames!=null){
                imageFileNames.add(selectedImageFile.getCurrentName());}
        }
    }

    @FXML
    public void imagesViewToggle(){
        if (toggleButton.isSelected()){
            imageTilePane.setVisible(false);
            imageSearchBar.setVisible(false);
            imageNamesListView.setVisible(true);
            ArrayList<String> imageNamesArrayList = new ArrayList<>();
            imageNamesArrayList.addAll(imageNames);
            if (imageFileNames != null){
                imageFileNames.clear();
            }
            imageFileNames = ConfigureJFXControl.populateListViewWithArrayList(imageNamesListView, imageNamesArrayList);
        }else {
            imageTilePane.setVisible(true);
            imageSearchBar.setVisible(true);
            imageNamesListView.setVisible(false);
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
        checkForUnsavedChanges();
        if (selectedImageFile == null){
            Alerts.chooseFileAlert();
        }
        else {
            File movedFile = ImageFileOperationsManager.moveImageFile(selectedImageFile);
            if (movedFile != null) {
                File newDirectoryLocation = movedFile.getParentFile();
                ButtonType response = Alerts.showYesNoAlert("Go To Directory", null, "Would you like to go " +
                        "to the new directory?");
                if (response == ButtonType.YES) {
                    // set screen to new directory
                    setTargetDirectory(newDirectoryLocation);
                    PrimaryStageManager.setScreen("Browse Images - [~" + newDirectoryLocation.getPath() + "]",
                            "/activities/browse_imagefiles_view.fxml");
                    // update recently viewed on home scene
                    selectedImageFile.setFile(movedFile);
                    StateManager.userData.addPathToVisitedList(newDirectoryLocation.toString());
                } else {
                    setTargetDirectory(targetDirectory);
                    PrimaryStageManager.setScreen("Browse Images - [~" + targetDirectory.getPath() + "]",
                            "/activities/browse_imagefiles_view.fxml");
                }
            }
        }
    }

    /**
     * Takes user to the home screen when back button is clicked.
     */
    @FXML
    public void backButtonClick() {
        checkForUnsavedChanges();
        PrimaryStageManager.setScreen("Cheap Tags", "/activities/home_screen_view.fxml");
    }

    // Miscellaneous
//    static void setImagesToLoad(Collection<ImageFile> input){
//        imagesToLoad = input;
//    }

    static void setTargetDirectory(File directory) {
        StateManager.sessionData.setSession(directory.getPath());
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

    // ImageTile Pane Methods

    private void populateImageTilePane(){
        for (ImageFile imageFile : imagesToLoad){
            addImageToTilePane(imageFile);
        }
    }

    private void addImageToTilePane(ImageFile imageFile){
        Image image = null;
        try {
            image = new Image(imageFile.getThisFile().toURI().toURL().toString(), 300, 300, true, true);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setOnMouseClicked(event -> imageClicked(imageFile));
        imageTilePane.getChildren().add(imageView);
    }

    private void imageClicked(ImageFile imageFile){
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

    /**
     * check if users save their changes or not
     */
    private void checkForUnsavedChanges(){
        if (unsavedChanges){
            ButtonType saveChangesResponse = Alerts.showYesNoAlert("Save Your Changes", "Save Changes?",
                    "You forgot to hit Set Tags! Would you like us to set your new tags?");
            if (saveChangesResponse == ButtonType.YES){
                renameButtonClick();
            }
            unsavedChanges = false;
        }
    }

    /**
     * Populate a list view of tags under the image file
     */
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
        String input = imageSearchBar.getText().toLowerCase().replace("@", "");
        ArrayList<ImageFile> searchResultImageFileList = new ArrayList<>();
        String fullPattern;
        if (input.startsWith("^") && input.endsWith("$")){
            fullPattern = input.substring(1, input.length()-1);
        }else if(input.startsWith("^")){
            // User is currently typing a regex. Must wait until they complete.
            return;
        }else {
            fullPattern = ".*\\b(" + Pattern.quote(input) + ")" +imageSearchPatternEnd.toString();
        }

        Pattern imageSearchPattern = Pattern.compile(fullPattern);
        Matcher imageSearchMatcher;
        imageTilePane.getChildren().clear();
        if (input.isEmpty()){
            searchResultImageFileList.clear();
            populateImageTilePane();
        }else {
            for (String name:imageNames){
                imageSearchMatcher = imageSearchPattern.matcher(name.toLowerCase());
                if (imageSearchMatcher.find()){
                    searchResultImageFileList.add(StateManager.sessionData.getImageFileWithName(name));
                }
            }
            for (ImageFile imf : searchResultImageFileList){
                addImageToTilePane(imf);
            }
        }
    }

    /**
     * Reverts the current image to the selected previous name.
     */
    @FXML
    public void revertButtonClick() {
        int indexOfRevision = revisionLog.getSelectionModel().getSelectedIndex();
        System.out.println(indexOfRevision);
        if (indexOfRevision != -1) {
            ArrayList<String> specificRevision = revisionLog.getSelectionModel().getSelectedItem();
            selectedImageFile.updateTagHistory(selectedImageFile.getTagList());
            selectedImageFile = ImageFileOperationsManager.renameImageFile(selectedImageFile, specificRevision.get(1));
            selectedImageFile.getTagList().clear();
            //update the selected imageFiles tagList with the tags associated with oldName.
            selectedImageFile.getTagList().addAll(selectedImageFile.getTagHistory().get(indexOfRevision));
            System.out.println(selectedImageFile.getTagList().toString());
            updateImageLog();
            nameOfSelectedFile.setText(selectedImageFile.getCurrentName());
            existingTagsOnImageFile.clear();
            existingTagsOnImageFile.addAll(selectedImageFile.getTagList());
            populateImageFileTagListViews();
            for (Tag tag : availableTagOptions) {
                if (selectedImageFile.getTagList().contains(tag)) {
                    availableTagOptions.remove(tag);
                }
            }
        }
//        selectedImageView.setImage(new Image(selectedImageFile.getThisFile().toURI().toURL().toString(), true));
    }

}






