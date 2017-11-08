import java.util.ArrayList;
public class Tag {
    private String _name;
    public static ArrayList<ImageFile> _images = new ArrayList<ImageFile>();

    public Tag(String name) {
        _name = name;

    }

    /**
     * Add the image with this tag
     * @param image the image to be added to the arraylist
     */
    public void addImage(ImageFile image){
        _images.add(image);

    }

    /**
     * Delete the image if the user delete the tag under the image
     * @param image the image to be deleted in the arraylist
     */
    public void deleteImage(ImageFile image){
        _images.remove(image);
    }


    public String toString() {
        return _name;
    }
}