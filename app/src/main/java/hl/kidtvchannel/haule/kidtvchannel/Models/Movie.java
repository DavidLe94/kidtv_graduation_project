package hl.kidtvchannel.haule.kidtvchannel.Models;

import java.util.ArrayList;

/**
 * Created by Hau Le on 1/19/2018.
 * Reference source: Duy Pham.
 */

public class Movie{
    //declare
    private String createDate, key, playlistId, playlistName, derectors, category, description;
    private boolean statDelete;
    private ArrayList<String> images;

    public Movie(){}

    public Movie(String createDate, String key, String playlistId, String playlistName,
                 String derectors, String category, String description,
                 boolean statDelete, ArrayList<String> images) {
        this.createDate = createDate;
        this.key = key;
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.derectors = derectors;
        this.category = category;
        this.description = description;
        this.statDelete = statDelete;
        this.images = images;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getDerectors() {
        return derectors;
    }

    public void setDerectors(String derectors) {
        this.derectors = derectors;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String creatDate) {
        this.createDate = creatDate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public boolean isStatDelete() {
        return statDelete;
    }

    public void setStatDelete(boolean statDelete) {
        this.statDelete = statDelete;
    }

}
