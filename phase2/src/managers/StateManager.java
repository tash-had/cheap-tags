package managers;

import exceptions.DirectoryCreationException;
import exceptions.FileNotCreatedException;

import java.io.*;

/**
 * A class to manage states for the program, including session state and user data state.
 */
public class StateManager {
    /**
     * Container for all user data in records
     */
    public static UserDataManager userData;

    /**
     * Container for all session data
     */
    public static SessionDataManager sessionData;

    /**
     * Start a new session
     */
    public static void startSession(){
        reloadState();
        sessionData = new SessionDataManager();
    }

    /**
     * End a session
     */
    static void endSession(){
        userData.allTags = TagManager.getTagList();
        saveState(userData);
    }

    /**
     * Attempt to load a state from a previous session
     */
    private static void reloadState(){
        File dataFile = new File("data/data.ctags");
        if (!dataFile.exists()){
            userData = new UserDataManager();
        }else{
            FileInputStream fileInputStream;
            ObjectInputStream objectInputStream;
            try{
                // Figure out storing multiple objects later
                fileInputStream = new FileInputStream(dataFile);
                objectInputStream = new ObjectInputStream(fileInputStream);
                userData = (UserDataManager) objectInputStream.readObject();
                TagManager.setTagList(userData.allTags);
                objectInputStream.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Attempt to store all new data from this session, including ImageFiles and Tags.
     *
     * @param userDataManager the main data manager for this session.
     */
    private static void saveState(UserDataManager userDataManager){
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;
        File dataFile = new File("data/data.ctags");
        try{
            if (!dataFile.exists()){
                createDataFile(dataFile);
            }
            fileOutputStream = new FileOutputStream(dataFile);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(userDataManager);
            objectOutputStream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void createDataFile(File dataFile) {
        if (!dataFile.exists()) {
            File dir = new File(dataFile.getParentFile().getAbsolutePath());
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    try {
                        throw new DirectoryCreationException("There was an error making a directory!");
                    } catch (DirectoryCreationException e) {
                        e.printStackTrace();
                    }
                }
            }else {
                dir.delete();
                dir.mkdirs();
            }
            try {
                if (!dataFile.createNewFile()) {
                    throw new FileNotCreatedException("There was an error creating a new data file!");
                }
            } catch (IOException | FileNotCreatedException e) {
                e.printStackTrace();
            }
        }
    }
}


