package activities;

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

//    @FXML
//    ListView<String> imageSidePane;

//    @FXML
//    Button ChooseImage;

    @FXML
    TextField imageSearchBar;
    @FXML
    TilePane imageTilePane;

//    ArrayList<File> allImages = new ArrayList<>();
    ArrayList<String> allTags = new ArrayList<>();
    ArrayList<String> selectedTags = new ArrayList<>();
    static String[] acceptedExtensions = new String[]{"jpg"};

    private StringBuilder imageSearchPatternEnd;
    private static final Logger logger = Logger.getAnonymousLogger();

    private Collection<String> imageNames;

    /**
     * The file object that is the currently displayed image
     */
    File selectedFile;

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

        ConfigureJFXControl.setFontOfLabeled("resources/fonts/Roboto-Regular.ttf", 20, Tags );
        ConfigureJFXControl.setFontOfLabeled("/resources/fonts/Roboto-Regular.ttf", 20, Tags );
        ConfigureJFXControl.populateListViewWithArrayList(allTagsListView, allTags);

//        if (targetDirectory.isDirectory()){
//            for (File imgFile : targetDirectory.listFiles(imgFilter)){
//                allImages.add(imgFile);
//            }
//        }

//        for (File file : allImages){
//            imageSidePane.getItems().add(file.getName());
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
        if (allTagsListView.getItems().indexOf(selectedTag) > -1){
            allTagsListView.getItems().remove(selectedTag);
            existingTags.getItems().add(selectedTag);
            selectedTags.add(selectedTag);
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

    @FXML
    public void renameButtonClick(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < selectedTags.size()-1; i++){
            sb.append("@" + selectedTags.get(i) + " ");
        }
        sb.append("@" + selectedTags.get(selectedTags.size()-1) + ".jpg");

        //todo: need to track UDI, instead of name
        ImageFileOperationsManager.renameImageFile(UserDataManager.getImageFileWithName(selectedFile.getName()), sb.toString());
    }

    @FXML
    public void deleteButtonClick(){
        String selectedTag = existingTags.getSelectionModel().getSelectedItem();
        if (existingTags.getItems().size() > -1){
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
    }
    
    public void populateImageTilePane(){
        for (ImageFile imageFile : UserDataManager.getNameToImageFileSessionMap().values()){
            addImageToTilePane(imageFile);
        }
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
        imageView.setOnMouseClicked(event -> {
            try {
                selectedImageView.setImage(new Image(imageFile.getThisFile().toURI().toURL().toString(), true));
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

}






