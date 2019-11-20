public class User {
    String userId;
    String userfirstname;
    String userlastname;
    String useremail;
    String userpassword;

    public User(){

    }

    public User(String userId, String userfirstname, String userlastname, String useremail, String userpassword) {
        this.userId = userId;
        this.userfirstname = userfirstname;
        this.userlastname = userlastname;
        this.useremail = useremail;
        this.userpassword = userpassword;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserfirstname() {
        return userfirstname;
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
