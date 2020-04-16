package com.padaliya.phv1.Model;

public class User {

    private String id ;
    private String username ;
    private String fullname ;
    private String imgurl ;
    private String bio;

    public User() {}

    public User(String id, String username, String fullname, String imgurl, String bio) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.imgurl = imgurl;
        this.bio = bio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}
