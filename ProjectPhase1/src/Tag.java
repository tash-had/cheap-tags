import java.util.ArrayList;
public class Tag {
    private String _name;
    public static ArrayList<ImageFile> _images = new ArrayList<ImageFile>();

    public Tag(String name) {
        _name = name;

    }

    public void addImage(ImageFile image){
        _images.add(image);

    }

    public void deleteImage(ImageFile image){
        _images.remove(image);
    }

    




}