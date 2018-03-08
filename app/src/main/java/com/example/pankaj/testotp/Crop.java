package com.example.pankaj.testotp;



 import java.io.Serializable;

/**
 * Created by devendra on 8/3/18.
 */

public class Crop implements Serializable{

    private String Title;
    private String Description_short;
    private String description_long;
    private String image;
    private int time;
    private int counter;
    public Crop()
    {

    }


    public Crop(String Description_short, String Title, String image,String description_long,int counter,int time){
        this.Title = Title;
        this.Description_short = Description_short;
        this.description_long = description_long;
        this.image = image;
        this.time = time;
        this.counter = counter;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription_short(String description_short) {
        Description_short = description_short;
    }

    public void setDescription_long(String description_long) {
        this.description_long = description_long;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getTitle() {

        return Title;
    }

    public String getDescription_short() {
        return Description_short;
    }

    public String getDescription_long() {
        return description_long;
    }

    public String getImage() {
        return image;
    }

    public int getTime() {
        return time;
    }

    public int getCounter() {
        return counter;
    }
}
