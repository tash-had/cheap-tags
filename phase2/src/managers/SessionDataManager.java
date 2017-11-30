package managers;

import model.ImageFile;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

public class SessionDataManager extends UserDataManager implements Serializable {
    private String currentSessionPath;

    /**
     * Set this session using the given path as the identifier for the session.
     *
     * @param directory the directory this session will browse.
     */
    public void startNewSession(File directory) {
        String sessionPath = directory.getAbsolutePath();

        if (this.currentSessionPath == null || !this.currentSessionPath.equals(sessionPath)) {
            this.nameToImageFileMap.clear();
            this.currentSessionPath = sessionPath;
            ImageFileOperationsManager.fetchImageFiles(directory);
        }
    }

    /**
     * Functions in exactly the same way as {@link UserDataManager#getNameToImageFileMap() the parent's version} but has
     * a <pre>public</pre> access modifier.
     */
    @Override
    public HashMap<String, ImageFile> getNameToImageFileMap() {
        return nameToImageFileMap;
    }

}
