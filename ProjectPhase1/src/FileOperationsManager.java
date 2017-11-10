import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.UUID;

/**
 * This class handles all OS-related operations for the ImageFiles and the
 */
public class FileOperationsManager {

    public void renameFile(Path path, String newName) throws IOException {
        Files.move(path, path.resolveSibling(newName));
    }

    /**
     * Change the directory to image
     * @param newDirectory is the new directory where we will move file to)
     */
    public void changeImageDirectory(File fileToMove, Path newDirectory){
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

    public void fetchFromDirectory(){

    }

    // Store data

}


