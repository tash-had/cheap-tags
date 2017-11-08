import java.util.ArrayList;
import java.util.UUID;

public class ImageFile{
    public String currentName;
    private String oldName;

    private ArrayList<String> timestamp;
    public String imageUUID;
    public ImageFile(String actual_file_name){
        currentName = actual_file_name;
        oldName = "";
        imageUUID = UUID.randomUUID().toString();
    }
    public void change_image_name(String newName){
        oldName =  currentName;
        currentName = newName;
    }

}