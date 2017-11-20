package model;

import model.ImageFile;

import java.util.ArrayList;

public class Tag implements java.io.Serializable{

    /**
     * the name of the tag
     */
    public String name;

    /**
     * the arraylist stores all images that have this tag
     */
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

    public boolean equals(Object o){
        if (o == null){
            return false;
        }
        if (o.getClass() != this.getClass()){
            return false;
        }
        else {
            Tag newo = (Tag) o;
            return newo.name.equals(this.name);
        }
    }
}
