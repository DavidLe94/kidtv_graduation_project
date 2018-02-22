package hl.kidtvchannel.haule.kidtvchannel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import hl.kidtvchannel.haule.kidtvchannel.R;

/**
 * Create by Hau Le on 1/20/2018.
 * Reference internet
 */
public class PlayVideoActivity extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener{

    private YouTubePlayerView youTubePlayerView;
    private String videoId = "";
    private int REQUEST_VIDEO = 1;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        initView();
        initData();
    }

    private void initData() {
        intent = getIntent();
        videoId = intent.getStringExtra("VIDEO_ID");
        youTubePlayerView.initialize(ListMoviesActivity.API_KEY, PlayVideoActivity.this);
    }

    private void initView() {
        youTubePlayerView = findViewById(R.id.youtube_player_view);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        //load video when start activity
        youTubePlayer.loadVideo(videoId);
        //set full screen
        youTubePlayer.setFullscreen(true);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(PlayVideoActivity.this, REQUEST_VIDEO);
        }else{
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_VIDEO){
            //reload video
            youTubePlayerView.initialize(ListMoviesActivity.API_KEY, PlayVideoActivity.this);
        }
    }
}
