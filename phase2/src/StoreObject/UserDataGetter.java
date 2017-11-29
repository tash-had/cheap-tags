package StoreObject;

import managers.StateManager;
import managers.TagManager;
import managers.UserDataManager;
import model.ImageFile;
import model.Tag;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class used to extract data from file
 */
public class UserDataGetter implements java.io.Serializable {

    /**
     * This Function is used to extract file.
     * when there is no target directory or file, it will create a new empty directory or file
     */
    private static void GetDataAfterOpen() throws IOException {
        if (!new File("src/data").exists()) {
            File checkFile = new File("src/data");
            System.out.println("Make directory successfully: " + checkFile.mkdir());
            File newFile = new File("src/data/AllOfData.cheaptag");
            System.out.println("Make new file successfully: " +newFile.createNewFile());

        }
        else if(!new File("src/data/AllOfData.cheaptag").exists()){
            File newFile = new File("src/data/AllOfData.cheaptag");
            System.out.println("Make new file successfully: " + newFile.createNewFile());
        }

        else if(new File("src/data/AllOfData.cheaptag").length()!=0){
            //Object nameToImageFileMap = null;
            Object previousPathsVisited = null;
            //Object tagList = null;
            FileInputStream InputStream = new FileInputStream(new File("src/data/AllOfData.cheaptag"));
            ObjectInputStream tempIn = new ObjectInputStream(InputStream);

            try {
                //nameToImageFileMap = tempIn.readObject();
                previousPathsVisited = tempIn.readObject();
                //tagList = tempIn.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            tempIn.close();
            if (
                //(nameToImageFileMap instanceof HashMap) &&
                    (previousPathsVisited instanceof ArrayList)
                //&& (tagList instanceof ArrayList)
                    ) {
                //HashMap<String,ImageFile> tempNameToImage = (HashMap<String, ImageFile>) nameToImageFileMap;
                ArrayList<String> tempPreviousPath = (ArrayList<String>) previousPathsVisited;
                //ArrayList<Tag> tempTagList = (ArrayList<Tag>) tagList;
                //UserDataManager.NameToImageFileMapSetterForDataGetter(tempNameToImage);
                //StateManager.userData.previousPathsVisitedSetterForDataGetter(tempPreviousPath);
                //TagManager.tagListSetterForDataGetter(tempTagList);
            }

        }
    }


    /**
     * it is a helper function to load all of the data into current data container
     */
    public static void loadDATA(){
        try{UserDataGetter.GetDataAfterOpen();}
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
