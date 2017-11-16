package managers;

import model.Tag;

import java.util.ArrayList;

public class TagManager {

    /**
     * A list of all the tags in the system.
     */
    private static ArrayList<Tag> tagList = new ArrayList<Tag>();

    /**
     * Add a new tag.
     *
     * @param newTag the tag to be added to the list of tags.
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

    public static Tag getTagByString(String stringOfTag){
        for(Tag i: TagManager.tagList){
            if(i.toString().equals(stringOfTag)){
                return i;
            }
        }
        return null;
    }
}
