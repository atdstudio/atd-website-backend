package com.atd.official.entity;

public class Feedback {
    String Id;
    String User_login;
    String Name;
    String Title;
    String Contact;
    String Description;
    String Date;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUser_login() {
        return User_login;
    }

    public void setUser_login(String user_login) {
        User_login = user_login;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }


    @Override
    public String toString() {
        return "Feedback{" +
                "Id='" + Id + '\'' +
                ", User_login='" + User_login + '\'' +
                ", Name='" + Name + '\'' +
                ", Title='" + Title + '\'' +
                ", Contact='" + Contact + '\'' +
                ", Description='" + Description + '\'' +
                ", Date='" + Date + '\'' +
                '}';
    }
}
