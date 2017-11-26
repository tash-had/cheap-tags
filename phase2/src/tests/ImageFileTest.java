//package tests;
//
//
//import model.ImageFile;
//import model.Tag;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
//import java.io.File;
//import java.util.ArrayList;
//
//public class ImageFileTest {
//    @Test
//    public void testGeneralRename(){
//        File testFile = new File("src/resources/images/logo_2.jpg");
//        ImageFile testImage = new ImageFile(testFile);
//        testImage.generalReName("@Tag1 logo_2");
//        assertEquals("");
//
//    }
//
//    @Test
//    public void testGeneralRenameWithTwoParameter(){
//        Tag t1 = new Tag("TAG1");
//        Tag t2 = new Tag("TAG2");
//        ArrayList<Tag> tempList = new ArrayList<Tag>();
//        tempList.add(t1);
//        tempList.add(t2);
//        File testFile = new File("src/resources/images/logo_2.jpg");
//        ImageFile testImage = new ImageFile(testFile);
//
//    }
//
//}
