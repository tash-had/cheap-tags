package managers;

import java.io.Serializable;

public class SessionDataManager extends UserDataManager implements Serializable{
    private String currentSessionPath;

    /**
     * Set this session using the given path as the identifier for the session.
     *
     * @param sessionPath the path of the directory being browsed in this session.
     */
    public void setSession(String sessionPath){
        if (this.currentSessionPath != null){
            this.nameToImageFileMap.clear();
            this.currentSessionPath = sessionPath;
        }else {
            this.currentSessionPath = sessionPath;
        }
    }



}
