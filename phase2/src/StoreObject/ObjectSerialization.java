package StoreObject;

import javafx.scene.shape.Path;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

//cite https://examples.javacodegeeks.com/core-java/io/objectinputstream/java-objectinputstream-and-objectoutputstream-example/
public class ObjectSerialization {
    public ObjectSerialization(){
    }

    public static void saveDataTo(Object data, String address) throws IOException, FileNotFoundException{
        FileOutputStream OutputStream = new FileOutputStream(address);
        ObjectOutputStream tempOut = new ObjectOutputStream(OutputStream);
        tempOut.writeObject(data);
        tempOut.close();
    }
}
