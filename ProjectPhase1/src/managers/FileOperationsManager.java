package managers;

import javafx.scene.control.ButtonType;
import model.ImageFile;
import utils.Alerts;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class handles all OS-related operations for the ImageFiles and the
 */
public class FileOperationsManager {


    public static File renameImageFile(ImageFile imageFile, String newName){
        File currentImageFile = imageFile.getThisFile();
        String imageFilePath = imageFile.getThisFile().getAbsolutePath();
        int renameStatus = renameFile(currentImageFile, newName);
        if (renameStatus == 1){
            UserDataManager.resetImageFileKey(currentImageFile.getName(), newName);
            imageFilePath = currentImageFile.getParentFile().getAbsolutePath() +"/"+newName;
        }else if (renameStatus == -1){
            int suffix = 1;
            String modifiedName = newName;
            while (UserDataManager.existsInMap(modifiedName)){
                modifiedName = modifiedName +" ("+Integer.toString(suffix)+")";
            }
            ButtonType renameReqResponse = Alerts.showYesNoAlert("Could not rename file",
                    "Name Taken",
                    newName +" already exists. Would you like to name it "+modifiedName+"?");
            if (renameReqResponse == ButtonType.OK){
                System.out.println("Implement this after ImageFile implements a renameFunction");
            }
        }else {
            // Show error alert dialog
        }
        return new File(imageFilePath);
    }

    /**
     * Rename a given file.
     *
     * @param oldFile the file to rename
     * @param newName the new name for the given file, including extension
     * @return 0 on failure, 1 on success and -1 on failure due to existing file
     */
    public static int renameFile(File oldFile, String newName) {
        File newFile = new File(oldFile.getParentFile().getAbsolutePath() + "/" + newName);
        int status = 0;
        try {
            if (!(newFile.exists())) {
                Path currentFilePath = oldFile.toPath();
                Files.move(currentFilePath, currentFilePath.resolveSibling(newName));
                status = 1;
            }else {
                status = -1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * Change the directory to image
     *
     * @param newDirectory is the new directory where we will move file to)
     */
    public boolean changeImageDirectory(ImageFile imageFile, Path newDirectory) {
        StringBuilder targetDirectory = new StringBuilder();
        String newDirectoryString = newDirectory.toString();

        targetDirectory.append(newDirectoryString.replace('\\', '/'));

        newDirectoryString = targetDirectory.toString() + "/" + imageFile.getCurrentName();
        File newFile = new File(newDirectoryString);

        imageFile.setUnderWhichDirectory(newFile.getPath());

        boolean res = imageFile.getThisFile().renameTo(newFile);
        imageFile.setFile(newFile);
        return res;
    }


    public void fetchFromDirectory() {

    }

    // Store data

}


