package utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;


public class Log{
    private SimpleStringProperty currentName;
    private SimpleStringProperty oldName;
    private SimpleStringProperty timeStamp;

    public Log(String current, String old, String time){
        this.currentName = new SimpleStringProperty(current);
        this.oldName = new SimpleStringProperty(old);
        this.timeStamp = new SimpleStringProperty(time);

    }

    public String getCurrentName(){
        return currentName.get();
    }

    public String getOldName(){
        return oldName.get();
    }

    public String getTimeStamp(){
        return timeStamp.get();
    }

    public void setCurrentName(String name){
        currentName.set(name);
    }

    public void setOldName(String name){
        oldName.set(name);
    }

    public void setTimeStamp(String time){
        timeStamp.set(time);

    }

}

