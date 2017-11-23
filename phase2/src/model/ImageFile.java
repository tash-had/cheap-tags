package model;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.nio.file.Path;
import java.io.File;
import java.lang.StringBuilder;


//after the filemanager passes the file to this class, model.ImageFile will construct an imagefile object
//on it. But in this class, I haven't associated rename with tag.java
//Any operations inside this class will not manipulate the actual file, but the data inside the userdata.
public class ImageFile implements Serializable{
    private StringBuilder currentName; //the most current name of this image
    private ArrayList<Tag> tagList; //the list of tag this image has.
    //oldName keeps track of all of the revision histories in the format of arraylist [newname,previous name,timestamp]
    private ArrayList<ArrayList<String>> oldName;
    private String originalName; //the original name of this image without any tag.
    private String underWhichDirectory; //the path for the parent folder of this image.
    private File thisFile;
    private String imageType; //the type of the string(eg. ".jpeg")
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

    //public void addTagOnImage(Tag newTag){
      //  String tempName = currentName.toString();
        //newTag.addImage(this);
      //  String tempTag ="@"+newTag.toString();
       // if(!tagList.contains(tempTag)){
        //    this.tagList.add(tempTag); }
       // Long timeStamp = System.currentTimeMillis();
       // currentName.insert(0,(tempTag+" "));
        //String[] tempLog = {currentName.toString(),tempName,timeStamp.toString()};
        //this.oldName.add(tempLog);
        //tempName = currentName.toString();
        //String targetName = this.underWhichDirectory+tempName+this.imageType;
        //this.thisFile = new File (targetName);

    //}


    //public void removeTagOnImage(Tag oldTag){
      //  String tempName = currentName.toString();
        //oldTag.deleteImage(this);
        //String tempTag = oldTag.toString();
        //if(!tagList.contains(tempTag)){
          //  this.tagList.add(tempTag);
        //}
        //for(int i = 0;i<this.tagList.size();i++){
          //  if(this.tagList.get(i).equals(tempTag)){
            //    this.tagList.remove(i);
            //}
        //}
        //Long timeStamp = System.currentTimeMillis();
        //String[] currentNameSplit = tempName.split("\\b");
        //currentName = new StringBuilder();
        //for(String i:currentNameSplit){
          //  if(i.equals(tempTag)){
            //    continue;
            //}
            //else{
             //   currentName.append(i);
        //    }
       // }
        //String[] tempLog = {currentName.toString(),tempName,timeStamp.toString()};
        //this.oldName.add(tempLog);
        //tempName = currentName.toString();
        //String targetName = this.underWhichDirectory+tempName+this.imageType;
        //this.thisFile = new File (targetName);
   //}

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

    public void updateTagHistory(ArrayList<Tag> newEntry){
        ArrayList<Tag> temp = new ArrayList<>();
        temp.addAll(newEntry);
        this.tagHistory.add(temp);
    }

    public ArrayList<ArrayList<Tag>> getTagHistory(){
        return this.tagHistory;
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


    public void setFile(File newFile){
        this.thisFile = newFile;
    }
    public void setUnderWhichDirectory(String underWhichDirectory) {
        this.underWhichDirectory = underWhichDirectory;
    }


}