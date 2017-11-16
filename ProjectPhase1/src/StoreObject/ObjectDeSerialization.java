package StoreObject;

import java.io.*;

public class ObjectDeSerialization {
        public ObjectDeSerialization(){

        }
        public static Object getDataFrom(String address) throws IOException,FileNotFoundException,ClassNotFoundException {
            FileInputStream InputStream = new FileInputStream(new File(address));
            ObjectInputStream tempIn = new ObjectInputStream(InputStream);
            Object data = tempIn.readObject();
            tempIn.close();
        return data;
        }
        }
