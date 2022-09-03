package ictgradschool.user;

/**
 * This calss is used to define the information required by the user data and can be assigned values.
 * We will use this class in the creation of the JTable adapter...
 */
public class UserDataSet {

    private int id;
    private String fname;
    private String lname;
    private String bio;
    private String username;
    private String dob;
    private int authored;

    public UserDataSet(int id, String fname, String lname, String bio, String username, String dob, int authored){
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.bio = bio;
        this.username = username;
        this.dob = dob;
        this.authored = authored;
    }

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