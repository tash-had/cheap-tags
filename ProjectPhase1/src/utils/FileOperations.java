package utils;

import com.sun.istack.internal.Nullable;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Collection;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static utils.FileOperations.FileOperationsResponse.*;

/**
 * This class contains exclusively of static methods that operate on files or attributes of files.
 */
public class FileOperations {

    /**
     * Enum class to hold response types
     */
    public enum  FileOperationsResponse{
        SUCCESS, FAILURE, FILENAME_TAKEN
    }

    private FileOperationsResponse response;
    /**
     * Rename a given file.
     *
     * @param file the file to rename
     * @param newName the new name for the given file, including extension
     * @return 0 on failure, 1 on success and -1 on failure due to existing file
     */
    public static FileOperationsResponse renameFile(File file, String newName) {
        File newFile = new File(file.getParentFile().getAbsolutePath(), newName);
        try {
            if (!(newFile.exists())) {
                Path currentFilePath = file.toPath();
                Files.move(currentFilePath, currentFilePath.resolveSibling(newName));
                return SUCCESS;
            }else {
                return FILENAME_TAKEN;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FAILURE;
    }

    /**
     * Get a new file with a suffix at the end of its name based on how many other files in the parent directory,
     * share the same name
     *
     * @param existingFileName the name to suffix
     * @param parentDirectory the parent directory
     * @return a File object of the
     */
    public static String getSuffixedFileName(String existingFileName, File parentDirectory){
        String fileExtension = getFileExtension(new File(parentDirectory, existingFileName));
        String nameWithoutExt = existingFileName.substring(0, existingFileName.length() - fileExtension.length());

        int suffix = 1;
        String baseFilePath = parentDirectory.getAbsolutePath() +"/"+nameWithoutExt;
        String newName = nameWithoutExt + "("+Integer.toString(suffix)+")"+fileExtension;
        File newFile = new File(baseFilePath + newName);

        while(newFile.exists()){
            newName = nameWithoutExt +"("+Integer.toString(suffix)+")"+fileExtension;
            newFile = new File(baseFilePath + newName);
            suffix ++;
        }
        return newName;

    }

    /**
     * Get the extension of a normal file.
     *
     * @param file the file to check
     * @return the extension (includes the dot)
     */
    public static String getFileExtension(File file){
        String fileExtension = null;
        String fileName = file.getName();
        if (!file.isDirectory() && !file.isHidden() && fileName.contains(".")){
            fileExtension = fileName.substring(fileName.lastIndexOf("."));
        }
        return fileExtension;
    }

    /**
     * Move a file to a new location
     *
     * @param file the file to move
     * @param destinationDirectory where to move the file
     * @return 0 on failure, 1 on success and -1 on failure due to existing file
     */
    public static FileOperationsResponse moveFile(File file, Path destinationDirectory){
        File newFile = new File(destinationDirectory.toFile(), file.getName());

        if (!newFile.exists()){
            Path sourcePath = file.toPath();
            try {
                Files.move(sourcePath, destinationDirectory.resolve(sourcePath.getFileName()), REPLACE_EXISTING);
                return SUCCESS;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            return FILENAME_TAKEN;
        }
        return FAILURE;
    }

    /**
     * Fetch files from a given directory
     *
     * @param directory the directory to fetch from
     * @param acceptedExtensions a collection of extensions to filter the files with
     * @return an arraylist of the fetched files (as File objects)
     */
    public static ArrayDeque<File> fetchFromDirectory(File directory, @Nullable Collection acceptedExtensions)
            throws InvalidArgumentException {
        if (!directory.isDirectory()){
            throw new InvalidArgumentException(new String[]{"File passed in is not a directory."});
        }
        File[] filesInDirectory = directory.listFiles();
        ArrayDeque<File> validFiles = new ArrayDeque<>();

        if (filesInDirectory != null){
            for (File file : filesInDirectory){
                String fileExtension = FileOperations.getFileExtension(file);
                if ((fileExtension != null) &&
                        (acceptedExtensions == null || acceptedExtensions.contains(fileExtension))){
                    validFiles.add(file);
                }
            }
        }
        return validFiles;
    }
}
