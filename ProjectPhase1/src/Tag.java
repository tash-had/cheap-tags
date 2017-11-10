import java.util.ArrayList;
public class Tag {
    public static String _name;
    public static ArrayList<ImageFile> _images = new ArrayList<ImageFile>();

    public Tag(String name) {
        _name = name;
    }

    /**
     * Add the image with this tag
     * @param image the image to be added to the arraylist
     */
    public static void addImage(ImageFile image){
        _images.add(image);

    }

    /**
     * Delete the image if the user delete the tag under the image
     * @param image the image to be deleted in the arraylist
     */
    public static void deleteImage(ImageFile image){
        _images.remove(image);
    }


    public String toString() {
        return _name;
    }
}