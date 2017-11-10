import java.util.ArrayList;

/**
 * Created by tash-had on 2017-11-09.
 */
public abstract class UserDataManager {

    private static ArrayList<String> previousPathsVisited = new ArrayList<>();

    public static String[] getPreviousPathsVisited() {
        return previousPathsVisited.toArray(new String[previousPathsVisited.size()]);
    }
    public static void addPathToVisitedList(String path){
        if (!previousPathsVisited.contains(path)){
            previousPathsVisited.add(path);
        }
    }

}
