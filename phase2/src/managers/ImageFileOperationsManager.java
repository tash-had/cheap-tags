package managers;

import com.sun.istack.internal.Nullable;
import javafx.scene.control.ButtonType;
import model.ImageFile;

import model.Tag;
import model.UserTagData;
import utils.Dialogs;
import utils.FileOperations;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Arrays;

import static utils.FileOperations.*;
import static utils.FileOperations.FileOperationsResponse.FAILURE;
import static utils.FileOperations.FileOperationsResponse.FILENAME_TAKEN;
import static utils.FileOperations.FileOperationsResponse.SUCCESS;

/**
 * This class handles how file operations are <b>handled</b> within the program.
 * <p>
 * Note: This class differs from utils/FileOperations in that the latter deals with the OS-related operations,
 * while this class handles the consequences of those operations (ie.
 */
public class ImageFileOperationsManager {

    public static String[] ACCEPTED_EXTENSIONS = {".jpg", ".jpeg", ".png", ".bmp", ".tif"};

    /**
     * Rename a given ImageFile but only take two parameters.
     *
     * @param imageFile the image to rename
     * @param newName   the new name
     * @return a ImageFile object with the new name if successfully renamed, the old File object otherwise
     */
    public static ImageFile renameImageFile(ImageFile imageFile, String newName) {
        File currentImageFile = imageFile.getThisFile();
        Path imageFilePath = Paths.get(currentImageFile.getParentFile().getAbsolutePath());

//        if (StateManager.userData.existsInMap(newName)) {
//            return handleNewNameExists(imageFile, newName);
//        }

        FileOperationsResponse response = renameFile(currentImageFile, newName);
        if (response == SUCCESS) {
            String oldName = imageFile.getCurrentName();
            imageFile.generalReName(newName);
            StateManager.userData.resetImageFileKey(oldName);
            StateManager.sessionData.resetImageFileKey(oldName);
            imageFilePath = Paths.get(imageFilePath.toAbsolutePath().toString(), newName);
        } else if (response == FILENAME_TAKEN) {
            String suffixedFileName = Dialogs.showFileExistsAlert(currentImageFile.getParentFile(), newName,
                    StateManager.userData.getImageFileNames());
            // User accepted to a suffixed filename
            if (suffixedFileName != null) {
                return renameImageFile(imageFile, suffixedFileName);
            }
        } else {
            // Show error alert dialog
            Dialogs.showErrorAlert("Renaming Error", "Error",
                    "There was an error renaming your file");
        }
        imageFile.setFile(imageFilePath.toFile());
        return imageFile;
    }

//    /**
//     * Helper function to handle the case where the new name for an ImageFile already exists in the database
//     *
//     * @param imageFile the ImageFile being renamed
//     * @param newName   the new name the user is attempting to give the ImageFile
//     * @return the ImageFile with a newer name of the users choice if they accept a rename. The old ImageFile otherwise.
//     */
//    private static ImageFile handleNewNameExists(ImageFile imageFile, String newName) {
//        String chosenName = handleFilenameTakenByDatabase(new File(newName),
//                "It looks like the new name you chose exists in our database from " +
//                        StateManager.userData.getImageFileWithName(newName).getThisFile().getAbsolutePath() +
//                        ". Would you like to choose a new name? (If you select no, your file will not be renamed).");
//        if (chosenName == null) {
//            // User denied to a rename.
//            return imageFile;
//        } else {
//            return renameImageFile(imageFile, chosenName);
//        }
//    }

    /**
     * Move an image to another directory
     *
     * @param imageFile the imageFile representing the image to move
     * @return File file in its new directory or null if file not moved
     */
    public static File moveImageFile(ImageFile imageFile, File newDirectory) {
        String oldName = imageFile.getCurrentName();

        File oldFile = imageFile.getThisFile();

        // If user clicks cancel on directory dialog, end function.
        if (newDirectory == null) {
            return null;
        }
        // A file object of the imageFile in the new directory
        File newFile = new File(newDirectory, oldFile.getName());
        FileOperationsResponse response = moveFile(oldFile, newDirectory.toPath());

        if (response == FILENAME_TAKEN) {
            String suffixedFileName = Dialogs.showFileExistsAlert(newDirectory, newFile.getName(), null);

            if (suffixedFileName != null) {
                imageFile.generalReName(suffixedFileName);
                moveFile(imageFile.getThisFile(), newDirectory.toPath());
            } else {
                // Don't move
                newFile = null;
            }
        } else if(response == FAILURE) {
            Dialogs.showErrorAlert("Move Error", "Error", "There was an error moving your file");
        }
        imageFile.setFile(newFile);
        StateManager.sessionData.resetImageFileKey(oldName);
        StateManager.userData.resetImageFileKey(oldName);
        return newFile;
    }

