package activities;

import StoreObject.UserDataGetter;
import StoreObject.UserDataSaver;
import activities.BrowseImageFilesViewController;

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
import managers.UserDataManager;
import utils.ConfigureJFXControl;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


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

    /*
     * TODO: Style buttons - material
     * add docstrings!
     * remove the "Users/tash-had/Pictures" initial entry in listview
     * */

    @Override
    public void initialize (URL location, ResourceBundle resources) {
//        UserDataGetter.loadDATA();

        Image logoImage = new Image("resources/images/logo_2.jpg", true);
        homeScreenImageView.setImage(logoImage);

        ConfigureJFXControl.populateListViewWithArrayList(previouslyViewedListView,
                getHyperlinkArrayList(UserDataManager.getPreviousPathsVisited()));
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
            UserDataManager.addPathToVisitedList(selectedFile.getPath());
            switchToToBrowseImageFilesView(selectedFile);
        }
 //       UserDataSaver.storeData();
    }

    /**
     * Given an array of paths, get an arraylist of hyperlinked paths
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
        BrowseImageFilesViewController.setTargetDirectory(directoryPath);
        PrimaryStageManager.setScreen("Browse Images - [~" + directoryPath.getPath() + "]",
                "/activities/browse_imagefiles_view.fxml");

    }

    public void openTagScreen(){
        PrimaryStageManager.setScreen("My Tags", "/activities/tag_screen_view.fxml");
    }


}
