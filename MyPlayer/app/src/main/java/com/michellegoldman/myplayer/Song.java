package com.michellegoldman.myplayer;

/**
 * Created by mgoldman on 1/8/16.
 */
public class Song {

    private String name;
    private String image;

    public Song(String name, String image) {
        this.name = name;
        this.image = image;
    }



    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return  name + image;
    }
}
