package managers;

import model.ImageFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public abstract class UserDataManager implements Serializable {
    private static ArrayList<String> previousPathsVisited = new ArrayList<>();
    private static HashMap<String, ImageFile> nameToImageFileMap = new HashMap<>(); // contains all known images
    private static HashMap<String, ImageFile> nameToImageFileSessionMap = new HashMap<>(); // contains images from this session only
    private static String currentSessionPath;

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

    public static ImageFile getSessionImageFileWithName(String imageName){
        return getNameToImageFileSessionMap().get(imageName);
    }

    public static void resetImageFileKey(String oldName){
        if (getNameToImageFileMap().containsKey(oldName)){
            ImageFile renamedImageFile = getNameToImageFileMap().get(oldName);
            getNameToImageFileMap().remove(oldName);
            addImageFileToMap(renamedImageFile);
            if (getNameToImageFileSessionMap().containsKey(oldName)){
                getNameToImageFileSessionMap().remove(oldName);
                addImageFileToSessionMap(renamedImageFile);
            }
        }

    }
    public static boolean setNewImageFile(String imageName, ImageFile newImageFile){
        if (getNameToImageFileMap().containsKey(imageName)){
            getNameToImageFileMap().put(imageName, newImageFile);
            return true;
        }return false;
    }

    public static Collection<String> getImageFileNames(){
        return nameToImageFileSessionMap.keySet();
    }

    public static Collection<String> getSessionImageFileNames(){
        return new ArrayList<>(getNameToImageFileSessionMap().keySet());
    }

    public static void addImageFileToMap(ImageFile imageFile){
        getNameToImageFileMap().put(imageFile.getCurrentName(), imageFile);
    }

    public static void addImageFileToSessionMap(ImageFile imageFile){
        getNameToImageFileSessionMap().put(imageFile.getCurrentName(), imageFile);
    }


    public static HashMap<String, ImageFile> getNameToImageFileSessionMap() {
        return nameToImageFileSessionMap;
    }
    private static HashMap<String, ImageFile> getNameToImageFileMap() {
        return nameToImageFileMap;
    }

    //Some setter and getter used only by UserDataSaver and UserDataGetter
    public static HashMap<String, ImageFile> getNameToImageFileMapForDataSaver() {
        return nameToImageFileMap;
    }
    public static void NameToImageFileMapSetterForDataGetter(HashMap<String, ImageFile> newOne){
        nameToImageFileMap = newOne;
    }
    private void setNameToImageFileMap(HashMap<String, ImageFile> map){
        nameToImageFileMap = map;
    }
    public static ArrayList<String> previousPathsVisitedGetterForDataSaver(){
        return previousPathsVisited;
    }
    public static void previousPathsVisitedSetterForDataGetter(ArrayList<String> newList){
        previousPathsVisited = newList;
    }

    public static void setSession(String sessionPath){
        if (currentSessionPath != null){
            getNameToImageFileSessionMap().clear();
            currentSessionPath = sessionPath;
        }else {
            currentSessionPath = sessionPath;
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException{
        out.defaultWriteObject();
        Object[] data = {nameToImageFileMap, previousPathsVisited};
        out.writeObject(data);
     }

    private void readObject(ObjectInputStream in) throws IOException{
        try {
            Object[] data = (Object[]) in.readObject();
            HashMap<String, ImageFile> nameToImfMap;
            nameToImfMap = (HashMap<String, ImageFile>) data[0];
            setNameToImageFileMap(nameToImfMap);
            previousPathsVisitedSetterForDataGetter((ArrayList<String>) data[1]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // prep data
}
