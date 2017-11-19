package StoreObject;

import managers.TagManager;
import managers.UserDataManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class UserDataSaver implements java.io.Serializable {

    /**
     * this function stores user data in "AllOfUserData.cheaptag" (first: nameToImageFileMap, second: previousPathsVisited
     * third: tagList)
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void SaveBeforeClose() throws FileNotFoundException,IOException{
        FileOutputStream OutputStream = new FileOutputStream("src/historyData/AllOfUserData.cheaptag");
        ObjectOutputStream tempOut = new ObjectOutputStream(OutputStream);
        //tempOut.writeObject(UserDataManager.getNameToImageFileMapForDataSaver());
        tempOut.writeObject(UserDataManager.previousPathsVisitedGetterForDataSaver());
        //tempOut.writeObject(TagManager.getTagList());
        tempOut.close();
    }

    public static void storeData(){
        try{UserDataSaver.SaveBeforeClose();}
        catch(IOException e){
            e.printStackTrace();
        }    }
}
