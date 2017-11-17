package StoreObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class UserDataGetter {

    /**
     * This Function is used to extract file.
     */
    public static void GetDataAfterOpen() throws IOException{
        Object nameToImageFileMap;
        Object previousPathsVisited;
        Object tagList;
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
    }
}
