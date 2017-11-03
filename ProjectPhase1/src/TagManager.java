import java.util.ArrayList;

public class TagManager {

    /**
     * A list of all the tags in the system.
     */
    private static ArrayList<Tag> tagList = new ArrayList<Tag>();

    /**
     * Add a new tag.
     *
     * @param Tag newTag
     */
    public static void addTag(Tag newTag){
        tagList.add(newTag);
    }

    /**
     * Removes and returns the most recent tag.
     */
    public static void removeTag(){
        tagList.remove(tagList.size()-1);
    }

    /**
     * Removes tag at index i.
     */
    public static void removeTag(int i){
        tagList.remove(i);
    }

    /**
     * Returns the list of tags in the system.
     */
    public static ArrayList<Tag> getTagList(){
        return tagList;
    }
}
