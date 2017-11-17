package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;
import java.nio.file.Path;
import java.io.File;
import java.lang.StringBuilder;


//after the filemanager passes the file to this class, model.ImageFile will construct an imagefile object
//on it. But in this class, I haven't associated rename with tag.java
//Any operations inside this class will not manipulate the actual file, but the data inside the userdata.
public class ImageFile{
    private StringBuilder currentName; //the most current name of this image
    private ArrayList<Tag> tagList; //the list of tag this image has.
    //oldName keeps track of all of the revision histories in the format of array [newname,previous name,timestamp]
    private ArrayList<String[]> oldName;
    private String originalName; //the original name of this image without any tag.
    private String underWhichDirectory; //the path for the parent folder of this image.
    private File thisFile;
    private String imageType; //the type of the string(eg. ".jpeg")
    private String suffixForDuplicate; //


    /**
     * Construct a new model.ImageFile object.
     * @param oneImageFile is the actual imagefile(eg.image.jpeg)
     */
    public ImageFile(File oneImageFile){
        currentName = new StringBuilder();
        originalName = oneImageFile.getName();
        currentName.append(originalName);
        oldName = new ArrayList<String[]>();
        underWhichDirectory = oneImageFile.getParent();
        thisFile = oneImageFile;
        String c= oneImageFile.getName();
        String[] split = c.split("\\.");
        imageType = ("."+split[split.length-1]);
        suffixForDuplicate = "";
    }

    /**
     * Change the name
     * @param newTag is the new tag which will be added to the file name)
     * but this one is just to add tag not deleting tag!
     * Also,the image would also be added to the arraylist in model.Tag class
     *
     *
     */
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


        /**
         * Change the name
         * @param oldTag is the old tag which will be added to the file name)
         * but this one is just to remove tag not deleting tag!!!!!!!
         * Also,the image would also be removed from the arraylist in model.Tag class
         */
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
     * @param newName
     */
    public void generalReName(String newName, ArrayList<Tag> newTagList){
        String tempName = currentName.toString();
        currentName = new StringBuilder();
        currentName.append(newName);
        currentName.append(this.imageType);
        tagList = newTagList;
        Long timeStamp = System.currentTimeMillis();
        String[] tempLog = {currentName.toString(),tempName,timeStamp.toString()};
        this.oldName.add(tempLog);
        String targetName = this.underWhichDirectory+tempName+this.imageType;
        this.thisFile = new File(targetName);
    }


    //some getters
    public String getCurrentName(){
        return this.currentName.toString();
    }
    public String getOriginalName(){
        return this.originalName;
    }
    public ArrayList<String[]> getOldName(){
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