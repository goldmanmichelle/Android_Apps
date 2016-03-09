package com.michellegoldman.widget4;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mgoldman on 10/16/15.
 */
public class ContactData {

    public static final String FILENAME = "contact.txt";
    public static List<Contact> mContactList = new ArrayList<Contact>();

    //Save contacts to persistent storage
    public static void saveData(Context context) {

        FileOutputStream fos;

        try {
            fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(mContactList);
            oos.flush();
            oos.close();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Load contacts from persistent storage
    public static ArrayList<Contact> loadData(Context context) {

        FileInputStream fis;
        try{
            fis = context.openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            mContactList = (List<Contact>) ois.readObject();
            ois.close();
            ois.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
