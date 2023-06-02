package com.example.vetext;

public class Vetlistclass {
    private String admin;
    private String uname,email,number;
    private Vetlistclass (){}
    private Vetlistclass(String admin,String uname,String email,String number){
        this.admin=admin;
        this.email=email;
        this.number=number;
        this.uname= uname;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
