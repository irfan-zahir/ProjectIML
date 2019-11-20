package com.example.rentaloka;

public class User {
    private String userId;
    private String userfirstname;
    private String userlastname;
    private String useremail;
    private String userpassword;
    private String uType;


    public User(){

    }

    public User(String userId, String userfirstname, String userlastname, String useremail, String userpassword, String uType) {
        this.userId = userId;
        this.userfirstname = userfirstname;
        this.userlastname = userlastname;
        this.useremail = useremail;
        this.userpassword = userpassword;
        this.uType = uType;
    }

    public String getuType() {
        return uType;
    }

    public void setuType(String uType) {
        this.uType = uType;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserfirstname() {
        return userfirstname;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserfirstname(String userfirstname) {
        this.userfirstname = userfirstname;
    }

    public void setUserlastname(String userlastname) {
        this.userlastname = userlastname;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getUserlastname() {
        return userlastname;
    }

    public String getUseremail() {
        return useremail;
    }

    public String getUserpassword() {
        return userpassword;
    }
}
