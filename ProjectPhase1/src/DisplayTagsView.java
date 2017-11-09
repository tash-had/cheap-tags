import javafx.application.Application;
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

    public DisplayTagsView(){

    }

    public static Scene getScene(Button back) {


        TableView<String> table;
        TextField tagInput;

        TableColumn<String, String> nameColumn = new TableColumn<>("Tag");
        nameColumn.setMinWidth(370);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("tag"));


        table = new TableView<>();
        table.setItems(tagslist());
        table.getColumns().add(nameColumn);

        //Tag Input

        tagInput = new TextField();
        tagInput.setPromptText("Example: @May");
        tagInput.setMinWidth(100);

        //Botton
        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");


        HBox hBox = new HBox();
        hBox.setPadding(new Insets(360, 10, 10, 10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(tagInput, addButton, deleteButton, back);


        StackPane layout = new StackPane();
        layout.getChildren().addAll(table, hBox);

        Scene s = new Scene(layout, 375, 400);

        return s;
    }




    public static ObservableList<String> tagslist() {
        ObservableList<String> tags = FXCollections.observableArrayList();
        tags.add("May");
        return tags;
    }




}
