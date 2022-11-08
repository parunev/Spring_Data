package com.example.demo.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne(optional = false)
    private Color backgroundColor;

    @Column(name = "is_public", nullable = false)
    boolean isPublic;

    @ManyToMany
    @JoinTable(name = "albums_pictures",
    joinColumns = @JoinColumn(name = "album_id"),
    inverseJoinColumns = @JoinColumn(name = "picture_id"))
    private Set<Picture> pictures;

    public Album() {
    }

    public Album(String name, Color backgroundColor, boolean isPublic) {
        this.name = name;
        this.backgroundColor = backgroundColor;
        this.isPublic = isPublic;
        this.pictures = new HashSet<>();
    }

    public Album(String name, Color backgroundColor, boolean isPublic, Set<Picture> pictures) {
        this(name, backgroundColor, isPublic);
        this.pictures = pictures;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }

}
