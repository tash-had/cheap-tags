import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;
import java.nio.file.Path;
import java.io.File;
import java.lang.StringBuilder;


//after the filemanager passes the file to this class, ImageFile will construct an imagefile object
//on it. But in this class, I haven't associated rename with tag.java
public class ImageFile{
    private StringBuilder currentName; //the most current name of this image
    private String previousName; //the last version of image name
    private ArrayList<String> tagList; //the list of tag this image has.
    //oldName keeps track of all of the revision histories in the format of array [newname,previous name,timestamp]
    private ArrayList<String[]> oldName;
    private String originalName; //the original name of this image without any tag.
    private String underWhichDirectory; //the path for the parent folder of this image.
    private File thisFile;
    private String imageType; //the type of the string(eg. ".jpeg")


    /**
     * Construct a new ImageFile object.
     * @param oneImageFile is the actual imagefile(eg.image.jpeg)
     */
    public ImageFile(File oneImageFile){
        currentName = new StringBuilder();
        originalName = oneImageFile.getName();
        currentName.append(originalName);
        previousName = oneImageFile.getName();
        oldName = new ArrayList<String[]>();
        underWhichDirectory = oneImageFile.getParent();
        thisFile = oneImageFile;
        String c= oneImageFile.getName();
        String[] split = c.split("\\.");
        imageType = ("."+split[split.length-1]);
    }

    /**
     * Change the name
     * @param newTag is the new tag which will be added to the file name)
     * but this one is just to add tag not deleting tag!
     * Also,the image would also be added to the arraylist in Tag class
     *
     */
    public boolean addTagOnImage(Tag newTag){
        newTag.addImage(this);
        String tempTag ="@"+newTag.toString();
        if(!tagList.contains(tempTag)){
            this.tagList.add(tempTag);
        }
        Long timeStamp = System.currentTimeMillis();
        currentName.insert(0,(tempTag+" "));
        String[] tempLog = {currentName.toString(),previousName,timeStamp.toString()};
        this.oldName.add(tempLog);
        previousName = currentName.toString();
        String targetName = this.underWhichDirectory+previousName+this.imageType;
        File tempFile = new File (targetName);
        this.thisFile = tempFile;
        return this.getThisFile().renameTo(tempFile);
    }


        /**
         * Change the name
         * @param oldTag is the old tag which will be added to the file name)
         * but this one is just to remove tag not deleting tag!!!!!!!
         * Also,the image would also be removed from the arraylist in Tag class
         */
    public boolean removeTagOnImage(Tag oldTag){
        oldTag.deleteImage(this);
        String tempTag = oldTag.toString();
        if(!tagList.contains(tempTag)){
            this.tagList.add(tempTag);
        }
        for(int i = 0;i<this.tagList.size();i++){
            if(this.tagList.get(i)==tempTag){
                this.tagList.remove(i);
            }
        }
        Long timeStamp = System.currentTimeMillis();
        String[] currentNameSplit = previousName.split("\\b");
        currentName = new StringBuilder();
        for(String i:currentNameSplit){
            if(i.equals(tempTag)){
                continue;
            }
            else{
                currentName.append(i);
            }
        }
        String[] tempLog = {currentName.toString(),previousName,timeStamp.toString()};
        this.oldName.add(tempLog);
        previousName = currentName.toString();
        String targetName = this.underWhichDirectory+previousName+this.imageType;
        File tempFile = new File (targetName);
        this.thisFile = tempFile;
        return this.getThisFile().renameTo(tempFile);
    }


    /**
     * Change the directory to image
     * @param newDirectory is the new directory where we will move file to)
     */
    public boolean changeImageDirectory(Path newDirectory){
        StringBuilder targetDirectory = new StringBuilder();
        String tempDirectory = newDirectory.toString();
        targetDirectory.append(tempDirectory.replace('\\','/'));
        tempDirectory = targetDirectory.toString() +"/" + this.previousName;
        File tempFile = new File (tempDirectory);
        this.thisFile = tempFile;
        this.underWhichDirectory = tempFile.getParent();
        return this.getThisFile().renameTo(tempFile);//Check the sequence here.
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
    public ArrayList<String> getTagList(){return this.tagList;}


}