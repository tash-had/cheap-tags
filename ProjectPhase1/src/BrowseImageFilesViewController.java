import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;

import javax.swing.text.html.ImageView;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;

public class BrowseImageFilesViewController implements Initializable {
    private static File targetDirectory;

    @FXML
    ListView<Hyperlink> allImagefilesListView;

    @FXML
    ListView<String> allTagsListView;

    @FXML
    ImageView selecetedImageView;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println(targetDirectory.getPath());
        ConfigureJFXControl.populateListViewWithArrayList(allTagsListView,DisplayTagsView.myData);



    }
    public static File getTargetDirectory() {
        return targetDirectory;
    }

    public static void setTargetDirectory(File directory) {
        targetDirectory = directory;
    }

}
