package managers;

import exceptions.DirectoryCreationException;
import exceptions.FileNotCreatedException;

import java.io.*;

/**
 * Class to set states
 */
public class StateManager {
    public static UserDataManager userData;
    public static SessionDataManager sessionData;

    public static void startSession(){
        reloadState();
        sessionData = new SessionDataManager();
    }

    public static void endSession(){
        userData.allTags = TagManager.getTagList();
        saveState(userData);
    }

    public static void reloadState(){
        File dataFile = new File("data/data.ctags");
        if (!dataFile.exists()){
            userData = new UserDataManager();
        }else{
            FileInputStream fileInputStream = null;
            ObjectInputStream objectInputStream = null;
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

    public static void saveState(UserDataManager userDataManager){
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
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


