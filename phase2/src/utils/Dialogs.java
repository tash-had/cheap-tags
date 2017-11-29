package utils;

import com.sun.istack.internal.Nullable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.Collection;
import java.util.Optional;

import static managers.PrimaryStageManager.getPrimaryStageManager;


public class Dialogs {
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
     * @param windowTitle the dialog window title
     * @param header the dialog header text
     * @param body the dialog body text
     * @return the user response to the dialog
     */
    public static ButtonType showYesNoAlert(String windowTitle, String header, String body){
        Alert yesNoDialog = new Alert(Alert.AlertType.CONFIRMATION, body, ButtonType.NO, ButtonType.YES);
        yesNoDialog.setTitle(windowTitle);
        yesNoDialog.setHeaderText(header);
        yesNoDialog.showAndWait();
        return yesNoDialog.getResult();
    }

    /**
     * Tell the user that they are trying to move/rename a file to/in a directory where a file with that name exists.
     *
     * @param directory the directory in which there is an existing file
     * @param fileName the name of the file they are trying to place in the directory
     * @param filter which names to avoid when selecting a suffixed name
     * @return the file name with a numerical suffix, or null if they don't want to rename it
     */
    public static String showFileExistsAlert(File directory, String fileName, @Nullable Collection filter){
        // Get new file with a suffix
        String suffixedFileName = FileOperations.getSuffixedFileName(directory, fileName);

        while (filter != null && filter.contains(suffixedFileName)){
            suffixedFileName = FileOperations.getSuffixedFileName(directory, suffixedFileName);
        }
        // Ask user if they would like a suffixed name on the image
        ButtonType renameReqResponse = Dialogs.showYesNoAlert("Could not rename file",
                "Filename Taken",
                fileName +" already exists. Would you like to name it "+suffixedFileName+"?");
        if (renameReqResponse == ButtonType.OK){
            return suffixedFileName;
        }
        return null;
    }

    /**
     * Show an alert telling the user than an error has occured
     *
     * @param windowTitle the title of the dialog window
     * @param header the header text for the dialog
     * @param body the body text
     */
    public static void showErrorAlert(String windowTitle, String header, String body){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR, body, ButtonType.OK);
        errorAlert.setTitle(windowTitle);
        errorAlert.setHeaderText(header);
        errorAlert.showAndWait();
    }

    /**
     * Show an alert telling the user than an error has occured
     *
     * @param windowTitle the title of the dialog window
     * @param header the header text for the dialog
     * @param body the body text
     * @return the string the user entered
     */
    public static String showTextInputDialog(String windowTitle, @Nullable String header, String body){
        TextInputDialog textDialog = new TextInputDialog();
        textDialog.setTitle(windowTitle);
        if (header != null){
            textDialog.setHeaderText(header);
        }
        textDialog.setContentText(body);
        textDialog.showAndWait();
        return textDialog.getResult();
    }

    /**
     * Show a dialog asking the user for login creds
     *
     * @param windowTitle the title of the dialog window
     * @param header the header text for the dialog
     * @param body the body text
     * @return a String array containing the username and password and indexes 0 and 1, respectively
     */
    public static String[] loginDialog(String windowTitle, String header, @Nullable String body){
        /*
         Citation
         *************************************************************************************
         Title: JavaFX Custom Login Dialog
         Author: Marco Jakob
         Date: 2014
         Availability: http://code.makery.ch/blog/javafx-dialogs-official/
         */
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle(windowTitle);
        dialog.setHeaderText(header);
        if (body != null){
            dialog.setContentText(body);
        }

        ButtonType loginBtnType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelBtnType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(loginBtnType, cancelBtnType);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        PasswordField password = new PasswordField();
        username.setPromptText("Username");
        password.setPromptText("Password");
        gridPane.add(username, 0, 0);
        gridPane.add(password, 0, 1);

        dialog.getDialogPane().setContent(gridPane);

        dialog.setResultConverter(dialogBtn -> {
            if (dialogBtn == loginBtnType){
                return new String[]{username.getText(), password.getText()};
            }
            return null;
        });
        String[] loginCreds = new String[2];

        Optional<String[]> dialogReturn = dialog.showAndWait();
        dialogReturn.ifPresent(dialogResult -> {
            loginCreds[0] = dialogResult[0];
            loginCreds[1] = dialogResult[1];
        });
        return loginCreds;
    }

    /**
     * Prompt the user to select a directory using a DirectoryChooser Dialog, and return the selected directory.
     *
     * @return the directory chosen by the user. returns null if no file selected.
     */
    public static File getDirectoryWithChooser(){
        DirectoryChooser directoryChooser=  new DirectoryChooser();
        directoryChooser.setTitle("Select a directory");
        return directoryChooser.showDialog(getPrimaryStageManager().getStage());
    }

    /**
     * Tell the user that a tag already exists with the same name.
     */
    public static void showTagExistsAlert(){
        Alert tagExistsDialog = new Alert(Alert.AlertType.NONE, "A tag with this name already exists. " +
                "Please select a different name.", ButtonType.CLOSE);
        tagExistsDialog.showAndWait();
    }

    /**
     * Tell the user that actions cannot be performed on a file because they haven't chosen one yet.
     */
    public static void chooseFileAlert(){
        Alert needToChooseFile = new Alert(Alert.AlertType.NONE, "No image file has been selected yet.\n" +
                "Please select a image file first.", ButtonType.CLOSE );
        needToChooseFile.showAndWait();
    }

    /**
     * Tell the user that the selected file already has that tag.
     */
    public static void fileContainsTagAlert(){
        Alert fileContainsTag = new Alert(Alert.AlertType.NONE, "The selected file already contains this tag." +
                "", ButtonType.CLOSE);
        fileContainsTag.showAndWait();
    }

}
