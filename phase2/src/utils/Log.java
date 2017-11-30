package utils;

import javafx.beans.property.SimpleStringProperty;

/**
 * The {@code Log} class is used to store the current name, old name and time stamp of an image file
 * used in activities/RevisionLogViewController to generate the table view of revision history
 *
 * @author Caroline Ming
 */
public class Log {
    /**
     * the current name of the image file as a string property
     */
    private SimpleStringProperty currentName;

    /**
     * The old name of the image file as a string property
     */
    private SimpleStringProperty oldName;

    /**
     * The time stamp of the image file as a string property
     */
    private SimpleStringProperty timeStamp;

    /**
     * Construct a new log with three variables
     *
     * @param current current name
     * @param old     old name
     * @param time    time stamp
     */
    public Log(String current, String old, String time) {
        this.currentName = new SimpleStringProperty(current);
        this.oldName = new SimpleStringProperty(old);
        this.timeStamp = new SimpleStringProperty(time);
    }

    /**
     * Get the current name as a string
     *
     * @return String current name
     */
    public String getCurrentName() {
        return currentName.get();
    }

    /**
     * Get the old name as a string
     *
     * @return String current name
     */
    public String getOldName() {
        return oldName.get();
    }

    /**
     * Get the time stamp as a string
     *
     * @return String current name
     */
    public String getTimeStamp() {
        return timeStamp.get();
    }

    /**
     * Set the current name
     */
    public void setCurrentName(String name) {
        currentName.set(name);
    }

    /**
     * Set the old name
     */
    public void setOldName(String name) {
        oldName.set(name);
    }

    /**
     * Set the time stamp
     */
    public void setTimeStamp(String time) {
        timeStamp.set(time);

    }

    @Override
    public String toString() {
        StringBuilder logString =  new StringBuilder();
        logString.append(currentName.get()+", ");
        logString.append(oldName.get() + ", ");
        logString.append(timeStamp.get());

        return logString.toString();

    }

}

