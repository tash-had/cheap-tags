package utils;

import com.sun.istack.internal.Nullable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.Collection;


public class Alerts {
    //citing this from JavaFx Tut5th.
    public static void displayWarning(String message){
        Stage window = new Stage();

        //this one should be taken care of first
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("WARNING!!!");
        window.setMinWidth(300);

        Label text = new Label();
        text.setText(message);
        Button button = new Button("I Know, Close This Window");
        button.setOnAction(event -> window.close());

        VBox layout = new VBox(15);
        layout.getChildren().add(text);
        layout.getChildren().add(button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();//display this window that needed to be
        //close to further process other things.
    }

    /**
     * Show an alert dialog with yes/no options
     *
     * @param title the dialog window title
     * @param header the dialog header text
     * @param body the dialog body text
     * @return the user response to the dialog
     */
    public static ButtonType showYesNoAlert(String title, String header, String body){
        Alert nameExistsDialog = new Alert(Alert.AlertType.CONFIRMATION, body, ButtonType.NO, ButtonType.YES);
        nameExistsDialog.setTitle(title);
        nameExistsDialog.setHeaderText(header);
        nameExistsDialog.showAndWait();
        return nameExistsDialog.getResult();
    }

    /**
     * Tell the that they are trying to move/rename a file to/in a directory where a file with that name exists.
     *
     * @param directory the directory in which there is an existing file
     * @param file the file they are trying to place in the directory
     * @param filter which names to avoid when selecting a suffixed name
     * @return the file name with a numerical suffix
     */
    public static String showFileExistsAlert(File directory, File file, @Nullable Collection filter){
        // Get new file with a suffix
        String directoryPath = directory.getAbsolutePath()+"/";

        String suffixedFileName = FileOperations.getSuffixedFileName(file.getName(), directory);

        while (filter != null && filter.contains(suffixedFileName)){
            suffixedFileName = FileOperations.getSuffixedFileName(suffixedFileName, directory);
        }
        // Ask user if they would like a suffixed name on the image
        ButtonType renameReqResponse = Alerts.showYesNoAlert("Could not rename file",
                "You Stupid",
                file.getName() +" already exists. Would you like to name it "+suffixedFileName+"?");
        if (renameReqResponse == ButtonType.OK){
            System.out.println("Implement code IN THE CALLING METHOD, make this method return the modified file " +
                    "after ImageFile implements a renameFunction");
            return suffixedFileName;
        }
        return null;
    }
}
