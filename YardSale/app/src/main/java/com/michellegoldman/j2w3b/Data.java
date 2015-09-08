package com.michellegoldman.j2w3b;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

/**
 * Created by mgoldman on 4/23/15.
 */
public class Data {

    public static void writeItemArray(Context Context, ArrayList<Item> sItemList){

        FileOutputStream fos;

        try {
            fos = Context.openFileOutput("FILENAME", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(sItemList);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Item> readItemArray(Context Context) {

        try {
            FileInputStream fis = Context.openFileInput("FILENAME");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Item> sItemList = (ArrayList<Item>) ois.readObject();
            ois.close();
            return sItemList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (OptionalDataException e) {
            e.printStackTrace();
            return null;
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
