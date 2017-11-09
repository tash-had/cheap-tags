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
     * @param one_image_file is the actual imagefile(eg.image.jpeg)
     */
    public ImageFile(File one_image_file){
        currentName = new StringBuilder();
        originalName = one_image_file.getName();
        currentName.append(originalName);
        previousName = one_image_file.getName();
        oldName = new ArrayList<String[]>();
        underWhichDirectory = one_image_file.getParent();
        thisFile = one_image_file;
        reNameSuccess = true;
        String c= one_image_file.getName();
        String[] split = c.split("\\.");
        imageType = ("."+split[split.length-1]);
    }

    /**
     * Change the name
     * @param newTag is the new tag which will be added to the file name)
     * but this one is just to add tag not deleting tag!!!!!!!
     */
    public void add_tag_onimage(Tag newTag){
        String temptag = newTag.toString();
        Long timestamp = System.currentTimeMillis();
        currentName.insert(0,(temptag+" "));
        String[] templog = {currentName.toString(),previousName,timestamp.toString()};
        this.oldName.add(templog);
        previousName = currentName.toString();
        String targetName = this.underWhichDirectory+previousName+this.imageType;
        File tempfile = new File (targetName);
        this.reNameSuccess = this.getthisFile().renameTo(tempfile);
        this.thisFile = tempfile;}


        /**
         * Change the name
         * @param oldTag is the old tag which will be added to the file name)
         * but this one is just to remove tag not deleting tag!!!!!!!
         */
    public void remove_tag_onimage(Tag oldTag){
        String temptag = oldTag.toString();
        Long timestamp = System.currentTimeMillis();
        String[] currentNameSplit = previousName.split("\\b");
        currentName = new StringBuilder();
        for(String i:currentNameSplit){
            if(i.equals(temptag)){
                continue;
            }
            else{
                currentName.append(i);
            }
        }
        String[] templog = {currentName.toString(),previousName,timestamp.toString()};
        this.oldName.add(templog);
        previousName = currentName.toString();
        String targetName = this.underWhichDirectory+previousName+this.imageType;
        File tempfile = new File (targetName);
        this.reNameSuccess = this.getthisFile().renameTo(tempfile);
        this.thisFile = tempfile;
    }


    /**
     * Change the directory to image
     * @param newDirectory is the new directory where we will move file to)
     */
    public void change_image_directory(Path newDirectory){
        StringBuilder targetDirectory = new StringBuilder();
        char tempchar = '\\';
        String tempDirectory = newDirectory.toString();
        for(int i=0;i<tempDirectory.length();i++){
            if(tempDirectory.charAt(i)==tempchar){
                targetDirectory.append("/");
            }
            else {
                targetDirectory.append(tempDirectory.charAt(i));
            }
        }
        tempDirectory = targetDirectory.toString() +"/" + this.previousName;
        File tempfile = new File (tempDirectory);
        this.movingSuccess=this.getthisFile().renameTo(tempfile);
        this.thisFile = tempfile;
        this.underWhichDirectory = tempfile.getParent();

    }


    //some getters
    public String get_currentName(){
        return this.currentName.toString();
    }
    public String get_original_name(){
        return this.originalName;
    }
    public ArrayList<String[]> get_oldName(){
        return this.oldName;
    }
    public String get_the_parentpath(){
        return this.underWhichDirectory;
    }
    public File getthisFile(){
        return this.thisFile;
    }


}