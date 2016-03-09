//Michelle Goldman
//MDF3 1601
//Updated 1/20/2016

package com.michellegoldman.widget4;

import java.io.Serializable;


public class Contact implements Serializable {

    private static final long serialVersionUID = 517116325584636891L;

    private  String title;
    private  String firstName;
    private  String lastName;
    private  String age;


    public Contact() {
        title = "";
        firstName = "";
        lastName = "";
        age = "";
    }



    public Contact(String _title, String _firstName, String _lastName, String _age){
        this.title = _title;
        this.firstName = _firstName;
        this.lastName = _lastName;
        this.age = _age;
    }

    public  String getTitle() {
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public  String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public  String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public  String getAge() {
        return age;
    }

    public void setAge(String age){
        this.age = age;
    }


    public String toString(){
        return firstName + lastName + "\"" + title + age;
    }
}
