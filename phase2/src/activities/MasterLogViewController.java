package activities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import managers.StateManager;
import utils.Log;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class MasterLogViewController implements Initializable {

    /**
     * Shows a table for all revision history
     */
    @FXML
    TableView<Log> masterLog;

    /**
     * the column for current name in the table view
     */
    @FXML
    TableColumn<Log, String> currentName;

    /**
     * the column for old name in the table view
     */
    @FXML
    TableColumn<Log, String> oldName;

    /**
     * the column for timeStamp in the table view
     */
    @FXML
    TableColumn<Log, String> timeStamp;


    /**
     * Stores all revision history in an observable list
     */
    ObservableList<Log> allRevisionHistory = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (String name : StateManager.userData.nameToImageFileMap.keySet()) {
            for (ArrayList al : StateManager.userData.nameToImageFileMap.get(name).getOldName()) {
                Log log = new Log((String) al.get(0), (String) al.get(1), (String) al.get(2));
                allRevisionHistory.add(log);
            }
        }
        currentName.setCellValueFactory(new PropertyValueFactory<>("currentName"));
        oldName.setCellValueFactory(new PropertyValueFactory<>("oldName"));
        timeStamp.setCellValueFactory(new PropertyValueFactory<>("timeStamp"));

        masterLog.setItems(allRevisionHistory);

    }
}
