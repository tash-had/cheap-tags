import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
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


    /**
     * This method is used by "Move To" button in browse image files view.
     * @param targetParentPath: this function takes the string to target parent path;
     * @param theImage: the imagefile object which would be moved
     */
    public static void changeDirector(ImageFile theImage, Path targetParentPath){
        theImage.changeImageDirectory(targetParentPath);
    }


    /**
     * This method is used by "Uncheck" action of "Checkbox" in browse image files view.
     * @param theImage: the imagefile object which would be moved
     * @param removeThisTag: the tag which would be removed
     */
    public static void removeTag(ImageFile theImage, Tag removeThisTag){
        theImage.removeTagOnImage(removeThisTag);
    }

    /**
     * This method is used by "Check" action of "Checkbox" in browse image files view.
     * @param theImage: the imagefile object which would be moved
     * @param addThisTag: the tag which would be removed
     */
    public static void addTag(ImageFile theImage, Tag addThisTag){
        theImage.addTagOnImage(addThisTag);
    }





}
