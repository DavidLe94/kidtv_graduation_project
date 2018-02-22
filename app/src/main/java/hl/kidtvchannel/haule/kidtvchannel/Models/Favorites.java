package hl.kidtvchannel.haule.kidtvchannel.Models;

/**
 * Created by Hau Le on 1/23/2018.
 * Reference source: HSCoraline.
 */

public class Favorites {
    private String playlistName, playlistId, image,
            createDate, category, derectors, description;
    public Favorites(){

    }

    public Favorites(String playlistName, String playlistId, String image, String createDate,
                     String category, String derectors, String description) {
        this.playlistName = playlistName;
        this.playlistId = playlistId;
        this.image = image;
        this.createDate = createDate;
        this.category = category;
        this.derectors = derectors;
        this.description = description;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDerectors() {
        return derectors;
    }

    public void setDerectors(String derectors) {
        this.derectors = derectors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
