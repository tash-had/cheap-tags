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
    //oldName keeps track of all of the revision histories in the format of array [newname,previous name,timestamp]
    private ArrayList<String[]> oldName;
    private String originalName; //the original name of this image without any tag.
    private String underWhichDirectory; //the path for the parent folder of this image.
    private File thisFile;
    private boolean reNameSuccess; //a boolean indicator which shows whether the renaming is successful
    private boolean movingSuccess; //a boolean indicator which shows whether the moving is successful
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
        reNameSuccess = true;
        String c= oneImageFile.getName();
        String[] split = c.split("\\.");
        imageType = ("."+split[split.length-1]);
    }

    /**
     * Change the name
     * @param newTag is the new tag which will be added to the file name)
     * but this one is just to add tag not deleting tag!
     * Also,the image would also be added to the arraylist in Tag class
     */
    public void addTagOnImage(Tag newTag){
        newTag.addImage(this);
        String tempTag = newTag.toString();
        Long timeStamp = System.currentTimeMillis();
        currentName.insert(0,(tempTag+" "));
        String[] tempLog = {currentName.toString(),previousName,timeStamp.toString()};
        this.oldName.add(tempLog);
        previousName = currentName.toString();
        String targetName = this.underWhichDirectory+previousName+this.imageType;
        File tempFile = new File (targetName);
        this.reNameSuccess = this.getThisFile().renameTo(tempFile);
        this.thisFile = tempFile;}


        /**
         * Change the name
         * @param oldTag is the old tag which will be added to the file name)
         * but this one is just to remove tag not deleting tag!!!!!!!
         * Also,the image would also be removed from the arraylist in Tag class
         */
    public void removeTagOnImage(Tag oldTag){
        oldTag.deleteImage(this);
        String tempTag = oldTag.toString();
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
        this.reNameSuccess = this.getThisFile().renameTo(tempFile);
        this.thisFile = tempFile;
    }


    /**
     * Change the directory to image
     * @param newDirectory is the new directory where we will move file to)
     */
    public void changeImageDirectory(Path newDirectory){
        StringBuilder targetDirectory = new StringBuilder();
        char tempChar = '\\';
        String tempDirectory = newDirectory.toString();
        for(int i=0;i<tempDirectory.length();i++){
            if(tempDirectory.charAt(i)==tempChar){
                targetDirectory.append("/");
            }
            else {
                targetDirectory.append(tempDirectory.charAt(i));
            }
        }
        tempDirectory = targetDirectory.toString() +"/" + this.previousName;
        File tempFile = new File (tempDirectory);
        this.movingSuccess=this.getThisFile().renameTo(tempFile);
        this.thisFile = tempFile;
        this.underWhichDirectory = tempFile.getParent();

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


}