package model;

import java.io.Serializable;
import java.util.TreeSet;

/**
 * the {@code Tag} class is used to take users input in the program
 * and convert the input to a Tag object for further use
 *
 * @author Caroline Ming
 */
public class Tag implements Serializable{

    /**
     * the name of the tag
     */
    public String name;

    /**
     * the arraylist stores all images that have this tag
     */
    public TreeSet<ImageFile> images = new TreeSet<>();

    /**
     * Construct a new model.Tag object
     * @param name
     */
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


    /**
     * Convert tag object to string
     * @return String
     */
    public String toString() {
        return name;
    }

    /**
     * Compare the object 0 with this
     * @param o
     * @return boolean
     */
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
