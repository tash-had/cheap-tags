package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * This class stores all ImageFile data from every session.
 */
public class UserImageFileData implements Serializable {

    /**
     * An ArrayList of all the previous directories the user has visited.
     */
    private ArrayList<String> previousPathsVisited = new ArrayList<>();

    /**
     * An ArrayList of all the tags
     */
    public ArrayList<Tag> allTags;

    /**
     * The name to image file map
     */
    public HashMap<String, ImageFile> nameToImageFileMap = new HashMap<>();

    /**
     * Get the ImageFile associated with the given name
     *
     * @param imageName the image name
     * @return the imagefile with the imageName
     */
    public ImageFile getImageFileWithName(String imageName) {
        return getNameToImageFileMap().get(imageName);
    }

    /**
     * Add an ImageFile to the main map containing all ImageFiles on record
     *
     * @param imageFile the ImageFile to add
     */
    public void addImageFileToMap(ImageFile imageFile) {
        getNameToImageFileMap().put(imageFile.getCurrentName(), imageFile);
    }

    /**
     * Reset the key of the ImageFile in the main HashMap of all ImageFiles
     *
     * @param oldName the old name of the image
     */
    public void resetImageFileKey(String oldName) {
        if (getNameToImageFileMap().containsKey(oldName)) {
            ImageFile renamedImageFile = getNameToImageFileMap().get(oldName);
            getNameToImageFileMap().remove(oldName);
            addImageFileToMap(renamedImageFile);
        }
    }

    /**
     * Get a collection of names of all ImageFiles on record.
     *
     * @return a collection of all names.
     */
    public Collection<String> getImageFileNames() {
        return new ArrayList<>(nameToImageFileMap.keySet());
    }

    /**
     * Get a reference to the main HashMap containing all ImageFiles on record
     *
     * @return a HashMap of image names to their corresponding ImageFile
     */
    public HashMap<String, ImageFile> getNameToImageFileMap() {
        return nameToImageFileMap;
    }

    /**
     * Check if an image with the given name exists in the main HashMap of ImageFiles
     *
     * @param imageName the image name to look for
     * @return if it exists in the map
     */
    public boolean existsInMap(String imageName) {
        return getNameToImageFileMap().containsKey(imageName);
    }

    /**
     * Add a path to the visited paths list
     *
     * @param path the path to add
     */
    public void addPathToVisitedList(String path) {
        if (previousPathsVisited.contains(path)) {
            previousPathsVisited.remove(path);
        }
        previousPathsVisited.add(path);
    }

    /**
     * Get an array of the previous paths visited
     *
     * @return an array of previous paths
     */
    public String[] getPreviousPathsVisited() {
        return previousPathsVisited.toArray(new String[previousPathsVisited.size()]);
    }
}