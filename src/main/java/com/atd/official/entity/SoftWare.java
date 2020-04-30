package com.atd.official.entity;

public class SoftWare {

    String Id;
    String Name;
    String Intro;
    String Location;
    String SoftWare_class;
    String Cover_img;
    String Date;
    String Clicks;
    String Explain;
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

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getSoftWare_class() {
        return SoftWare_class;
    }

    public void setSoftWare_class(String softWare_class) {
        SoftWare_class = softWare_class;
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

    public String getExplain() {
        return Explain;
    }

    public void setExplain(String explain) {
        Explain = explain;
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
        return "SoftWare{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", Intro='" + Intro + '\'' +
                ", Location='" + Location + '\'' +
                ", SoftWare_class='" + SoftWare_class + '\'' +
                ", Cover_img='" + Cover_img + '\'' +
                ", Date='" + Date + '\'' +
                ", Clicks='" + Clicks + '\'' +
                ", Explain='" + Explain + '\'' +
                ", Temp_link='" + Temp_link + '\'' +
                ", Temp='" + Temp + '\'' +
                '}';
    }
}
