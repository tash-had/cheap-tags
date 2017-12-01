package tests;


import model.ImageFile;
import org.junit.Test;

import java.io.File;

public class ImageFileTest {
    @Test
    public void generalReName() throws Exception {
        File testFile = new File("src/tests/test.txt");
        ImageFile img = new ImageFile(testFile);
        img.generalReName("newName");
        assert(img.getCurrentName().equals("newName"));
    }

    @Test
    public void updateTagHistory() throws Exception {
    }

    @Test
    public void equals() throws Exception {
        File testFile = new File("src/test/tests.txt");
        ImageFile img = new ImageFile(testFile);
        File testFile2 = new File ("src/test/tests2/txt");
        ImageFile img2 = new ImageFile(testFile2);
        assert(img != img2);
    }

    @Test
    public void compareTo() throws Exception {
    }

}
