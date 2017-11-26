package activities;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import managers.PrimaryStageManager;
import managers.StateManager;
import utils.Alerts;
import utils.ConfigureJFXControl;

import java.io.File;
import java.net.URL;
import java.util.*;


public class HomeScreenViewController implements Initializable{

    /**
     * A reference to the
     */
    @FXML
    ListView<Hyperlink> previouslyViewedListView;

    @FXML
    ImageView homeScreenImageView;

    @FXML
    Label previouslyViewedLabel;

    @FXML
    Button openDirectoryButton;

    @FXML
    Button addTagsButton;

    @FXML
    Button importFromInstagramBtn;

    @Override
    public void initialize (URL location, ResourceBundle resources) {
//       UserDataGetter.loadDATA();

        Image logoImage = new Image("resources/images/logo_2.jpg", true);
        homeScreenImageView.setImage(logoImage);

        ConfigureJFXControl.populateListViewWithArrayList(previouslyViewedListView,
                getHyperlinkArrayList(StateManager.userData.getPreviousPathsVisited()));
        ConfigureJFXControl.setFontOfLabeled("/resources/fonts/Roboto-Regular.ttf",
                15, previouslyViewedLabel);
        ConfigureJFXControl.setFontOfLabeled("/resources/fonts/Roboto-Light.ttf",
                15, openDirectoryButton, addTagsButton);
        ConfigureJFXControl.toggleHoverTextColorOfLabeled(Color.web("#2196fe"),
                Color.BLACK, openDirectoryButton, addTagsButton);
    }

    /**
     * Function to handle "Open Directory" button click on Home screen.
     */
    public void openDirectoryClick(){
        File selectedFile = PrimaryStageManager.getDirectoryWithChooser();

        if (selectedFile != null) {
//            Collection<ImageFile> imagesToLoad = ImageFileOperationsManager.fetchImageFiles(selectedFile);
//            BrowseImageFilesViewController.setImagesToLoad(imagesToLoad);
            //System.out.println(ImageFileOperationsManager.fetchImageFiles(selectedFile));
            //imagesToLoad.addAll(ImageFileOperationsManager.fetchImageFiles(selectedFile));

//            if (imagesToLoad.size() != 0) {
                //System.out.println(imagesToLoad.toString());
//                UserDataManager.addPathToVisitedList(selectedFile.getPath());
//                BrowseImageFilesViewController controller = fxmlLoader.getController();
//                System.out.println(imagesToLoad);
//                controller.setImagesToLoad(imagesToLoad);
                //BrowseImageFilesViewController.setImagesToLoad(imagesToLoad);
                StateManager.userData.addPathToVisitedList(selectedFile.getPath());
                switchToToBrowseImageFilesView(selectedFile);
//            }
             //else imagesToLoad size != 0
//            else{
//                Alerts.showErrorAlert("No Files to Load", "Uh oh!", "We didn't find any image files" +
//                        " in the directory you loaded. Please select another");
//                openDirectoryClick();
//            }
        }
//        UserDataSaver.storeData();
    }

    /**
     * Given an array of paths, get an arrayList of hyperlinked paths
     *
     * @param pathArray an array of paths
     * @return an arraylist of hyperlinked paths
     */
    private ArrayList<Hyperlink> getHyperlinkArrayList(String[] pathArray){
        ArrayList<Hyperlink> hyperlinkArrayList = new ArrayList<>();
        for (String path : pathArray){
            hyperlinkArrayList.add(0, getHyperlinkWithPathName(path));
        }
        return hyperlinkArrayList;
    }

    /**
     * Create a hyperlink with a given path, and set its action to  switch to the BrowseImageFilesView
     *
     * @param path the path to hyperlink
     * @return the hyperlinked path
     */
    private Hyperlink getHyperlinkWithPathName(String path){
        Hyperlink hyperlink = new Hyperlink(path);
        hyperlink.setOnAction(event -> switchToToBrowseImageFilesView(new File(path)));
        ConfigureJFXControl.toggleHoverTextColorOfLabeled(Color.BLUE, Color.BLACK, hyperlink);
        hyperlink.setTextFill(Color.BLACK);
        hyperlink.setUnderline(false);
        hyperlink.setVisited(false);
        ConfigureJFXControl.setFontOfLabeled("/resources/fonts/Roboto-Thin.ttf", 20, hyperlink);
        return hyperlink;
    }

    private void switchToToBrowseImageFilesView(File directoryPath){
        BrowseImageFilesViewController.setNewTargetDirectory(directoryPath);
        if (StateManager.sessionData.getNameToImageFileMap().values().size() > 0){
            PrimaryStageManager.setScreen("Browse Images - [~" + directoryPath.getPath() + "]",
                    "/activities/browse_imagefiles_view.fxml");
        }else {
            Alerts.showErrorAlert("No Files to Load", "Uh oh!", "We didn't find any image files" +
                    " in the directory you loaded. Please select another");
            openDirectoryClick();
        }
    }

    public void openTagScreen(){
        PrimaryStageManager.setScreen("My Tags", "/activities/tag_screen_view.fxml");
    }

    @FXML
    public void importFromInstagram(){

    }

}
