package ictgradschool.user;

/**
 * The class will be used to receive and read data from the api json.
 */
public class UserDetailCreate {

    private int id;
    private String fname;
    private String lname;
    private String bio;
    private String username;
    private String dob;
    private int authored;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getAuthored() {
        return authored;
    }

    public void setAuthored(int authored) {
        this.authored = authored;
    }
}
