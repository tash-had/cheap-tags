package StoreObject;

import javafx.scene.shape.Path;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * This class is a basic implementation of ObjectSerialization
 * Citation
 *************************************************************************************
 Title: Java ObjectInputStream and ObjectOutputStream Example
 Author: Anirudh Bhatnagar
 Date: 2014
 Availability:https://examples.javacodegeeks.com/core-java/io/objectinputstream/java-objectinputstream-and-objectoutputstream-example/
 */
public class ObjectSerialization {
    public ObjectSerialization(){
    }

    /**
     * Stores data into selected file()
     * @param data the data which will be saver
     * @param address where to store the data
     * @throws IOException when there is Input Output Problem
     * @throws FileNotFoundException when there is no such file
     */
    public static void saveDataTo(Object data, String address) throws IOException, FileNotFoundException{
        FileOutputStream OutputStream = new FileOutputStream(address);
        ObjectOutputStream tempOut = new ObjectOutputStream(OutputStream);
        tempOut.writeObject(data);
        tempOut.close();
    }
}
