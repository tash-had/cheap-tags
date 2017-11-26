package model;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.nio.file.Path;
import java.io.File;
import java.lang.StringBuilder;

/**
 * After the filemanager passes the file to this class, model.ImageFile will construct an imagefile object on it.
 * Any operations inside this class will not manipulate the actual file, but the data inside the userdata.
 */
public class ImageFile implements Serializable, Comparable<ImageFile>{
    /**
     * the most current name of this image
     */
    private StringBuilder currentName;

    /**
     * the list of tag this image has.
     */
    private ArrayList<Tag> tagList;

    /**
     * oldName keeps track of all of the revision histories in the format of arraylist [newname,previous name,timestamp]
     */
    private ArrayList<ArrayList<String>> oldName;

    /**
     * the original name of this image without any tag.
     */
    private String originalName;

    /**
     * the path for the parent folder of this image.
     */
    private String underWhichDirectory;

    /**
     * the actual file object
     */
    private File thisFile;

    /**
     * the type of the string(eg. ".jpeg")
     */
    private String imageType;


    private ArrayList<ArrayList<Tag>> tagHistory;

    /**
     * Construct a new model.ImageFile object.
     * @param oneImageFile is the actual imagefile(eg.image.jpeg)
     */
    public ImageFile(File oneImageFile){
        currentName = new StringBuilder();
        originalName = oneImageFile.getName();
        currentName.append(originalName);
        oldName = new ArrayList<ArrayList<String>>();
        underWhichDirectory = oneImageFile.getParent();
        thisFile = oneImageFile;
        String c= oneImageFile.getName();
        String[] split = c.split("\\.");
        imageType = ("."+split[split.length-1]);
        tagList = new ArrayList<>();
        tagHistory = new ArrayList<>();
    }

    /**
     * Change inner information of an imagefile class based on given String
     * @param newName The new name for imagefile
     */
    public void generalReName(String newName, ArrayList<Tag> newTagList){
        String tempName = currentName.toString();
        currentName = new StringBuilder();
        currentName.append(newName);
        currentName.append(this.imageType);
        tagList = newTagList;
        Long timeStamp = System.currentTimeMillis();
        ArrayList<String> temLog = new ArrayList<>(Arrays.asList(currentName.toString(),tempName,timeStamp.toString()));
        this.oldName.add(temLog);
        String targetName = this.underWhichDirectory+tempName+this.imageType;
        this.thisFile = new File(targetName);
    }

    /**
     * override the generalReNameFunction but only take one parameter.
     * Change inner information of an imagefile class based on given String
     * @param newName the newname for the imagefile
     */
    public void generalReName(String newName){
        String tempName = currentName.toString();
        currentName = new StringBuilder();
        currentName.append(newName);
        //currentName.append(this.imageType);
        Long timeStamp = System.currentTimeMillis();
        ArrayList<String> temLog = new ArrayList<>(Arrays.asList(currentName.toString(),tempName,timeStamp.toString()));
        this.oldName.add(temLog); //Add new entry to revision history
        String targetName = this.underWhichDirectory+tempName+this.imageType;
        this.thisFile = new File(targetName);
    }

    /**
     * It updates tag history
     * @param newEntry an arraylist of tag
     */
    public void updateTagHistory(ArrayList<Tag> newEntry){
        ArrayList<Tag> temp = new ArrayList<>();
        temp.addAll(newEntry);
        this.tagHistory.add(temp);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ImageFile)){
            return false;
        }
        else{
            ImageFile temp = (ImageFile) obj;
            if(this.currentName.equals(temp.currentName)){
                return true; }
            return false; }
    }

    //override compareto method, let the image can be ordered in alphabetical order.
    public int compareTo(ImageFile i){
        if (this.originalName.charAt(0)<i.originalName.charAt(0)){
            return -1;
        }
        else if(this.originalName.charAt(0)>i.originalName.charAt(0)){
            return 1;
        }
        else{
            return 0;
        }
    }

    //some getters
    public String getCurrentName(){
        return this.currentName.toString();
    }
    public String getOriginalName(){
        return this.originalName;
    }
    public ArrayList<ArrayList<String>> getOldName(){
        return this.oldName;
    }
    public String getTheParentpath(){
        return this.underWhichDirectory;
    }
    public File getThisFile(){
        return this.thisFile;
    }
    public ArrayList<Tag> getTagList(){return this.tagList;}
    public String getImageType(){ return this.imageType; }
    public ArrayList<ArrayList<Tag>> getTagHistory(){
        return this.tagHistory;
    }


    //some setters
    public void setFile(File newFile){
        this.thisFile = newFile;
    }
    public void setUnderWhichDirectory(String underWhichDirectory) {
        this.underWhichDirectory = underWhichDirectory;
    }


}