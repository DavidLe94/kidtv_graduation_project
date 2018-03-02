package hl.kidtvchannel.haule.kidtvchannel.Activity;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hl.kidtvchannel.haule.kidtvchannel.Adapter.FavAdapter;
import hl.kidtvchannel.haule.kidtvchannel.DB.Sqlite;
import hl.kidtvchannel.haule.kidtvchannel.Models.Favorites;
import hl.kidtvchannel.haule.kidtvchannel.R;

/**
 * Create by Hau Le on 1/23/2018.
 * Reference menthod getData form Database local, Source: HSCoraline.
 */
public class FavoriteMoviesActivity extends AppCompatActivity {
    //declare SQLite
    private Sqlite db = new Sqlite(this, "movies.sqlite",null,3);

    private ArrayList<Favorites> list;
    private FavAdapter adapter;

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add anim
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        overridePendingTransition(R.anim.layout_fav_in_up, R.anim.layout_fav_out_up);
        //set content
        setContentView(R.layout.activity_favorite_movies);

        initView();
        setDataToView();
    }

    private void initView(){
        gridView = findViewById(R.id.grid_view_fav);
        list = new ArrayList<>();
        adapter = new FavAdapter(list, FavoriteMoviesActivity.this);

        //declare for action bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_details_movies);

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

    private void setDataToView(){
        Cursor cursor = db.GetData("SELECT * FROM Movies");
        cursor.moveToFirst();
        for (int i = 0; i<cursor.getCount(); i++){
            cursor.moveToPosition(i);
            list.add(new Favorites(cursor.getString(1),
                    cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5),
                    cursor.getString(6), cursor.getString(7)));
        }

        if(list.size()!=0){
            findViewById(R.id.text_view_null_item).setVisibility(View.GONE);
        }

        gridView.setAdapter(adapter);
        adapter.notifyData(list);
    }
}
