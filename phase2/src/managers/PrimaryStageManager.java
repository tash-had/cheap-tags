package managers;


import javafx.stage.Stage;


/**
 * This class consists exclusively of static methods, and delegates all communication with the
 * primary stage of the application.
 *
 * This class is final as it does not make sense to extend the Primary stage manager.
 */
@SuppressWarnings({"unused"})
public final class PrimaryStageManager extends StageManager{

    private static PrimaryStageManager thisPrimaryStageManager;

    /**
     * Private constructor because there is only one PrimaryStage, so there cannot be multiple instances
     * of a PrimaryStage.
     *
     * @param stage the primary stage
     */
    private PrimaryStageManager(Stage stage) {
        super(stage);
    }

    /**
     * Get the one and only instance of PrimaryStageManager
     *
     * @return the PrimaryStageManager for this progream
     */
    public static PrimaryStageManager getPrimaryStageManager(){
        return thisPrimaryStageManager;
    }

    /**
     * Set the primaryStage this PrimaryStageManager will manage. A stage can only be
     * set once.
     *
     * @param stage the stage to manage.
     */
    public static void setPrimaryStage(Stage stage){
        if (getPrimaryStageManager() == null){
            thisPrimaryStageManager = new PrimaryStageManager(stage);
            getPrimaryStageManager().getStage().setOnCloseRequest(event -> StateManager.endSession());
        }
    }

    @Override
    public void closeStage(){
        for (StageManager stageManager : StageManager.getStageManagers()){
            stageManager.closeStage();
        }
        this.getStage().close();
    }
}