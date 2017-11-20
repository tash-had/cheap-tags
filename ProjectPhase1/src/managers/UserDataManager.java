package managers;

import model.ImageFile;
import model.Tag;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class UserDataManager implements Serializable {
    private ArrayList<String> previousPathsVisited = new ArrayList<>();
    public ArrayList<Tag> allTags;
    HashMap<String, ImageFile> nameToImageFileMap = new HashMap<>();

    public ImageFile getImageFileWithName(String imageName){
        return getNameToImageFileMap().get(imageName);
    }

    public void addImageFileToMap(ImageFile imageFile){
        getNameToImageFileMap().put(imageFile.getCurrentName(), imageFile);
    }

    public void resetImageFileKey(String oldName){
        if (getNameToImageFileMap().containsKey(oldName)){
            ImageFile renamedImageFile = getNameToImageFileMap().get(oldName);
            getNameToImageFileMap().remove(oldName);
            addImageFileToMap(renamedImageFile);
        }
    }

    public Collection<String> getImageFileNames(){
        return new ArrayList<>(nameToImageFileMap.keySet());
    }

    public HashMap<String, ImageFile> getNameToImageFileMap() {
        return nameToImageFileMap;
    }

    public boolean existsInMap(String imageName){
        return getNameToImageFileMap().containsKey(imageName);
    }

    public void addPathToVisitedList(String path){
        if (!previousPathsVisited.contains(path)){
            previousPathsVisited.add(path);
        }
    }

    public String[] getPreviousPathsVisited() {
        return previousPathsVisited.toArray(new String[previousPathsVisited.size()]);
    }

    //
    public ArrayList<String> previousPathsVisitedGetterForDataSaver(){
        return previousPathsVisited;
    }

    public void previousPathsVisitedSetterForDataGetter(ArrayList<String> newList){
        previousPathsVisited = newList;
    }
}
