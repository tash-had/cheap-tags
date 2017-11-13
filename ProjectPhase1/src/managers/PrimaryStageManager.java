package managers;

import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * This class consists exclusively of static methods, and delegates all communication with the
 * primary stage of the application.
 */
public abstract class PrimaryStageManager {
    private static Stage primaryStage;

    private static double defaultStageWidth;
    private static double defaultStageHeight;

    /**
     * Set the stage to manage. The primary stage can only be set once and should be set in the start() method of the
     * Main-Class.
     *
     * @param stage the primary stage of this application
     */
    public static void setPrimaryStage(Stage stage){
        if (primaryStage == null){
            primaryStage = stage;
        }
    }

    /**
     * Get the primary stage.
     *
     * @return the primary stage being managed
     */
    private static Stage getPrimaryStage(){
            return primaryStage;
    }

    /**
     * Show the primary stage window
     *
     */
    public static void showPrimaryStage(){
        getPrimaryStage().show();
    }

    /**
     * Set the scene for the entire primary stage using a given Scene object.
     *
     * @param windowTitle the title of the new window
     * @param scene the scene to set
     */
    public static void setScreen(String windowTitle, Scene scene){
        getPrimaryStage().setTitle(windowTitle);
        getPrimaryStage().setScene(scene);
    }

    /**
     * Set the scene for the entire primary stage using a given FXML file.
     *
     * @param windowTitle the title of the new window
     * @param fxmlPath the path of this FXML file relative to this class.
     */
    public static void setScreen(String windowTitle, String fxmlPath){
        getPrimaryStage().setTitle(windowTitle);
        getPrimaryStage().setScene(new Scene(getParentWithFXMLPath(fxmlPath),
                getDefaultStageWidth(), getDefaultStageHeight()));
    }

    /**
     * Set the scene for the entire primary stage using a given FXML file, a width, and a height
     *
     * @param windowTitle the title of the new window
     * @param fxmlPath the path of the FXML file relative to this class.
     * @param width the width the main scene in the stage
     * @param height the height of the main scene in the stage
     */
    public static void setScreen(String windowTitle, String fxmlPath, double width, double height){
        getPrimaryStage().setTitle(windowTitle);
        getPrimaryStage().setScene(new Scene(getParentWithFXMLPath(fxmlPath), width, height));
    }

    /**
     * Return a Parent object loaded with an object hierarchy from an FXMLfile.
     *
     * @param fxmlPath the path of the FXML file to load the Parent with. (relative to this class)
     * @return a Parent instance, loaded with the given FXML file
     */
    public static Parent getParentWithFXMLPath(String fxmlPath){
        try {
            return FXMLLoader.load(PrimaryStageManager.class.getResource(fxmlPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Pane();
    }

    /**
     * Get the default width of the primary stage.
     *
     * @return the default width of this primary stage
     */
    public static double getDefaultStageWidth() {
        return defaultStageWidth;
    }

    /**
     * Set the default width of the primary stage. This will be used when a main scene is set to stage with unspecified
     * width.
     *
     * @param width the width to set as default.
     * */
    public static void setDefaultStageWidth(double width) {
        defaultStageWidth = width;
    }

    /**
     * Get the default height of the primary stage.
     *
     * @return the default height of this primary stage
     */
    public static double getDefaultStageHeight() {
        return defaultStageHeight;
    }

    /**
     * Set the default height of the primary stage. This will be used when a main scene is set to stage with unspecified
     * height.
     *
     * @param height the height to set as default
     */
    public static void setDefaultStageHeight(double height) {
        defaultStageHeight = height;
    }

    /**
     * Prompt the user to select a directory using a DirectoryChooser Dialog, and return the selected directory.
     *
     * @return the directory chosen by the user.
     */
    public static File getDirectoryWithChooser(){
        DirectoryChooser directoryChooser=  new DirectoryChooser();
        directoryChooser.setTitle("Select a directory");
        return directoryChooser.showDialog(PrimaryStageManager.getPrimaryStage());
    }
}