package com.atd.official.entity;

public class Video {

    String Id;
    String Name;
    String Intro;
    String Author;
    String Video_class;
    String Location;
    String Cover_img;
    String Date;
    String Clicks;
    String Temp_link;
    String Temp;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIntro() {
        return Intro;
    }

    public void setIntro(String intro) {
        Intro = intro;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getVideo_class() {
        return Video_class;
    }

    public void setVideo_class(String video_class) {
        Video_class = video_class;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getCover_img() {
        return Cover_img;
    }

    public void setCover_img(String cover_img) {
        Cover_img = cover_img;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getClicks() {
        return Clicks;
    }

    public void setClicks(String clicks) {
        Clicks = clicks;
    }

    public String getTemp_link() {
        return Temp_link;
    }

    public void setTemp_link(String temp_link) {
        Temp_link = temp_link;
    }

    public String getTemp() {
        return Temp;
    }

    public void setTemp(String temp) {
        Temp = temp;
    }

    @Override
    public String toString() {
        return "Video{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", Intro='" + Intro + '\'' +
                ", Author='" + Author + '\'' +
                ", Video_class='" + Video_class + '\'' +
                ", Location='" + Location + '\'' +
                ", Cover_img='" + Cover_img + '\'' +
                ", Date='" + Date + '\'' +
                ", Clicks='" + Clicks + '\'' +
                ", Temp_link='" + Temp_link + '\'' +
                ", Temp='" + Temp + '\'' +
                '}';
    }
}
