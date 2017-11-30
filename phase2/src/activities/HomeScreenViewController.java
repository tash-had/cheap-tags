package activities;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import managers.StageManager;
import managers.StateManager;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramUserFeedRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramFeedItem;
import org.brunocvcunha.instagram4j.requests.payload.InstagramFeedResult;
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

import java.util.ResourceBundle;

import static managers.PrimaryStageManager.getPrimaryStageManager;

/**
 * This class manages activities on the home screen.
 */
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

    @FXML
    Button masterLogButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ConfigureJFXControl.populateListViewWithArrayList(previouslyViewedListView,
                getHyperlinkArrayList(StateManager.userData.getPreviousPathsVisited()));
        ConfigureJFXControl.setFontOfLabeled("/resources/fonts/Roboto-Regular.ttf",
                15, previouslyViewedLabel);
        ConfigureJFXControl.setFontOfLabeled("/resources/fonts/Roboto-Light.ttf",
                15, openDirectoryButton, myTagsButton, importFromInstagramBtn, tumblrBtn);
        ConfigureJFXControl.toggleHoverTextColorOfLabeled(Color.web("#2196fe"),
                Color.BLACK, openDirectoryButton, myTagsButton, importFromInstagramBtn, tumblrBtn, masterLogButton);
    }

    /**
     * Function to handle "Open Directory" button click on Home screen. Opens the chosen directory and moves to
     * the image-browsing screen.
     */
    public void openDirectoryClick() {
        File selectedFile = Dialogs.getDirectoryWithChooser();
        if (selectedFile != null) {
            switchToToBrowseImageFilesView(selectedFile);
        }
    }

    /**
     * Given an array of paths, get an arrayList of hyperlinked paths
     *
     * @param pathArray an array of paths
     * @return an ArrayList of hyperlinked paths
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
    private void switchToToBrowseImageFilesView(File directoryPath) {
        StateManager.userData.addPathToVisitedList(directoryPath.getPath());
        BrowseImageFilesViewController.setNewTargetDirectory(directoryPath);
        if (StateManager.sessionData.getNameToImageFileMap().values().size() > 0) {
            getPrimaryStageManager().setScreen("Browse Images - [~" + directoryPath.getAbsolutePath() + "]",
                    "/activities/browse_imagefiles_view.fxml");
        } else {
            Dialogs.showErrorAlert("No Files to Load", "Uh oh!", "We didn't find any image files" +
                    " in the directory you loaded. Please select another");
            openDirectoryClick();
        }
    }

    public ArrayList<String> getInstragramDirectUrls(ArrayList<String> photoCodes) {
        ArrayList<String> directUrls = new ArrayList<>();
        for (String photoCode : photoCodes) {
            HttpGet httpGet = new HttpGet("https://api.instagram.com/oembed/?url=http://instagram.com/p/" +
                    photoCode);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            CloseableHttpResponse response = null;
            try {
                StringBuilder sb = new StringBuilder();
                response = httpclient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                JSONObject json = new JSONObject(sb.toString());
                String directURL = json.getString("thumbnail_url");
                directUrls.add(directURL);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
        return directUrls;
    }

    public void importFromInstagram(){
        File chosenDirectory = Dialogs.getDirectoryWithChooser();
        if (chosenDirectory != null){
            if (StateManager.sessionData.instagramReference == null){
                Dialogs.showInstagramLoginDialog();
            }
            ArrayList<String> codeList = getInstagramPhotoCodes();
            ArrayList<String> directUrls = getInstragramDirectUrls(codeList);
            writeUrlToFile(directUrls, chosenDirectory);
            switchToToBrowseImageFilesView(chosenDirectory);
        }
    }

    public ArrayList<String> getInstagramPhotoCodes(){
        ArrayList<String> instagramPhotoIds = new ArrayList<>();
        Instagram4j instagramRef = StateManager.sessionData.instagramReference;
        InstagramFeedResult feed = null;
        try {
            feed = instagramRef.sendRequest(new InstagramUserFeedRequest(instagramRef.getUserId()));
            if (feed != null && feed.getItems() != null){
                for (InstagramFeedItem item : feed.getItems()){
                    instagramPhotoIds.add(item.getCode());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instagramPhotoIds;
    }

    /**
     * A function that handles when the import from tumblr button is clicked. Prompts user to choose a directory and
     * enter a tumblr URL. Imports first 20 images from the URL to the chosen directory and opens the image browsing
     * screen on that directory.
     */
    @FXML
    public void tumblrButtonClicked() {
        File chosenDirectory = Dialogs.getDirectoryWithChooser();
        if (chosenDirectory != null) {
            String blogName = Dialogs.showTextInputDialog("Import From Tumblr blog", null,
                    "Please enter a Tumblr URL");
            if (blogName != null) {
                CloseableHttpResponse response  = getHttpResponse("https://api.tumblr.com/v2/blog/" + blogName + "/posts/photo?&api_key=3ty3TDhh79GPAJBoVy25768p81ApgqiyYTp59ugyD19ncgQdh0");
                if (response != null && response.getStatusLine().getStatusCode() == 200){ // response is not null and equal to 200 i.e. success code
                    JSONObject json = getJSONObject(response);
                    if (json != null) {
                        try {
                            JSONObject responseJson = json.getJSONObject("response");
                            JSONArray posts = responseJson.getJSONArray("posts");
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
                            writeUrlToFile(urlArray, chosenDirectory);
                            switchToToBrowseImageFilesView(chosenDirectory);
                            StateManager.userData.addPathToVisitedList(chosenDirectory.getPath());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    Dialogs.showErrorAlert("Error", "Not a valid tumblr URL", "The URL entered" +
                            " was not a valid tumblr blog. Please try again.");
                }
            }
        }
    }

    /**
     * Returns the HttpResponse object created from requesting GET from a uri.
     *
     * @param uriString string of the URI to be accessed
     *
     * @return returns the response object from that page
     */
    private CloseableHttpResponse getHttpResponse(String uriString){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uriString);
        try {
            return httpClient.execute(httpGet);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Given a valid HTTP response, creates a JSON object from given entity.
     *
     * @param response the Http response to retrieve entity from
     *
     * @return return the JSONObject created from http entity, or null if JSONObject not created
     */
    private JSONObject getJSONObject(CloseableHttpResponse response){
            HttpEntity entity = response.getEntity();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            try {
                return new JSONObject(sb.toString());
            }
            catch (JSONException je){
                je.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Given a list of URLs containing only an image, retrieves image and writes a new File object on the
     * user's computer to the chosen directory with that image.
     *
     * @param urlArray        The list of URLs as strings.
     *
     * @param chosenDirectory The directory where the user wants the images written to.
     */
    private void writeUrlToFile(ArrayList<String> urlArray, File chosenDirectory) {
        for (String urlString : urlArray) {
            try {
                URL url = new URL(urlString);
                try {
                    BufferedImage image = ImageIO.read(url);
                    File outputfile = new File(chosenDirectory.getAbsolutePath() + File.separator + getUniqueNameFromUrl(urlString));
                    ImageIO.write(image, "png", outputfile);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Return a String of the unique name of an image located at the end of URL (after the last '/' character).
     *
     * @param urlString the url containing link to image and unique name.
     *
     * @return a String of the unique name.
     */
    private String getUniqueNameFromUrl(String urlString){
        int startIndex = urlString.lastIndexOf("/");
        return urlString.substring(startIndex, urlString.length());
    }

    /**
     * Changes the current screen to the Tag screen.
     */
    public void openTagScreen() {
        getPrimaryStageManager().setScreen("My Tags", "/activities/tag_screen_view.fxml");
    }

    /**
     * Change the current screen to the tag screen
     */
    public void openMasterLog() {
        StageManager masterLog = new StageManager(new Stage());
        masterLog.setDefaultScreenHeight(400);
        masterLog.setDefaultScreenWidth(600);
        masterLog.setScreen("All Revision History", "/activities/master_log_view.fxml");
        masterLog.showStage();
    }
}