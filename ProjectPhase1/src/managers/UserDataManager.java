package managers;

import model.ImageFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

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
    public static boolean setNewImageFile(String imageName, ImageFile newImageFile){
        if (getNameToImageFileMap().containsKey(imageName)){
            getNameToImageFileMap().put(imageName, newImageFile);
            return true;
        }return false;
    }

    public static Collection getImageFileNames(){
        return getNameToImageFileMap().keySet();
    }


    public static void addImageFileToMap(ImageFile imageFile){
        getNameToImageFileMap().put(imageFile.getCurrentName(), imageFile);
    }

    private static HashMap<String, ImageFile> getNameToImageFileMap() {
        return nameToImageFileMap;
    }

    // prep data
}
