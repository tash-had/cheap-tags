package activities;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import managers.StateManager;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.ConfigureJFXControl;
import utils.Dialogs;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import static managers.PrimaryStageManager.getPrimaryStageManager;



public class HomeScreenViewController implements Initializable {

    /**
     * A list of hyperlinks showing directories that have previously been opened.
     */
    @FXML
    ListView<Hyperlink> previouslyViewedListView;

    /**
     * The title above the list of previously viewed directories.
     */
    @FXML
    Label previouslyViewedLabel;

    /**
     * A button which asks the user to choose a directory, then opens images under that directory under a new screen.
     */
    @FXML
    Button openDirectoryButton;

    /**
     * A button which takes the user to the Tag screen.
     */
    @FXML
    Button myTagsButton;

    /**
     * A button which asks the user to choose a directory, which images will be imported, then asks the user to enter
     * Instagram login info, then imports all images from the chosen Instagram account to the user's computer. Then
     * the screen will open on that directory containing the instagram photos.
     */
    @FXML
    Button importFromInstagramBtn;
    // Current implementation of the button is commented out due to issues getting access from Instagram's API.
    // App needs to be submitted for Instagram's approval before proper access tokens can be granted.

    /**
     * A button which asks the user to choose a directory which the images will be imported to, then asks the user to
     * enter the tumblr URL where they want to download images from. After images are downloaded into the directory,
     * the screen will open on that directory containing tumblr photos.
     */
    @FXML
    Button tumblrBtn;

    /**
     * The background image of the home screen.
     */
    @FXML
    ImageView backgroundImage;

