package hl.kidtvchannel.haule.kidtvchannel.Activity;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hl.kidtvchannel.haule.kidtvchannel.Adapter.CycleViewAdapter;
import hl.kidtvchannel.haule.kidtvchannel.DB.Sqlite;
import hl.kidtvchannel.haule.kidtvchannel.R;

/**
* Create by Hau Le on 1/20/2018.
* Reference source: Duy Pham.
 */

public class DetailMoviesActivity extends AppCompatActivity {
    private ImageView imageViewPannel;
    private FloatingActionButton buttonLove;
    private Button buttonWatch;
    private TextView textViewPlaylistName, textViewCreateDate,
            textViewDescription, textViewDerectors, textViewCategory;

    private String PLAYLIST_ID = "";
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //disable activity animation
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
//                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        overridePendingTransition(0, 0);
        //add content
        setContentView(R.layout.activity_detail_movies);

        //call some menthod
        initView();
        getDataFromArrayList();
        setControl();
        setAnimation();
    }

    private void setAnimation() {
        NestedScrollView nest = findViewById(R.id.nested_sscroll_view);
        nest.setVisibility(View.VISIBLE);
        buttonLove.setVisibility(View.VISIBLE);
        //add anim for layout
        Animation slideIn = AnimationUtils.loadAnimation(this, R.anim.layout_in_up);
        nest.startAnimation(slideIn);
        //add anim for button
        Animation scaleButton = AnimationUtils.loadAnimation(this, R.anim.scale_button);
        buttonLove.startAnimation(scaleButton);
    }

    private void setControl() {
        //set event back action when click button of action bar
        View view = getSupportActionBar().getCustomView();
        ImageView imageView= view.findViewById(R.id.button_back_to_home);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        buttonWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send playlist id to another activity
                Intent intent = new Intent(DetailMoviesActivity.this, ListMoviesActivity.class);
                intent.putExtra("PLAYLIST_ID", PLAYLIST_ID);
                startActivity(intent);
                finish();
            }
        });

        buttonLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: add menthod for save data to sqlite when on click
                addMoviesLocal();
            }
        });
    }

    private void initView() {
        //declare for action bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_details_movies);
        //init
        imageViewPannel = findViewById(R.id.image_view_pannel);
        buttonLove = findViewById(R.id.button_add_love);
        textViewCreateDate = findViewById(R.id.text_view_create_date);
        textViewDescription = findViewById(R.id.text_view_content);
        textViewPlaylistName = findViewById(R.id.text_view_playlist_name);
        buttonWatch = findViewById(R.id.button_watch);
        textViewCategory = findViewById(R.id.text_view_category);
        textViewDerectors = findViewById(R.id.text_view_derectors);
    }

    private void getDataFromArrayList(){
        CycleViewAdapter cycleViewAdapter = new CycleViewAdapter();
        //get intent
        intent =  getIntent();
        PLAYLIST_ID = intent.getStringExtra("PlaylistId");
        //set data to view...
        imageViewPannel.setImageBitmap(cycleViewAdapter.decodeBase64toBitmap(intent.getStringExtra("Image")));
        textViewPlaylistName.setText(intent.getStringExtra("PlaylistName"));
        textViewDescription.setText(intent.getStringExtra("Description"));
        textViewCreateDate.setText(intent.getStringExtra("CreateDate"));
        textViewDerectors.setText(intent.getStringExtra("Derectors"));
        textViewCategory.setText(intent.getStringExtra("Category"));
    }

    /**
     * menthod add movies to database local
     * Create by Hau Le on 1/23/2018.
     * Reference source: HSCoraline
    */
    private void addMoviesLocal(){
        Sqlite db = new Sqlite(this, "movies.sqlite",null,3);
        //create table on database, if table not exists
//        db.createTable("CREATE TABLE IF NOT EXISTS Movies(Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "playlistName VARCHAR, playlistId VARCHAR, image VARCHAR, " +
//                "createDate VARCHAR, category VARCHAR, derectors VARCHAR, description VARCHAR)");

        //check movies exists or not
        Cursor cursor = db.GetData("SELECT * FROM Movies");
        cursor.moveToFirst();

        //check index of table
        String playlistName = "";
        boolean flag = true;
        if(cursor.getCount() != 0){
            for (int i = 0; i<cursor.getCount(); i++){
                cursor.moveToPosition(i);
                //get playlistName of table movies local
                playlistName = cursor.getString(1);
                if (playlistName.equals(intent.getStringExtra("PlaylistName"))){
                    flag = false;
                    break;
                }
            }
            if (flag){
                //insert data to table movies
                db.insertMovies(intent.getStringExtra("PlaylistName"), PLAYLIST_ID, intent.getStringExtra("Image"),
                        intent.getStringExtra("CreateDate"), intent.getStringExtra("Category"),
                        intent.getStringExtra("Derectors"),intent.getStringExtra("Description"));

                //disable button
                buttonLove.setEnabled(false);
                //show message
                Toast.makeText(DetailMoviesActivity.this,
                        "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(DetailMoviesActivity.this,
                            "Phim đã tồn tại!", Toast.LENGTH_SHORT).show();
            }
        }else {
            db.insertMovies(intent.getStringExtra("PlaylistName"), PLAYLIST_ID, intent.getStringExtra("Image"),
                    intent.getStringExtra("CreateDate"), intent.getStringExtra("Category"),
                    intent.getStringExtra("Derectors"),intent.getStringExtra("Description"));
            buttonLove.setEnabled(false);
            Toast.makeText(DetailMoviesActivity.this,
                    "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
        }
        //end check
    }
}
