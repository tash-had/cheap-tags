package managers;

import com.sun.istack.internal.Nullable;
import com.sun.javaws.exceptions.InvalidArgumentException;
import javafx.scene.control.ButtonType;
import model.ImageFile;
import utils.Alerts;
import utils.FileOperations;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import model.Tag;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * This class manages how file operations for ImageFiles are handled
 */
public class ImageFileOperationsManager {

    /**
     * Rename a given ImageFile
     *
     * @param imageFile the image to rename
     * @param newName the new name
     * @return a File object with the new name if successfully renamed, the old File object otherwise
     */
    public static File renameImageFile(ImageFile imageFile, String newName){
        File currentImageFile = imageFile.getThisFile();
        String imageFilePath = currentImageFile.getParentFile().getAbsolutePath() +"/";
        imageFile.generalReName(newName);//everytime user rename the image, the information inside imagefile will also change.
        int renameStatus = FileOperations.renameFile(currentImageFile, newName);
        if (renameStatus == 1){
            UserDataManager.resetImageFileKey(currentImageFile.getName(), newName);
            imageFilePath += newName;
        }else if (renameStatus == -1){
            //where should we put the suffix inside the filename?
            String suffixedFileName = Alerts.showFileExistsAlert(currentImageFile.getParentFile(),
                    new File(imageFilePath+newName),
                    UserDataManager.getImageFileNames());
            if (suffixedFileName != null){
                /*
                  TODO:
                  Do steps required for a rename in imageFile
                 */
                //check the imageFile.generalReName(newName) above
                renameImageFile(imageFile, suffixedFileName);
            }else {
                /*
                  TODO:
                  dont rename.
                 */
                return currentImageFile;
            }
        }else {
            // Show error alert dialog
            return currentImageFile;
        }
        return new File(imageFilePath);
    }

    /**
     * Move an image to another directory
     *
     * @param imageFile the imageFile representing the image to move
     * @return the
     */
    public static File moveImageFile(ImageFile imageFile) {
        File oldFile = imageFile.getThisFile();
        File newDirectory = PrimaryStageManager.getDirectoryWithChooser();
        // A file object of the imagefile in the new directory
        File newFile = new File(newDirectory.getAbsolutePath()+"/"+oldFile.getName());
        int moveStatus = FileOperations.moveFile(oldFile, newDirectory.toPath());//when we use file.Move, shouldn't the target directory include the image name?
        if (moveStatus == 1){
            return newFile;
        }else if(moveStatus == -1){
            String suffixedFileName = Alerts.showFileExistsAlert(newDirectory, newFile, null);
            if (suffixedFileName != null){
                //where should we put the suffix inside the filename?
                //
                /*
                TODO:
                Take steps to rename this imageFile to suffixedFile.getName()
                (ie. setUnderWhichDirectory, imageFile.setFile, ...
                Take steps to move this iamgeFile to suffixedFile.getParent()
                 */
//                newFile = new File(newDirectory.getAbsolutePath()+"/"+suffixedFileName);
                // Make sure imageFile is renamed beforehand and its file attribute is reset with the new name before
                // this line is run!
                FileOperations.moveFile(imageFile.getThisFile(), newDirectory.toPath());
            }else{
                // Dont move
                newFile = null;
            }
        }else {
            // error alert
        }
        return newFile;
    }

    /**
     * Fetch images in a given directory
     *
     * @param directory the directory to fetch from
     * @return a collection of ImageFile's representing each image fetched
     */
    public ArrayDeque<ImageFile> fetchImageFiles(File directory){
        ArrayDeque<String> acceptedExtensions = new ArrayDeque<>();
        ArrayDeque<ImageFile> filesToLoad = new ArrayDeque<>();
        acceptedExtensions.addAll(Arrays.asList(".jpg", ".jpeg", ".png", ".bmp", ".tif"));
        try {
            ArrayDeque<File> filesFromDir = FileOperations.fetchFromDirectory(directory, acceptedExtensions);
            for (File file : filesFromDir){
                String fileName = file.getName();
                if (UserDataManager.existsInMap(fileName)){
                    ImageFile imageFile = UserDataManager.getImageFileWithName(fileName);
                    if (!imageFile.getThisFile().getParentFile().getAbsolutePath().equals(directory.getAbsolutePath())){
                        // Image does not match image that exists in directory!
                        /*
                        TODO:
                        Figure out how to deal with this.. show text input dialog askk for new name
                         */
                    }else {
                        filesToLoad.add(imageFile);
                    }
                }else {
                    ImageFile imageFile = new ImageFile(file);
                    UserDataManager.addImageFileToMap(imageFile);
                }
            }
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
        return filesToLoad;
    }


    // Store data

}


