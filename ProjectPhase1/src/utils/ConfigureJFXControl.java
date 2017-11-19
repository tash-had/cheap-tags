package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Tag;

import java.util.ArrayList;

/** A series of convenience functions for javafx.scene.control.Control objects */
public abstract class ConfigureJFXControl {
    /**
     * Set the font text font of any item that is an instance of Labeled, using a font file.
     *
     * @param fontPath the path of the font file you wish to use. Eg. resources/fonts/myFont.ttf
     * @param fontSize the size of the text on the Labeled instance.
     * @param labeledItems one or more instances of Labeled on which the font should be applied
     * */
    public static void setFontOfLabeled(String fontPath, double fontSize, Labeled... labeledItems){
        Font font = Font.loadFont(ConfigureJFXControl.class.getResourceAsStream(fontPath), fontSize);
        for (Labeled labeledItem : labeledItems){
            labeledItem.setFont(font);
        }
    }

    /**
     * Set a simple animation to change text color on mouse enter and mouse exit of instances of Labeled
     *
     * @param mouseEnterColor the color to set the text to when the mouse enters/hovers over the instance of Labeled
     * @param mouseExitColor the color to set the text to when the mouse is exits the instance of Labeled
     * @param labeledItems one or more instances of Labeled on which to apply this animation
     */
    public static void toggleHoverTextColorOfLabeled(Color mouseEnterColor, Color mouseExitColor, Labeled ... labeledItems){
        for (Labeled labeledItem: labeledItems){
            labeledItem.setOnMouseEntered(event -> labeledItem.setTextFill(mouseEnterColor));
            labeledItem.setOnMouseExited(event -> labeledItem.setTextFill(mouseExitColor));
        }
    }

    /**
     * Set the listview to display string representation of custom objects.
     * Precondition: The type parameter of the listview should have a toString() method defined.
     *
     * Source:https://stackoverflow.com/questions/36657299/how-can-i-populate-a-listview-in-javafx-using-custom-objects
     *
     * @param listView the listview on which to set the cell factory
     * @param <T> the type parameter of the listview
     */
    public static <T> void setListViewToDisplayCustomObjects(ListView<T> listView){
        listView.setCellFactory(param -> new ListCell<T>(){
            @Override
            protected void updateItem(T obj, boolean empty){
                super.updateItem(obj, empty);

                if (empty || obj == null || obj.toString() == null){
                    setText(null);
                }else {
                    setText(obj.toString());
                }
            }

        });

    }
    /**
     * Populate a given ListView with a given ArrayList of data by adding the data to an ObservableList (which acts
     * as the data source for the ListView). Returns a reference to the ObservableList so the user can modify
     * the ListView's data.
     *
     * @param listView the ListView to populate
     * @param dataArrayList the data with which listView should be initially populated.
     * @param <T> the type of the items that listView will hold, and that dataArrayList already contains.
     * @return a reference to the data source that is set to listView
     */
    public static <T> ObservableList<T> populateListViewWithArrayList(ListView<T> listView, ArrayList<T> dataArrayList){
        ObservableList<T> observableList = listView.getItems();
        if (observableList == null){
            observableList = FXCollections.observableArrayList();
        }
        for (T item : dataArrayList){
            observableList.add(item);
        }
        listView.setItems(observableList);
        return observableList;
    }
}
