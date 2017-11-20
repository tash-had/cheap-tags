package managers;

import java.io.Serializable;

public class SessionDataManager extends UserDataManager implements Serializable{
    private String currentSessionPath;

    public void setSession(String sessionPath){
        if (this.currentSessionPath != null){
            this.nameToImageFileMap.clear();
            this.currentSessionPath = sessionPath;
        }else {
            this.currentSessionPath = sessionPath;
        }
    }



}
