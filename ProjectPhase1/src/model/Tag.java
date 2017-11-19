package model;

import model.ImageFile;

import java.util.ArrayList;
public class Tag implements java.io.Serializable{
    public String name;
    public ArrayList<ImageFile> images = new ArrayList<>();

    public Tag(String name) {
        this.name = name;
    }

    /**
     * Add the image with this tag
     * @param image the image to be added to the arraylist
     */
    public void addImage(ImageFile image){
        this.images.add(image);

    }

    /**
     * Delete the image if the user delete the tag under the image
     * @param image the image to be deleted in the arraylist
     */
    public void deleteImage(ImageFile image){
        this.images.remove(image);
    }


    public String toString() {
        return name;
    }
}