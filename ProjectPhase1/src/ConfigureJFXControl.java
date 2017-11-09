import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

/** A series of convenience functions for javafx.scene.control.Control objects */
public abstract class ConfigureJFXControl {
    public static void setFontOfLabeled(String fontPath, double fontSize, Labeled... labeledItems){
        Font font = Font.loadFont(ConfigureJFXControl.class.getResourceAsStream(fontPath), fontSize);
        for (Labeled labeledItem : labeledItems){
            labeledItem.setFont(font);
        }
    }

    public static void toggleHoverTextColorOfLabeled(Color mouseEnterColor, Color mouseExitColor, Labeled ... labeledItems){
        for (Labeled labeledItem: labeledItems){
            labeledItem.setOnMouseEntered(event -> {
                labeledItem.setTextFill(mouseEnterColor);
            });
            labeledItem.setOnMouseExited(event -> {
                labeledItem.setTextFill(mouseExitColor);
            });
        }
    }

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
