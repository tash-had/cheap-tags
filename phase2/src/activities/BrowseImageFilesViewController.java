package activities;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import managers.ImageFileOperationsManager;
import managers.StateManager;
import managers.TagManager;
import model.ImageFile;
import model.Tag;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramUploadPhotoRequest;
import utils.Dialogs;
import utils.ConfigureJFXControl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static managers.PrimaryStageManager.getPrimaryStageManager;

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

    @FXML
    ImageView shareWithInstagramBtn;

    @FXML
    TextField TagSearchBar;


    private ObservableList<String> imageFileNames;

    /**
     * Stores the selected directory File object.
     */
    private static File targetDirectory;

//    /**
//     * An ArrayList of File objects of all the images in the chosen directory.
//     */
//    private ArrayList<File> fileObjectsInDirectory = new ArrayList<>();

    private ObservableList<Tag> availableTagOptions;
    private ObservableList<Tag> existingTagsOnImageFile;
    private ObservableList<ArrayList<String>> selectedImageLog;
    private Collection<String> imageNames;
//    private static Collection<ImageFile> imagesToLoad;
    private boolean unsavedChanges = false;

//    /**
//     * A String array containing accepted image file types.
//     */
//    private static String[] acceptedExtensions = new String[]{"jpg"};

    private StringBuilder imageSearchPatternEnd;


    /**
     * The File object that is the currently displayed image.
     */
    private ImageFile selectedImageFile = null;

