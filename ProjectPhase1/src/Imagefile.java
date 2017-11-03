import java.util.ArrayList;
public class ImageFile{
    private String currentName;
    private String oldName;
    private ArrayList<String> timestamp;
    public ImageFile(String actual_file_name){
        currentName = actual_file_name;
        oldName = "";
    }
    public void change_image_name(String newName){
        oldName =  currentName;
        currentName = newName;
    }
}
