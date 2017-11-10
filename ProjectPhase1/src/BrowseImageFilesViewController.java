import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class BrowseImageFilesViewController implements Initializable {
    private static File targetDirectory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(targetDirectory.getPath());
    }
    public static File getTargetDirectory() {
        return targetDirectory;
    }

    public static void setTargetDirectory(File directory) {
        targetDirectory = directory;
    }

}