    /**
     * The logo "Cheap Tags"
     */
    @FXML
    ImageView logo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ConfigureJFXControl.populateListViewWithArrayList(previouslyViewedListView,
                getHyperlinkArrayList(StateManager.userData.getPreviousPathsVisited()));
        ConfigureJFXControl.setFontOfLabeled("/resources/fonts/Roboto-Regular.ttf",
                15, previouslyViewedLabel);
        ConfigureJFXControl.setFontOfLabeled("/resources/fonts/Roboto-Light.ttf",
                15, openDirectoryButton, myTagsButton, importFromInstagramBtn, tumblrBtn);
        ConfigureJFXControl.toggleHoverTextColorOfLabeled(Color.web("#2196fe"),
                Color.BLACK, openDirectoryButton, myTagsButton);
    }

    /**
     * Function to handle "Open Directory" button click on Home screen. Opens the chosen directory and moves to
     * the image-browsing screen.
     */
    public void openDirectoryClick(){
        File selectedFile = Dialogs.getDirectoryWithChooser();
        if (selectedFile != null) {
                switchToToBrowseImageFilesView(selectedFile);
        }
    }

    /**
     * Given an array of paths, get an arrayList of hyperlinked paths
     *
     * @param pathArray an array of paths
     * @return an arraylist of hyperlinked paths
     */
    private ArrayList<Hyperlink> getHyperlinkArrayList(String[] pathArray) {
        ArrayList<Hyperlink> hyperlinkArrayList = new ArrayList<>();
        for (String path : pathArray) {
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
    private Hyperlink getHyperlinkWithPathName(String path) {
        Hyperlink hyperlink = new Hyperlink(path);
        hyperlink.setOnAction(event -> switchToToBrowseImageFilesView(new File(path)));
        ConfigureJFXControl.toggleHoverTextColorOfLabeled(Color.BLUE, Color.BLACK, hyperlink);
        hyperlink.setTextFill(Color.BLACK);
        hyperlink.setUnderline(false);
        hyperlink.setVisited(false);
        ConfigureJFXControl.setFontOfLabeled("/resources/fonts/Roboto-Thin.ttf", 20, hyperlink);
        return hyperlink;
    }

    /**
     * Takes an File argument and opens that File in the image-browsing screen.
     *
     * @param directoryPath The directory that is to be opened.
     */
    private void switchToToBrowseImageFilesView(File directoryPath){
        StateManager.userData.addPathToVisitedList(directoryPath.getPath());
        BrowseImageFilesViewController.setNewTargetDirectory(directoryPath);
        if (StateManager.sessionData.getNameToImageFileMap().values().size() > 0){
            getPrimaryStageManager().setScreen("Browse Images - [~" + directoryPath.getAbsolutePath() + "]",
                    "/activities/browse_imagefiles_view.fxml");
        }else {
            Dialogs.showErrorAlert("No Files to Load", "Uh oh!", "We didn't find any image files" +
                    " in the directory you loaded. Please select another");
            openDirectoryClick();
        }
    }

    /**
     *
     */
    @FXML
    public void importFromInstagram(){
//        File chosenDirectory = Dialogs.getDirectoryWithChooser();
//
//        HttpGet httpGet = new HttpGet("https://api.instagram.com/v1/users/self/media/recent/?access_token=548320548.b2a4a69.db79a44000d7460db61a6186a6928da3");
////        TextInputDialog dialog = new TextInputDialog();
////        dialog.setContentText("Enter your Instagram id:");
////        Optional<String> input = dialog.showAndWait();
////        if (input != null) {
//
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//
//        try {
//            ArrayList<File> instagramImages = new ArrayList<>();
//            CloseableHttpResponse response = httpclient.execute(httpGet);
//            HttpEntity entity = response.getEntity();
//            BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
//            StringBuilder sb = new StringBuilder();
//            String line;
//            while ((line = br.readLine())!= null){
//                sb.append(line).append("\n");
//            }
//            JSONObject json = new JSONObject(sb.toString());
//            JSONArray arr = json.getJSONArray("data");
//            ArrayList<String> urlArray = new ArrayList<>();
//
//            // add all urls to our arraylist
//            for (int i = 0; i < arr.length(); i++){
//                JSONObject subJSON = arr.getJSONObject(i);
//                JSONObject imagesJSON = subJSON.getJSONObject("images");
//                JSONObject standardRes = imagesJSON.getJSONObject("standard_resolution");
//                String url = standardRes.getString("url");
//                urlArray.add(url);
//            }
//            for (String s : urlArray){
//                System.out.println(s);
//            }
//            // create BufferedImage object from url
//            // Change BufferedImage to File object
//            // add to File to directory chosen
//            int i = 1;
//            for (String urlString : urlArray){
//                URL url = new URL(urlString);
//                BufferedImage image = ImageIO.read(url);
//                System.out.println(image);
//                System.out.println(chosenDirectory.getAbsolutePath());
//
//                File outputfile = new File(chosenDirectory.getAbsolutePath() +File.separator + String.valueOf(i) + ".jpg");
//                ImageIO.write(image, "png", outputfile);
//
//                outputfile.getParentFile().mkdirs();
//                outputfile.createNewFile(); // if image doesn't already exist in directory, create it.
//
//                i++;
//            }
//            switchToToBrowseImageFilesView(chosenDirectory);
//            StateManager.userData.addPathToVisitedList(chosenDirectory.getPath());
//        } catch (IOException e) {
//            System.out.println("IOException");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * A function that handles when the import from tumblr button is clicked. Prompts user to choose a directory and
     * enter a tumblr URL. Imports first 20 images from the URL to the chosen directory and opens the image browsing
     * screen on that directory.
     */
    @FXML
    public void tumblrButtonClicked(){
        File chosenDirectory = Dialogs.getDirectoryWithChooser();
        if (chosenDirectory != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setContentText("Enter a tumblr blog:");
            Optional<String> input = dialog.showAndWait();
            System.out.println(input);
            if (input.isPresent()) {
                String blogName = input.get();

                try {
                    JSONObject json = blogToJSONObject(blogName);
                    JSONObject responsejson = json.getJSONObject("response");
                        JSONArray posts = responsejson.getJSONArray("posts");
                        //iterate through all posts, at each post, get photo array
                        ArrayList<String> urlArray = new ArrayList<>();
                        for (int i = 0; i < posts.length(); i++) {
                            JSONObject currPost = posts.getJSONObject(i);
                            JSONArray photoArray = currPost.getJSONArray("photos");
                            for (int j = 0; j < photoArray.length(); j++) {
                                JSONObject photoObj = photoArray.getJSONObject(j);
                                JSONArray photoSpecs = photoObj.getJSONArray("alt_sizes");
                                String photoUrlString = photoSpecs.getJSONObject(0).getString("url");
                                urlArray.add(photoUrlString);
                            }
                        }
                        // System.out.println(urlArray);
                        urlToImages(urlArray, chosenDirectory);

                        switchToToBrowseImageFilesView(chosenDirectory);
                        StateManager.userData.addPathToVisitedList(chosenDirectory.getPath());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Retrieves information about the tumblr page using tumblr API. Creates a JSON object from that and returns it.
     *
     * @param blogName A string of the tumblr URL
     *
     * @return Returns the JSONObject retrieved from the tumblr API.
     */
    private JSONObject blogToJSONObject(String blogName){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://api.tumblr.com/v2/blog/" + blogName + "/posts/photo?&api_key=3ty3TDhh79GPAJBoVy25768p81ApgqiyYTp59ugyD19ncgQdh0");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity entity = response.getEntity();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(entity.getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new JSONObject(sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Given a list of URLs containing only an image, retrieves image and writes a new File object to the chosen
     * directory with that image.
     *
     * @param urlArray The list of URLs as strings.
     * @param chosenDirectory The directory where the user wants the images written to.
     */
    private void urlToImages(ArrayList<String> urlArray, File chosenDirectory){
        // create BufferedImage object from url
        // Change BufferedImage to File object
        // add to File to directory chosen
        int i = 1;
        for (String urlString : urlArray) {
            URL url = null;
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            BufferedImage image = null;
            try {
                image = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            File outputfile = new File(chosenDirectory.getAbsolutePath() + File.separator + String.valueOf(i) + ".jpg");
            try {
                ImageIO.write(image, "png", outputfile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            outputfile.getParentFile().mkdirs();
            try {
                outputfile.createNewFile(); // if image doesn't already exist in directory, create it.
            } catch (IOException e) {
                e.printStackTrace();
            }

            i++;
        }
    }

    /**
     * Changes the current screen to the Tag screen.
     */
    public void openTagScreen(){
        getPrimaryStageManager().setScreen("My Tags", "/activities/tag_screen_view.fxml");
    }
}
