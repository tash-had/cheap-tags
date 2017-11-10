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
     * @param target_parent_path: this function takes the string to target parent path;
     * @param the_image: the imagefile object which would be moved
     */
    public static void changeDirector(ImageFile the_image, Path target_parent_path){
        the_image.change_image_directory(target_parent_path);
    }


    /**
     * This method is used by "Uncheck" action of "Checkbox" in browse image files view.
     * @param the_image: the imagefile object which would be moved
     * @param remove_this_tag: the tag which would be removed
     */
    public static void removeTag(ImageFile the_image, Tag remove_this_tag){
        the_image.remove_tag_onimage(remove_this_tag);
    }

    /**
     * This method is used by "Check" action of "Checkbox" in browse image files view.
     * @param the_image: the imagefile object which would be moved
     * @param add_this_tag: the tag which would be removed
     */
    public static void addTag(ImageFile the_image, Tag add_this_tag){
        the_image.add_tag_onimage(add_this_tag);
    }





}
