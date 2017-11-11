package managers;

import model.ImageFile;

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

    public boolean renameFile(ImageFile imageFile, String newName) {
        File newFile = new File(imageFile.getTheParentpath() + "/" +
                newName +imageFile.getImageType());
        File currentImageFile = imageFile.getThisFile();
        currentImageFile.renameTo(newFile);
        if (!currentImageFile.renameTo(newFile)){
            try{
                if (!(newFile.exists())){
                    Path currentFilePath = currentImageFile.toPath();
                    Files.move(currentFilePath, currentFilePath.resolveSibling(newName));
                    UserDataManager.resetImageFileKey(currentFilePath.getFileName().toString(), newName);
                    return true;
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * Change the directory to image
     * @param newDirectory is the new directory where we will move file to)
     */
    public boolean changeImageDirectory(ImageFile imageFile, Path newDirectory){
        StringBuilder targetDirectory = new StringBuilder();
        String newDirectoryString = newDirectory.toString();

        targetDirectory.append(newDirectoryString.replace('\\','/'));

        newDirectoryString = targetDirectory.toString() +"/" + imageFile.getCurrentName();
        File newFile = new File (newDirectoryString);

        imageFile.setUnderWhichDirectory(newFile.getPath());

        boolean res = imageFile.getThisFile().renameTo(newFile);
        imageFile.setFile(newFile);
        return res;
    }



    public void fetchFromDirectory(){


    }

    // Store data

}


