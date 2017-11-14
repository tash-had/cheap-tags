package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * This class contains exclusively of static methods that operate on files or attributes of files.
 */
public class FileOperations {

    /**
     * Rename a given file.
     *
     * @param file the file to rename
     * @param newName the new name for the given file, including extension
     * @return 0 on failure, 1 on success and -1 on failure due to existing file
     */
    public static int renameFile(File file, String newName) {
        File newFile = new File(file.getParentFile().getAbsolutePath() + "/" + newName);
        int status = 0;
        try {
            if (!(newFile.exists())) {
                Path currentFilePath = file.toPath();
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
     * Get a new file with a suffix at the end of its name based on how many other files in the parent directory,
     * share the same name
     *
     * @param existingFileName the name to suffix
     * @param parentDirectory the parent directory
     * @return a File object of the
     */
    public static String getSuffixedFileName(String existingFileName, File parentDirectory){
        String fileExtension = getFileExtension(existingFileName);
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
     * @param fileName the name of the file
     * @return the extension (includes the dot)
     */
    public static String getFileExtension(String fileName){
        String fileExtension = null;
        if (fileName.contains(".")){
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
    public static int moveFile(File file, Path destinationDirectory){
        File newFile = destinationDirectory.toFile();
        int status = 0;

        if (!newFile.exists()){
            Path sourcePath = file.toPath();
            try {
                Files.move(sourcePath, destinationDirectory.resolve(sourcePath.getFileName()), REPLACE_EXISTING);
                status = 1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            status = -1;
        }
        return status;
    }
}
