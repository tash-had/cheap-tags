package StoreObject;

import java.io.*;

/**
 * This class is a basic implementation of ObjectDeSerialization
 */
public class ObjectDeSerialization {
        public ObjectDeSerialization(){
        }

    /**
     * This function get data from address(where the file is at) and restore into current data container.
     * @param address the string version of address
     * @return Object
     * @throws IOException when there is Input Output Problem
     * @throws FileNotFoundException when there is no such file
     * @throws ClassNotFoundException when there is no such class
     */
        public static Object getDataFrom(String address) throws IOException,FileNotFoundException,ClassNotFoundException {
            FileInputStream InputStream = new FileInputStream(new File(address));
            ObjectInputStream tempIn = new ObjectInputStream(InputStream);
            Object data = tempIn.readObject();
            tempIn.close();
        return data;
        }
        }