    /**
     * Fetch images in a given directory and handle various possible events.
     *
     * @param directory the directory to fetch from
     */
    public static void fetchImageFiles(File directory) {
        ArrayDeque<String> acceptedExtensions = new ArrayDeque<>();
        acceptedExtensions.addAll(Arrays.asList(ACCEPTED_EXTENSIONS));
        try {
            // Get a list of files from the directory that have an accepted extension
            ArrayDeque<File> filesFromDir = fetchFromDirectory(directory, acceptedExtensions);
            for (File file : filesFromDir) {
                if (StateManager.userData.existsInMap(file)) {
                    // The file name already exists in our records
                    ImageFile imageFile = StateManager.userData.getImageFileWithFile(file);
                    processFetchedImageFile(null, imageFile);

//                    String existingImageFilePath = imageFile.getThisFile().getParentFile().getAbsolutePath();
//
//                    if (!existingImageFilePath.equals(file.getParentFile().getAbsolutePath())) {
//                        processFetchedImageFile(file, null);
////                        handleImageExistsWithDifferentPath(directory, file, imageFile);
//                    } else {
//                        // Image is exactly the same as one in our records. Process the existing ImageFile
//                        processFetchedImageFile(null, imageFile);
//                    }
                } else {
                    // Image is new. Process it as new
                    processFetchedImageFile(file, null);
                }
            }
        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
            Dialogs.showErrorAlert("Error", "Fetch Error",
                    "There was an error fetching your files. You sure that folder exists?");

        }
    }

//    /**
//     * A helper function to handle the case where an image being fetched has the same name but different location as
//     * an image that already exists in the database.
//     *
//     * @param directory      the firectory being queried
//     * @param duplicateImage the duplicate named image
//     * @param exisitngImage  the existing ImageFile
//     */
//    private static void handleImageExistsWithDifferentPath(File directory, File duplicateImage,
//                                                           ImageFile exisitngImage) {
//        // The ImageFile that exists in our records has a different path from the one being imported!
//        // Ask the user if this file is the same!
//        ButtonType imageIsExistingImage = Dialogs.showYesNoAlert("Filename Exists in Database",
//                "Filename Taken", "It looks like " + duplicateImage.getName() + " exists in our records " +
//                        "from another directory you imported. Is this the same image from "
//                        + exisitngImage.getThisFile().getPath() + "?");
//        if (imageIsExistingImage == ButtonType.NO) {
//            // Image is not the image that exists in directory - ask user if they want to rename the image
//            String alertBody = "Would you like to rename this file? (If you chose no, the file will " +
//                    "not be imported)";
//            String chosenName = handleFilenameTakenByDatabase(duplicateImage, alertBody);
//            // If chosenName is null, function will continue to end of this if statement and terminate
//
//            // Rename the image with the given name + remember to make sure the given name doesn't exist
//            // in the directory being browsed either
//            FileOperationsResponse renameResponse = FileOperations.renameFile(duplicateImage, chosenName);
//            if (renameResponse == FILENAME_TAKEN) {
//                // Don't need to provide database as filter because we already ensured that the new name
//                // doesn't exist in database with handleFilenameTakenByDatabase
//                renameResponse = FileOperations.renameFile(duplicateImage, Dialogs.showFileExistsAlert(directory,
//                        chosenName, null));
//            }
//            if (chosenName != null && renameResponse == SUCCESS) {
//                // Rename is a success. Process the renamed file as new
//                File fileWithChosenName = new File(duplicateImage.getParentFile().getAbsolutePath(), chosenName);
//                processFetchedImageFile(fileWithChosenName, null);
//            }
//        } else {
//            // User says image is same as existing image one in records.
//            // Update location of existing ImageFile and process it
//            exisitngImage.setFile(duplicateImage);
//            processFetchedImageFile(null, exisitngImage);
//        }
//    }

//    /**
//     * Prompt the user to rename the file being imported since it has the same name as something the database.
//     *
//     * @param file the file they are trying to import
//     * @return the new name they chose for the file, null if they said no to a new name.
//     */
//    private static String handleFilenameTakenByDatabase(File file, String alertBody) {
//        String fileName = file.getName();
//        ButtonType renameImage = Dialogs.showYesNoAlert("Filename Exists in Database",
//                "Filename Taken", alertBody);
//
//        if (renameImage == ButtonType.YES) {
//            String result = Dialogs.showTextInputDialog("Enter a new name", null,
//                    "Enter a new name for " + fileName);
//            if (result == null || result.equals("")) {
//                return null;
//            } else {
//                result = result + FileOperations.getFileExtension(file, false);
//            }
//            // Ensure that the new name they gave us doesn't also exist in our database
//            if (StateManager.userData.existsInMap(result)) {
//                return handleFilenameTakenByDatabase(new File(result), alertBody);
//            } else {
//                return result;
//            }
//        } else {
//            return null;
//        }
//    }

    /**
     * Process a fetched image file by adding it to the session and load list.
     * Precondition: one of file or existingImageFile must be null. Pass in file if the file being imported
     * is not in our records. Pass in existingImageFile (from records) if the file being imported exists and we have
     * an ImageFile for the file.
     *
     * @param file              the file being imported (if this files ImageFile isn't stored already), null otherwise
     * @param existingImageFIle the ImageFile for the file being imported (iff it exists in records), null otherwise
     */
    private static void processFetchedImageFile(@Nullable File file,
                                                @Nullable ImageFile existingImageFIle) {
        ImageFile fileToProcess;
        if (file == null) {
            fileToProcess = existingImageFIle;
        } else {
            fileToProcess = new ImageFile(file);

            // Check to see if there are tags in the new file, that don't already exist in our database
            String[] beginningName = fileToProcess.getCurrentName().split("\\s");
            for (String i : beginningName) {
                if (i.startsWith("@")) {
                    String withoutSymbol = i.substring(1, i.length());
                    if (UserTagData.getTagByString(withoutSymbol) == null) {
                        Tag tempTag = new Tag(withoutSymbol);
                        fileToProcess.getTagList().add(tempTag);
                        tempTag.images.add(fileToProcess);
                        UserTagData.addTag(tempTag);
                    } else {
                        Tag tempTag = new Tag(withoutSymbol);
                        fileToProcess.getTagList().add(tempTag);
                        tempTag.images.add(fileToProcess);
                    }

                }
            }
            StateManager.userData.addImageFileToMap(fileToProcess);
        }
        StateManager.sessionData.addImageFileToMap(fileToProcess);
    }
}


