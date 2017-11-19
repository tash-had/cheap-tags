package managers;

import com.sun.istack.internal.Nullable;
import com.sun.javaws.exceptions.InvalidArgumentException;
import javafx.scene.control.ButtonType;
import model.ImageFile;
import model.Tag;
import utils.Alerts;
import utils.FileOperations;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

import static utils.FileOperations.FileOperationsResponse.*;
import static utils.FileOperations.*;

/**
 * This class manages how file operations for ImageFiles are handled
 */
public class ImageFileOperationsManager {

    public static String[] ACCEPTED_EXTENSIONS = {".jpg", ".jpeg", ".png", ".bmp", ".tif"};

    /**
     * Rename a given ImageFile but only take two parameters.
     *
     * @param imageFile the image to rename
     * @param newName the new name
     * @return a ImageFile object with the new name if successfully renamed, the old File object otherwise
     */
    public static ImageFile renameImageFile(ImageFile imageFile, String newName){
        File currentImageFile = imageFile.getThisFile();
        Path imageFilePath = Paths.get(currentImageFile.getParentFile().getAbsolutePath());
        FileOperationsResponse response =  renameFile(currentImageFile, newName);
        if (response == SUCCESS){
            imageFile.generalReName(newName);
            UserDataManager.resetImageFileKey(currentImageFile.getName(), newName);
            imageFilePath = Paths.get(imageFilePath.toAbsolutePath().toString(), newName);
        }else if (response == FILENAME_TAKEN){
            String suffixedFileName = Alerts.showFileExistsAlert(currentImageFile.getParentFile(), newName,
                    UserDataManager.getImageFileNames());
            // User accepted to a suffixed filename
            if (suffixedFileName != null){
                renameImageFile(imageFile, suffixedFileName);
            }
        }else {
            // Show error alert dialog
            Alerts.showErrorAlert("Renaming Error", "Error", "There was an error renaming your file");
        }
        imageFile.setFile(imageFilePath.toFile());
        return imageFile;
    }

    /**
     * Move an image to another directory
     *
     * @param imageFile the imageFile representing the image to move
     * @return File file in its new directory
     */
    public static File moveImageFile(ImageFile imageFile) {
        File oldFile = imageFile.getThisFile();
        File newDirectory = PrimaryStageManager.getDirectoryWithChooser();
        // A file object of the imagefile in the new directory
        File newFile = new File(newDirectory, oldFile.getName());
        FileOperationsResponse response = moveFile(oldFile, newDirectory.toPath());

        if (response == SUCCESS){
            return newFile;
        }else if(response == FILENAME_TAKEN){
            String suffixedFileName = Alerts.showFileExistsAlert(newDirectory, newFile.getName(), null);
            if (suffixedFileName != null){
                imageFile.generalReName(suffixedFileName);
                moveFile(imageFile.getThisFile(), newDirectory.toPath());
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
     *
     * TODO:
     * 1 - See if imageFile exists in database
     * 2 - If it does, get our saved ImageFile and add it to the sessionMap
     * 3 - If it does not, create a new ImageFile, store it in our database and our sessionMap
     */
    public static Collection<ImageFile> fetchImageFiles(File directory){
        Collection<ImageFile> sessionMap = UserDataManager.getNameToImageFileSessionMap().values();
        if (sessionMap.size() > 0){
            return sessionMap;
        }
        ArrayDeque<String> acceptedExtensions = new ArrayDeque<>();
        ArrayDeque<ImageFile> filesToLoad = new ArrayDeque<>();
        acceptedExtensions.addAll(Arrays.asList(ACCEPTED_EXTENSIONS));
        try {
            ArrayDeque<File> filesFromDir = fetchFromDirectory(directory, acceptedExtensions);
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
                        UserDataManager.addImageFileToSessionMap(imageFile);
                    }
                }else {
                    ImageFile imageFile = new ImageFile(file);
                    filesToLoad.add(imageFile);
                    UserDataManager.addImageFileToMap(imageFile);
                    UserDataManager.addImageFileToSessionMap(imageFile);
                }
            }
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
        return filesToLoad;
    }


    // Store data

}