//    /**
//     * A FilenameFilter which filters out files that are not accepted image types.
//     */
//    private static FilenameFilter imgFilter = (dir, name) -> {
//        for (String ext : acceptedExtensions)
//            if (name.endsWith("." + ext)) {
//                return true;
//            }
//        return false;
//    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Set fonts of some elements
        ConfigureJFXControl.setFontOfLabeled("resources/fonts/Roboto-Regular.ttf", 20, Tags);
        ConfigureJFXControl.setFontOfLabeled("/resources/fonts/Roboto-Regular.ttf", 20, Tags);

        // Enable listviews to be able to display objects with the same type as their type parameter
        ConfigureJFXControl.setListViewToDisplayCustomObjects(existingTags);
        ConfigureJFXControl.setListViewToDisplayCustomObjects(allTagsListView);

        // Populate the listview of tag options
        availableTagOptions = ConfigureJFXControl.populateListViewWithArrayList(allTagsListView, TagManager.getTagList());

        toggleButton.setSelected(false);
        imagesViewToggle();


        prepImageSearchRegex();

        imageNames = StateManager.sessionData.getImageFileNames();

        imageTilePane.setOrientation(Orientation.HORIZONTAL);
        imageTilePane.setVgap(0);
        populateImageTilePane();

        rename.setDisable(true);


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
            Dialogs.chooseFileAlert();

        }
        else if (allTagsListView.getItems().indexOf(selectedTag) > -1) {
            if (selectedImageFile.getTagList().contains(selectedTag)){
                Dialogs.fileContainsTagAlert();
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
            Dialogs.chooseFileAlert();
        } else if (existingTags.getItems().size() > 0 && selectedTag != null) {

            existingTagsOnImageFile.remove(selectedTag);
            availableTagOptions.add(selectedTag);

            if (existingTagsOnImageFile.size() == 0){
                Delete.setDisable(true);
            }
            unsavedChanges = true;
            rename.setDisable(false);
        }
    }

    /**
     * Renames the file name in the user's operating system.
     * Modifies the tagsList of selected image, and stores the data
     */
    @FXML
    public void renameButtonClick() {
        if (selectedImageFile == null) {
            Dialogs.chooseFileAlert();
        }
        else {
            selectedImageFile.updateTagHistory(selectedImageFile.getTagList()); //Add the tag list to the tag history before updating.

            StringBuilder sb = new StringBuilder();

            selectedImageFile.getTagList().clear(); //clear all tags, since .addAll adds everything again.

            selectedImageFile.getTagList().addAll(existingTagsOnImageFile);
            for (Tag tag : existingTagsOnImageFile) {
                sb.append("@").append(tag).append(" ");
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
            for(Tag i : selectedImageFile.getTagList()){
             i.images.add(selectedImageFile);
            }
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
            // set imageFileNames for the toggle that allows images to be viewed as text (file names)
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

    /**
     * Moves the image to a new directory which the user selects. After moving an image, the user can go to that new
     * directory or stay in the current directory.
     */
    @FXML
    public void moveImageButtonClick() {
        checkForUnsavedChanges();
        if (selectedImageFile == null){
            Dialogs.chooseFileAlert();
        }
        else {
            File movedFile = ImageFileOperationsManager.moveImageFile(selectedImageFile);
            if (movedFile != null) {
                File newDirectoryLocation = movedFile.getParentFile();
                ButtonType response = Dialogs.showYesNoAlert("Go To Directory", null, "Would you like to go " +
                        "to the new directory?");
                if (response == ButtonType.YES) {
                    // set screen to new directory
                    setNewTargetDirectory(newDirectoryLocation);
                    getPrimaryStageManager().setScreen("Browse Images - [~" + newDirectoryLocation.getPath() + "]",
                            "/activities/browse_imagefiles_view.fxml");
                    // update recently viewed on home scene
                    selectedImageFile.setFile(movedFile);
                    StateManager.userData.addPathToVisitedList(newDirectoryLocation.toString());
                } else {
                    setNewTargetDirectory(targetDirectory);
                    getPrimaryStageManager().setScreen("Browse Images - [~" + targetDirectory.getPath() + "]",
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
        getPrimaryStageManager().setScreen("Cheap Tags", "/activities/home_screen_view.fxml");
    }

    // Miscellaneous
//    static void setImagesToLoad(Collection<ImageFile> input){
//        imagesToLoad = input;
//    }

    static void setNewTargetDirectory(File directory) {
        StateManager.sessionData.startNewSession(directory);
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

    /**
     * Populate the ImageTilePane with all the images in this session
     */
    private void populateImageTilePane(){
        for (ImageFile imageFile : StateManager.sessionData.getNameToImageFileMap().values()){
            addImageToTilePane(imageFile);
        }
    }

    /**
     * Add a new image to the tilepane using its corresponding ImageFile object
     *
     * @param imageFile the ImageFile of the image to add
     */
    private void addImageToTilePane(ImageFile imageFile){
        Image image = null;
        try {
            // Set the Image object to show the image associated with this ImageFile
            image = new Image(imageFile.getThisFile().toURI().toURL().toString(), 300,
                    300, true, true);
        } catch (MalformedURLException e) {
//            e.printStackTrace();
            Dialogs.showErrorAlert("Gallery Error", "Error", "There was an error adding " +
            imageFile.getThisFile().getAbsolutePath() + " to the gallery. You sure it exists?");
        }
        // Construct an ImageView for the image
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setOnMouseClicked(event -> imageClicked(imageFile));

        // Setup VBox to display both image and a label with the imagename
        VBox tilePaneVBox = new VBox();

        // Construct a BEAUTIFUL label
        Label label = new Label(imageFile.getCurrentName());
        label.setPadding(new Insets(20, 0, 0, 0));
        tilePaneVBox.setAlignment(Pos.CENTER); 
        label.setTextFill(Color.web("#000000"));
        ConfigureJFXControl.toggleHoverTextColorOfLabeled(Color.web("#2196f3"), Color.web("#000000"), label);
        ConfigureJFXControl.setFontOfLabeled("/resources/fonts/Roboto-Regular.ttf", 17, label);

        // Add imageview and label to vbox + add vbox to tilepane
        tilePaneVBox.getChildren().addAll(imageView, label);
        imageTilePane.getChildren().add(tilePaneVBox);

    }

    /**
     * Process a click on an image on the tile pane
     *
     * @param imageFile the ImageFile that was clicked
     */
    private void imageClicked(ImageFile imageFile){
        try {
            // Before navigating to the clicked image, alert the user if they have unset tags
            checkForUnsavedChanges();

            // Keep a reference to the selected image and set up right pane attributes for selected image
            selectedImageFile = imageFile;
            selectedImageView.setImage(new Image(selectedImageFile.getThisFile().toURI().toURL().toString(), true));
            nameOfSelectedFile.setText(selectedImageFile.getCurrentName());
            populateImageFileTagListViews();

        } catch (MalformedURLException e) {
            Dialogs.showErrorAlert("Image Error", "Error", "There was an error fetching " +
                    imageFile.getThisFile().getAbsolutePath() + " from the gallery. You sure it exists?");
        }
    }

    /**
     * Check if the user has unsaved changes and alert them if they do.
     */
    private void checkForUnsavedChanges(){
        if (unsavedChanges){
            ButtonType saveChangesResponse = Dialogs.showYesNoAlert("Save Your Changes", "Save Changes?",
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
        availableTagOptions.removeAll(existingTagsOnImageFile);
        if (selectedImageLog != null){
            selectedImageLog.clear();
        }
        // Populate selectedImageLog with pre-existing logs
        selectedImageLog = ConfigureJFXControl.populateListViewWithArrayList(revisionLog,
                selectedImageFile.getOldName());
    }

    /**
     * Handle text changed on the image search bar
     */
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

    public void TagSearchTextChanged(){
        String input = TagSearchBar.getText().toLowerCase();
        ArrayList<Tag> searchResult = new ArrayList<>();
        Pattern tagSearchPattern = Pattern.compile(input);
        Matcher tagSearchMatcher;
        availableTagOptions.clear();
        if (input.isEmpty()){
            searchResult.clear();
            availableTagOptions = ConfigureJFXControl.populateListViewWithArrayList(allTagsListView, TagManager.getTagList());
        }else {
            for (Tag tag: TagManager.getTagList()){
                tagSearchMatcher = tagSearchPattern.matcher(tag.toString().toLowerCase());
                if (tagSearchMatcher.find()){
                    searchResult.add(tag);
                }
            }
            availableTagOptions.addAll(searchResult);

            }

    }

    /**
     * Reverts the current image to the selected previous name.
     */
    @FXML
    public void revertButtonClick() {
        int indexOfRevision = revisionLog.getSelectionModel().getSelectedIndex();
//        System.out.println(indexOfRevision);
        if (indexOfRevision != -1) {
            ArrayList<String> specificRevision = revisionLog.getSelectionModel().getSelectedItem();
            selectedImageFile.updateTagHistory(selectedImageFile.getTagList());
            selectedImageFile = ImageFileOperationsManager.renameImageFile(selectedImageFile, specificRevision.get(1));
            selectedImageFile.getTagList().clear();
            //update the selected imageFiles tagList with the tags associated with oldName.
            selectedImageFile.getTagList().addAll(selectedImageFile.getTagHistory().get(indexOfRevision));
//            System.out.println(selectedImageFile.getTagList().toString());
            updateImageLog();
            nameOfSelectedFile.setText(selectedImageFile.getCurrentName());
            existingTagsOnImageFile.clear();
            existingTagsOnImageFile.addAll(selectedImageFile.getTagList());
            for(Tag j : selectedImageFile.getTagList()){
                if(!TagManager.getTagList().contains(j)){
                    TagManager.addTag(j);
                }
            }
            populateImageFileTagListViews();
            for (Tag tag : availableTagOptions) {
                if (selectedImageFile.getTagList().contains(tag)) {
                    availableTagOptions.remove(tag);
                }
            }
        }
    }


    /**
     * A function to handle the click action of the ImageView (the instagram icon) in the BrowseImageFilesView
     * This function prompts the user for their Instagram credentials and a caption, and then posts the current selected
     * ImageFile to their Instagram.
     *
     * Note that this function uses Instagram's private API because their public API does not allow photo upload from
     * third parties.
     */
    public void shareWithInstagram() {
        if (selectedImageFile == null) {
            Dialogs.showErrorAlert("Must select an image", "Select an Image",
                    "You must select an image first!");
            return;
        }
        String[] instagramCreds = Dialogs.loginDialog("Login to Instagram",
                "Enter your Instagram credentials ...", null);
        // Disable logs
        turnOffLog4J();
        if (instagramCreds[0] != null && instagramCreds[1] != null
                && instagramCreds[0].length() > 0 && instagramCreds[1].length() > 0) {
            Instagram4j instagram = Instagram4j.builder().username(instagramCreds[0])
                    .password(instagramCreds[1]).build();
            instagram.setup();
            try {
                instagram.login();
                try {
                    String caption = Dialogs.showTextInputDialog("Instagram Caption", "Caption?",
                            "Enter a caption for your photo");
                    if (caption == null) {
                        caption = "";
                    }
                    InstagramUploadPhotoRequest photoRequest = new
                            InstagramUploadPhotoRequest(selectedImageFile.getThisFile(), caption);
                    instagram.sendRequest(photoRequest);
                } catch (IOException | RuntimeException e) {
                    Dialogs.showErrorAlert("Upload Error", "Error", "Uh oh! There was an error "
                            + "uploading your photo to Instagram. Make sure you've entered the right credentials and" +
                            "that your photo is of type JPEG.");
                }
            } catch (IOException e) {
                Dialogs.showErrorAlert("Invalid Credentials", "Invalid Creds",
                        "Please enter a valid username and password.");
            }
        } else {
            Dialogs.showErrorAlert("Invalid Input", "No Input",
                    "You must enter valid credentials");
        }
    }

    /**
     * A method to turn off all apache log4j loggers.
     */
    private void turnOffLog4J(){
        Enumeration loggers = LogManager.getCurrentLoggers();
        while (loggers.hasMoreElements()){
            Logger logger = (Logger) loggers.nextElement();
            logger.setLevel(Level.OFF);
        }
        LogManager.getRootLogger().setLevel(Level.OFF);
    }

}






