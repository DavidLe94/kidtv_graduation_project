package hl.kidtvchannel.haule.kidtvchannel.Activity;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hl.kidtvchannel.haule.kidtvchannel.Adapter.VideoItemAdapter;
import hl.kidtvchannel.haule.kidtvchannel.Models.VideoItem;
import hl.kidtvchannel.haule.kidtvchannel.R;

/**
 * Create by Hau Le on 1/22/2018.
 * Reference internet
 */
public class ListMoviesActivity extends AppCompatActivity {

    public static final String API_KEY = "AIzaSyC3U5MMfd4_BsgnigUpy8GMAX1nv1eIEHs";
    private String PLAYLIST_ID = "";

    private ArrayList<VideoItem> arrayList;
    private VideoItemAdapter adapter;
    private GridView gridView;
    //wrapper of progress bar
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //disable activity animation
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_list_movies);

        Intent intent = getIntent();
        PLAYLIST_ID = intent.getStringExtra("PLAYLIST_ID");
        String url = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" +
                PLAYLIST_ID + "&key=" + API_KEY + "&maxResults=50";

        initControl();
        setEventActionBar();
        //get json youtube
        _getJsonYoutube(url);
    }

    private void setEventActionBar() {
        //set event back action when click button of action bar
        View view = getSupportActionBar().getCustomView();
        ImageView imageView= view.findViewById(R.id.button_back_to_home);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initControl() {
        //declare for action bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_details_movies);
        //init
        arrayList = new ArrayList<>();
        adapter = new VideoItemAdapter(this, arrayList);
        gridView = findViewById(R.id.grid_view_list_movies);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(ListMoviesActivity.this, PlayVideoActivity.class);
                intent.putExtra("VIDEO_ID", arrayList.get(position).getVideoId());
                startActivity(intent);
            }
        });

        relativeLayout = findViewById(R.id.layout_progress_bar);
    }

    private void _getJsonYoutube(String url){
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArrayItems = response.getJSONArray("items");

                            String title ="", thumbUrl = "", videoId ="", channelTitle;

                            for (int i=0; i<jsonArrayItems.length(); i++){
                                //get position of item
                                JSONObject jsonObjectItem = jsonArrayItems.getJSONObject(i);

                                //get snippet at position item
                                JSONObject jsonObjectSnippet = jsonObjectItem.getJSONObject("snippet");

                                //get title
                                title = jsonObjectSnippet.getString("title");

                                //get thumbnails
                                JSONObject jsonObjectThumbnails = jsonObjectSnippet.getJSONObject("thumbnails");
                                JSONObject jsonObjectDefauft = jsonObjectThumbnails.getJSONObject("default");
                                thumbUrl = jsonObjectDefauft.getString("url");

                                //get video id
                                JSONObject jsonObjectVideoId = jsonObjectSnippet.getJSONObject("resourceId");
                                videoId = jsonObjectVideoId.getString("videoId");

                                //get channel info
                                channelTitle = jsonObjectSnippet.getString("channelTitle");

                                //add item to array list
                                arrayList.add(new VideoItem(title, thumbUrl, videoId, "Channel: " + channelTitle));
                            }

                            gridView.setAdapter(adapter);

                            gridView.setVisibility(View.VISIBLE);
                            relativeLayout.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            relativeLayout.setVisibility(View.GONE);
                            MainActivity main = new MainActivity();
                            main.showDialogMessage("Đã xảy ra lỗi khi đọc dữ liệu!", ListMoviesActivity.this);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        relativeLayout.setVisibility(View.GONE);
                        MainActivity main = new MainActivity();
                        main.showDialogMessage("Vui lòng kiểm tra kết nối Internet hoặc Wifi!", ListMoviesActivity.this);
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}
