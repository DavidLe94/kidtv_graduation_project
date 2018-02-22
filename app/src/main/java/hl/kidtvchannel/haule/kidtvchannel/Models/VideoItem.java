package hl.kidtvchannel.haule.kidtvchannel.Models;

/**
 * Created by Hau Le on 1/20/2018.
 */

public class VideoItem {
    //declare
    private String title, thumbnails, videoId, channel;

    public VideoItem(String title, String thumbnails, String videoId, String channel) {
        this.title = title;
        this.thumbnails = thumbnails;
        this.videoId = videoId;
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
