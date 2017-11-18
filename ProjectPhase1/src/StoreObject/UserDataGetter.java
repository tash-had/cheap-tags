package StoreObject;

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

public class UserDataGetter {

    /**
     * This Function is used to extract file.
     */
    public static void GetDataAfterOpen() throws IOException{
        Object nameToImageFileMap = null;
        Object previousPathsVisited = null;
        Object tagList = null;
        FileInputStream InputStream = new FileInputStream(new File("src/historydata/data.txt"));
        ObjectInputStream tempIn = new ObjectInputStream(InputStream);

        try {
            nameToImageFileMap = tempIn.readObject();
            previousPathsVisited = tempIn.readObject();
            tagList = tempIn.readObject();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tempIn.close();
        if((nameToImageFileMap instanceof HashMap) && (previousPathsVisited instanceof ArrayList) && (tagList instanceof ArrayList)) {
            HashMap<String,ImageFile> tempNameToImage = (HashMap<String, ImageFile>) nameToImageFileMap;
            ArrayList<String> tempPreviousPath = (ArrayList<String>) previousPathsVisited;
            ArrayList<Tag> tempTagList = (ArrayList<Tag>) tagList;
            UserDataManager.NameToImageFileMapSetterForDataGetter(tempNameToImage);
            UserDataManager.previousPathsVisitedSetterForDataGetter(tempPreviousPath);
            TagManager.tagListSetterForDataGetter(tempTagList);
        }

    }
}
