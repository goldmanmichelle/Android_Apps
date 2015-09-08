package com.michellegoldman.j2w3b;

import java.io.Serializable;

/**
 * Created by mgoldman on 4/23/15.
 */
public class Item implements Serializable {

    //Member variables//
    private String iName;
    private String iDescription;
    private String iPrice;

    public Item() {
        iName = "";
        iDescription = "";
        iPrice = "";
    }

    public Item(String name, String description, String price){
        iName = name;
        iDescription = description;
        iPrice = price;
    }

    public String getiName() {
        return iName;
    }

    public void setiName(String iName) {
        this.iName = iName;
    }

    public String getiDescription() {
        return iDescription;
    }

    public void setiDescription(String iDescription) {
        this.iDescription = iDescription;
    }

    public String getiPrice() {
        return iPrice;
    }

    public void setiPrice(String iPrice) {
        this.iPrice = iPrice;
    }

}
