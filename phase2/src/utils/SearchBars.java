package utils;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import managers.TagManager;
import model.Tag;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SearchBars {

    public static void TagSearchByText(ListView listView, ObservableList data, String input ){
        ArrayList searchResult = new ArrayList<>();

        Pattern tagSearchPattern = Pattern.compile(input);
        Matcher tagSearchMatcher;

        data.clear();
        if (input.isEmpty()) {
            searchResult.clear();
            data = ConfigureJFXControl.populateListViewWithArrayList(listView, TagManager.getTagList());
        } else {
            for (Tag tag : TagManager.getTagList()) {
                tagSearchMatcher = tagSearchPattern.matcher(tag.toString().toLowerCase());
                if (tagSearchMatcher.find()) {
                    searchResult.add(tag);
                }
            }
            data.addAll(searchResult);

        }
    }


}
