package StoreObject;

import managers.StateManager;
import managers.TagManager;
import managers.UserDataManager;
import model.ImageFile;

import java.io.*;
import java.util.HashMap;

public class UserDataSaver implements Serializable{

    /**
     * this function stores user data in "AllOfUserData.cheaptag" (first: nameToImageFileMap, second: previousPathsVisited
     * third: tagList)
     * @throws FileNotFoundException filenotfoundexception
     * @throws IOException IOException
     */
    private static void SaveBeforeClose() throws FileNotFoundException,IOException{
        FileOutputStream OutputStream = new FileOutputStream("src/data/AllOfData.cheaptag");
        ObjectOutputStream tempOut = new ObjectOutputStream(OutputStream);
        //tempOut.writeObject((HashMap<String, ImageFile>)UserDataManager.getNameToImageFileMapForDataSaver());
//        tempOut.writeObject(StateManager.userData.previousPathsVisitedGetterForDataSaver());
//        tempOut.writeObject(StateManager.userData.getNameToImageFileMap());

        //tempOut.writeObject(TagManager.getTagList());
        tempOut.close();
    }

    public static void storeData(){
        try{UserDataSaver.SaveBeforeClose();}
        catch(IOException e){
            e.printStackTrace();
        }    }
}
