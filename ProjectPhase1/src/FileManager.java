import java.util.HashMap;
import java.util.UUID;

/**
 * Created by tash-had on 2017-11-03.
 */
public class FileManager {
    public static HashMap<String, ImageFile> idToImageFile;
    public static HashMap<String, String> imageNameToId;
    public HashMap<String, String> myUUIDs;
    public HashMap<String, ImageFile> myImageFiles;

    static String getNewId(){
        return UUID.randomUUID().toString();
    }

    static ImageFile getImageFileWithId(String id){
        return idToImageFile.get(id);
    }
    private static String getIdWithImageName(String imageName){
        return imageNameToId.get(imageName);
    }

    public FileManager(){
        myUUIDs = new HashMap<>();
        myImageFiles = new HashMap<>();
    }

    public boolean UUIDexists(String imageName){
        return myUUIDs.containsKey(imageName);
    }
    public String getUUID(String imageName){
        return myUUIDs.get(imageName);
    }
    public ImageFile getImageFile(String UUID){
        return myImageFiles.get(UUID);
    }

}


