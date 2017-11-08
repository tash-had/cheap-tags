import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;

public class ImageFile{
    private String currentName;
    //oldName keeps track of all of the revision histories in the format of array [newname,previous name,timestamp]
    private ArrayList<String[]> oldName;
    private String UUID;

    public ImageFile(String actual_file_name){
        currentName = actual_file_name;
        oldName = new ArrayList<String[]>();
        UUID = FileManager.getNewId(); //what parameter does FileManager.getNewId take???

    public void change_image_name(String newName){
        Long timestamp = System.currentTimeMillis();
        String[] templog = {newName,currentName,timestamp.toString()};
        currentName = newName;
    }

}