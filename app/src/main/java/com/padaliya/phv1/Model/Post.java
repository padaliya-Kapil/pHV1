package com.padaliya.phv1.Model;

public class Post {
    private String location ;
    private String postid;
    private String postImage;
    private String description;
    private String publisher;

    public Post(String postid, String postimage, String description, String publisher, String location) {
        this.postid = postid ;
        this.postImage = postimage ;
        this.description = description ;
        this.publisher = publisher ;
        this.location = location ;
    }

    public Post() {
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostimage() {
        return postImage;
    }

    public void setPostimage(String postimage) {
        this.postImage = postimage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Post{" +
                "location='" + location + '\'' +
                ", postid='" + postid + '\'' +
                ", postimage='" + postImage + '\'' +
                ", description='" + description + '\'' +
                ", publisher='" + publisher + '\'' +
                '}';
    }
}
