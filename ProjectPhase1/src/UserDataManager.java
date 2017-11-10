import java.util.ArrayList;
import java.util.HashMap;

public abstract class UserDataManager {

    private static ArrayList<String> previousPathsVisited = new ArrayList<>();

    private static HashMap<String, ImageFile> nameToImageFileMap = new HashMap<>();

    public static String[] getPreviousPathsVisited() {
        return previousPathsVisited.toArray(new String[previousPathsVisited.size()]);
    }

    public static void addPathToVisitedList(String path){
        if (!previousPathsVisited.contains(path)){
            previousPathsVisited.add(path);
        }
    }

    public static boolean existsInMap(String imageName){
        return getNameToImageFileMap().containsKey(imageName);
    }

    public static ImageFile getImageFileWithName(String imageName){
        return getNameToImageFileMap().get(imageName);
    }

    public static void resetImageFileKey(String oldName, String newName){
        if (getNameToImageFileMap().containsKey(oldName)){
            ImageFile renamedImageFile = getNameToImageFileMap().get(oldName);
            getNameToImageFileMap().remove(oldName);
            addImageFileToMap(renamedImageFile);
        }
    }

    public static void addImageFileToMap(ImageFile imageFile){
        getNameToImageFileMap().put(imageFile.getCurrentName(), imageFile);
    }

    public static HashMap<String, ImageFile> getNameToImageFileMap() {
        return nameToImageFileMap;
    }
}
