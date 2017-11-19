package managers;

import com.sun.istack.internal.Nullable;
import com.sun.javaws.exceptions.InvalidArgumentException;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import model.ImageFile;
import utils.Alerts;
import utils.FileOperations;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;

import static utils.FileOperations.*;
import static utils.FileOperations.FileOperationsResponse.FILENAME_TAKEN;
import static utils.FileOperations.FileOperationsResponse.SUCCESS;

/**
 * This class manages how file operations for ImageFiles are handled
 */
public class ImageFileOperationsManager implements java.io.Serializable {

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
            UserDataManager.resetImageFileKey(currentImageFile.getName());
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
            Alerts.showErrorAlert("Move Error", "Error", "There was an error moving your file");
        }
        return newFile;
    }

    /**
     * Fetch images in a given directory and handle various possible events.
     *
     * @param directory the directory to fetch from
     * @return a collection of ImageFile's representing each image fetched
     */
    public static Collection<ImageFile> fetchImageFiles(File directory) {
        Collection<ImageFile> sessionMap = UserDataManager.getNameToImageFileSessionMap().values();
        if (sessionMap.size() > 0) {
            return sessionMap;
        }
        ArrayDeque<String> acceptedExtensions = new ArrayDeque<>();
        ArrayDeque<ImageFile> filesToLoad = new ArrayDeque<>();
        acceptedExtensions.addAll(Arrays.asList(ACCEPTED_EXTENSIONS));
        try {
            // Get a list of files from the directory that have an accepted extension
            ArrayDeque<File> filesFromDir = fetchFromDirectory(directory, acceptedExtensions);
            for (File file : filesFromDir) {
                String fileName = file.getName();
                if (UserDataManager.existsInMap(fileName)) {
                    // The file name already exists in our records
                    ImageFile imageFile = UserDataManager.getImageFileWithName(fileName);
                    String existingImageFilePath = imageFile.getThisFile().getParentFile().getAbsolutePath();
                    if (!existingImageFilePath.equals(directory.getAbsolutePath())) {
                        // The ImageFile that exists in our records has a different path from the one being imported!
                        // Ask the user if this file is the same!
                        ButtonType imageIsExistingImage = Alerts.showYesNoAlert("Filename Exists in Database",
                                "Filename Taken", "It looks like " + fileName + " exists in our records " +
                                        "from another directory you imported. Is this the same image from "
                                        + existingImageFilePath + "?");
                        if (imageIsExistingImage == ButtonType.NO) {
                            // Image is not the image that exists in directory! Ask user if they want to rename the image
                            // and make sure the entered name doesn't also exist in our database.
                            String chosenName = handleFilenameTakenByDatabase(file);
                            while (UserDataManager.existsInMap(chosenName)) {
                                chosenName = handleFilenameTakenByDatabase(file);
                            }
                            // Rename the image with the given name + make sure the given name doesn't exist
                            // in the directory either
                            FileOperationsResponse renameResponse = FileOperations.renameFile(file, chosenName);
                            if (renameResponse == FILENAME_TAKEN) {
                                renameResponse = FileOperations.renameFile(file, Alerts.showFileExistsAlert(directory,
                                        chosenName, null));}
                            if (chosenName != null && renameResponse == SUCCESS) {
                                // Rename is a success. Process the renamed file as new
                                File fileWithChosenName = new File(file.getParentFile().getAbsolutePath(), chosenName);
                                processFetchedImageFile(filesToLoad, fileWithChosenName, null);}
                        } else {
                            // Image is same as existing image one in records. Update + process existing ImageFile
                            imageFile.setFile(file);
                            processFetchedImageFile(filesToLoad, null, imageFile);}
                    } else {
                        // Image is exactly the same as one in our records. Process the existing ImageFile
                        processFetchedImageFile(filesToLoad, null, imageFile);
                    }
                } else {
                    // Image is new. Process it as new
                    processFetchedImageFile(filesToLoad, file, null);
                }
            }
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
            Alerts.showErrorAlert("Error", "Fetch Error",
                    "There was an error fetching your files.");
        }
        return filesToLoad;
    }

    /**
     * Prompt the user to rename the file being imported since it has the same name as somethining the database.
     *
     * @param file the file they are trying to import
     * @return the new name they chose for the file, null if they said no to a new name.
     */
    private static String handleFilenameTakenByDatabase(File file) {
        String fileName = file.getName();
        ButtonType renameImage = Alerts.showYesNoAlert("Filename Exists in Database",
                "Filename Taken", "Would you like to rename this file? (If you chose no, the file will " +
                        "not be imported)");
        if (renameImage == ButtonType.YES) {
            TextInputDialog textDialog = new TextInputDialog();
            textDialog.setTitle("Choose a new name");
            textDialog.setContentText("Enter a new name for " + fileName);
            textDialog.showAndWait();
            String result = textDialog.getResult();
            if (result == null || result.equals("")) {
                return null;
            }
            return result + FileOperations.getFileExtension(file);
        } else {
            return null;
        }
    }

    /**
     * Process a fetched image file by adding it to the session and load list.
     * Precondition: one of file or existingImageFile must be null. Pass in file if the file being imported
     * is not in our records. Pass in existingImageFile (from records) if the file being imported exists and we have
     * an ImageFile for the file.
     *
     * @param list the list of files to load
     * @param file the file being imported (if this files ImageFile isn't stored already), null otherwise
     * @param existingImageFIle the ImageFile for the file being imported (iff it exists in records), null otherwise
     */
    private static void processFetchedImageFile(ArrayDeque<ImageFile> list, @Nullable  File file,
                                                   @Nullable  ImageFile existingImageFIle){
        ImageFile fileToProcess;
        if (file == null){
            fileToProcess = existingImageFIle;
            UserDataManager.addImageFileToMap(fileToProcess);
        }else {
            fileToProcess = new ImageFile(file);
        }
        list.add(fileToProcess);
        UserDataManager.addImageFileToSessionMap(fileToProcess);
    }

}


