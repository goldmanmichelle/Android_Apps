//Michelle Goldman
//Java 2 Week 4 1504
//April 28, 2015

package com.michellegoldman.multiapp.data;

import java.io.Serializable;

/**
 * Created by mgoldman on 4/29/15.
 */
public class Survey implements Serializable {

    private String fName;
    private String lName;
    private String city;
    private String state;

    public Survey(){
        fName = "";
        lName = "";
        city = "";
        state = "";
    }

    public Survey (String _fName, String _lName, String _city, String _state) {

        this.fName = _fName;
        this.lName = _lName;
        this.city = _city;
        this.state = _state;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
