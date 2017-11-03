import java.util.HashMap;
import java.util.UUID;

/**
 * Created by tash-had on 2017-11-03.
 */
public class FileManager {
    public static HashMap<String, ImageFile> idToImage;
    public static HashMap<String, String> imageNameToId;

    static String getNewId(){
        return UUID.randomUUID().toString();
    }


}


