import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.ArrayList;

import java.util.Observable;

public class DisplayTagsView {

    public static TextField tagInput;

    public static ArrayList<String> myData;

    public static ListView<String> tagsView;


    public DisplayTagsView(){

    }

    public static Scene getScene(Button back) {

        // Create an arrayList store all user input and visualize it.

        myData = new ArrayList<>();


        myData.add("May");
        myData.add("June");

        tagsView = new ListView<>();


        tagsView.setItems(ConfigureJFXControl.populateListViewWithArrayList(tagsView,myData));

        //Catch User Input
        tagInput = new TextField();
        tagInput.setPromptText("Example: Tag");
        tagInput.setMinWidth(100);


        //AddButton
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addButtonClicked());

        //DeleteButton
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteButtonClicked());

        //Store UserInput in a tagManager
        for (String s: myData){
            Tag myTag = new Tag(s);
            TagManager.addTag(myTag);
        }


        //Set a row for user to do all the operations.

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(360, 10, 10, 10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(tagInput,addButton, deleteButton, back);

        // Set the layout of the scene.
        StackPane layout = new StackPane();
        layout.getChildren().addAll(tagsView, hBox);

        return new Scene(layout, 375, 400);
    }

    public static void addButtonClicked(){
        String userInput = tagInput.getText();
        ArrayList<String> newData = new ArrayList<>();
        myData.add(userInput);
        newData.add(userInput);
        tagsView.setItems(ConfigureJFXControl.populateListViewWithArrayList(tagsView,newData));
        tagInput.clear();
    }

    public static void deleteButtonClicked(){
    }

    }
