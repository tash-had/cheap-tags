package activities;

import com.oracle.javafx.jmx.json.JSONReader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import jdk.nashorn.internal.parser.JSONParser;
import managers.PrimaryStageManager;
import managers.StateManager;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Dialogs;
import managers.StateManager;
import utils.Dialogs;
import utils.ConfigureJFXControl;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
//import org.json.simple.JSONObject;

import static managers.PrimaryStageManager.getPrimaryStageManager;


public class HomeScreenViewController implements Initializable {

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

    @FXML
    Button tumblrBtn;

    @FXML
    ImageView backgroundImage;

    @FXML
    ImageView logo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//       UserDataGetter.loadDATA();

        Image logoImage = new Image("resources/images/logo_2.jpg", true);
        //homeScreenImageView.setImage(logoImage);

        ConfigureJFXControl.populateListViewWithArrayList(previouslyViewedListView,
                getHyperlinkArrayList(StateManager.userData.getPreviousPathsVisited()));
        ConfigureJFXControl.setFontOfLabeled("/resources/fonts/Roboto-Regular.ttf",
                15, previouslyViewedLabel);
        ConfigureJFXControl.setFontOfLabeled("/resources/fonts/Roboto-Light.ttf",
                15, openDirectoryButton, addTagsButton, importFromInstagramBtn, tumblrBtn);
        ConfigureJFXControl.toggleHoverTextColorOfLabeled(Color.web("#2196fe"),
                Color.BLACK, openDirectoryButton, addTagsButton);
    }

    /**
     * Function to handle "Open Directory" button click on Home screen.
     */
    public void openDirectoryClick(){
        File selectedFile = Dialogs.getDirectoryWithChooser();

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
//            StateManager.userData.addPathToVisitedList(selectedFile.getPath());
//            switchToToBrowseImageFilesView(selectedFile);

                //BrowseImageFilesViewController.setImagesToLoad(imagesToLoad);

                switchToToBrowseImageFilesView(selectedFile);
//            }
            //else imagesToLoad size != 0
//            else{
//                Dialogs.showErrorAlert("No Files to Load", "Uh oh!", "We didn't find any image files" +
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


    @FXML
    public void importFromInstagram() throws IOException {
//        File chosenDirectory = PrimaryStageManager.getDirectoryWithChooser();
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
                //HttpGet httpGet = new HttpGet("https://api.tumblr.com/v2/blog/elegant-autumn.tumblr.com/posts/photo?api_key=3ty3TDhh79GPAJBoVy25768p81ApgqiyYTp59ugyD19ncgQdh0");

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

    public void openTagScreen(){
        getPrimaryStageManager().setScreen("My Tags", "/activities/tag_screen_view.fxml");
    }
}
