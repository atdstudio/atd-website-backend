package com.atd.official.entity;



public class User {
    String Id;
    String user_login;
    String user_pass;
    String user_email;
    String user_registered;
    String register_ip;
    String Verify;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_registered() {
        return user_registered;
    }

    public void setUser_registered(String user_registered) {
        this.user_registered = user_registered;
    }

    public String getRegister_ip() {
        return register_ip;
    }

    public void setRegister_ip(String register_ip) {
        this.register_ip = register_ip;
    }

    public String getVerify() {
        return Verify;
    }

    public void setVerify(String verify) {
        Verify = verify;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id='" + Id + '\'' +
                ", user_login='" + user_login + '\'' +
                ", user_pass='" + user_pass + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_registered='" + user_registered + '\'' +
                ", register_ip='" + register_ip + '\'' +
                ", Verify='" + Verify + '\'' +
                '}';
    }
}
