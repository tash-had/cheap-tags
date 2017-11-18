package activities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;

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

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

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
    ListView<javafx.collections.ObservableList<String>> RevisionLog;

    /**
     * Displays the currently selected file.
     */
    @FXML
    ImageView selectedImageView;

    /**
     * Allows user to pick a new directory and put the chosen file in that directory.
     */
    @FXML
    Button ChangedDirectory;

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

//    @FXML
//    ListView<String> imageSidePane;

//    @FXML
//    Button ChooseImage;
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
    Label NameOfSelectedFile;

    /**
     * Stores the selected directory File object.
     */
    private static File targetDirectory;

    /**
     * An ArrayList of File objects of all the images in the chosen directory.
     */
    private ArrayList<File> fileObjectsInDirectory = new ArrayList<>();

    /**
     * An ArrayList of strings representing every tag's name.
     */
    private ObservableList<Tag> stringsOfTags;

    /**
     * An ArrayList of strings representing tag names of tags associated with the selected file.
     */
    private ObservableList<Tag> stringsOfSelectedTags;

    /**
     * A String array containing accepted image file types.
     */
    private static String[] acceptedExtensions = new String[]{"jpg"};

    private StringBuilder imageSearchPatternEnd;
    private static final Logger logger = Logger.getAnonymousLogger();

    private Collection<String> imageNames;

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

        stringsOfTags = ConfigureJFXControl.populateListViewWithArrayList(allTagsListView,TagManager.getTagList());

        if (targetDirectory.isDirectory()) {
            Collections.addAll(fileObjectsInDirectory, targetDirectory.listFiles(imgFilter));
    }
//        if (targetDirectory.isDirectory()){
//            for (File imgFile : targetDirectory.listFiles(imgFilter)){
//                allImages.add(imgFile);
//            }
//        }


        prepImageSearchRegex();
        ImageFileOperationsManager.fetchImageFiles(targetDirectory);
        imageTilePane.setOrientation(Orientation.VERTICAL);
        populateImageTilePane();

    }

    public static File getTargetDirectory() {
        return targetDirectory;
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

    static void setTargetDirectory(File directory) {
        targetDirectory = directory;
    }
    /**
     * Takes user to the home screen when back button is clicked.
     */
    @FXML
    public void backButtonClick() {
        UserDataManager.clearSession();
        PrimaryStageManager.setScreen("Cheap Tags", "/activities/home_screen_view.fxml");
    }

    /**
     * Handles when a tag is clicked and add the selected tag under the selected image and removes the tag from
     * allTagsListView.
     */
    @FXML
    public void addButtonClick() {
        Tag selectedTag = allTagsListView.getSelectionModel().getSelectedItem();
        if (selectedImageFile == null) {
            Alerts.chooseFileAlert();
        }

        else if (allTagsListView.getItems().indexOf(selectedTag) > -1) {
            if (selectedImageFile.getCurrentName().contains(selectedTag.name)){
                Alerts.fileContainsTagAlert();
            }
            else {
                stringsOfTags.remove(selectedTag);
                stringsOfSelectedTags.add(selectedTag);
                selectedImageFile.getTagList().add(selectedTag);
//            allTagsListView.getItems().remove(selectedTag);
//            existingTags.getItems().add(selectedTag);
//            stringsOfSelectedTags.add(selectedTag);
            }
        }

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


    /**
     * Renames the file name in the user's operating system.
     */
    @FXML
    public void renameButtonClick() {
        if (selectedImageFile == null) {
            Alerts.chooseFileAlert();

        }
        else {
//            ImageFile image = new ImageFile(selectedFile);
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < stringsOfSelectedTags.size(); i++) {
                sb.append("@" + stringsOfSelectedTags.get(i) + " ");
            }
            sb.append(selectedImageFile.getOriginalName());
            ImageFileOperationsManager.renameImageFile(selectedImageFile, sb.toString());
            displayRevisionLog(selectedImageFile);
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
//            existingTags.getItems().remove(selectedTag);
            stringsOfSelectedTags.remove(selectedTag);
//            allTagsListView.getItems().add(selectedTag);
            stringsOfTags.add(selectedTag);
            selectedImageFile.getTagList().remove(selectedTag);
        }
    }

    /**
     * Moves the image to a new directory which the user selects. After moving an image, the user can go to that new
     * directory or stay in the current directory.
     */
    @FXML
    public void moveImageButtonClick() {
        ImageFileOperationsManager.moveImageFile(UserDataManager.getImageFileWithName(selectedImageFile.getCurrentName()));
        /* TODO: after image is moved from this directory, ask user if they want to go to the new directory or stay.
        TODO: if they stay, make sure the moved image is removed from the left side pane displaying images.
        */
        // use alert goToDirectoryYesNo
    }
    
    public void populateImageTilePane(){
        for (ImageFile imageFile : UserDataManager.getNameToImageFileSessionMap().values()){
//            if (imageFile.getThisFile().getAbsolutePath().contains(targetDirectory.getName())){
                addImageToTilePane(imageFile);
//            }
        }
    }
    private void loadImageExistingTags(ImageFile imageFile){
        ConfigureJFXControl.populateListViewWithArrayList(existingTags, imageFile.getTagList());
    }
    private void addImageToTilePane(ImageFile imageFile){
        Image image = null;
        try {
            image = new Image(imageFile.getThisFile().toURI().toURL().toString(), 100, 100, true, false); //imageFile.getThisFile().toURI().toURL().toString(), 100, 100, true, false);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setUserData(imageFile);
        imageView.setOnMouseClicked(event -> {
            try {
                selectedImageView.setImage(new Image(imageFile.getThisFile().toURI().toURL().toString(), true));
                NameOfSelectedFile.setText(imageFile.getCurrentName());
                selectedImageFile = new ImageFile(imageFile.getThisFile());
                stringsOfSelectedTags = ConfigureJFXControl.populateListViewWithArrayList(existingTags, selectedImageFile.getTagList());

                loadImageExistingTags(imageFile);
                displayRevisionLog((ImageFile) imageView.getUserData());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
        imageTilePane.getChildren().add(imageView);
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
        ObservableList<String> specificRevision = RevisionLog.getSelectionModel().getSelectedItem();
        Logger.getAnonymousLogger().info(specificRevision.get(1));
        ImageFileOperationsManager.renameImageFile(selectedImageFile, specificRevision.get(1));
    }

    /**
     * Displays the revision log of the image selected.
     *
     * @param imageFileObject stores the revision log of the selected image.
     */
    public void displayRevisionLog(ImageFile imageFileObject){
        for (String[] eachLog : imageFileObject.getOldName()){
            ObservableList<String> cellItem = FXCollections.observableArrayList(eachLog[0], eachLog[1], eachLog[2]);
            RevisionLog.getItems().add(cellItem);
        }
    }

}






