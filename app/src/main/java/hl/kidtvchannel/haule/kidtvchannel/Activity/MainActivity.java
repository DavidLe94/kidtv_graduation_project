package hl.kidtvchannel.haule.kidtvchannel.Activity;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import hl.kidtvchannel.haule.kidtvchannel.Adapter.CycleViewAdapter;
import hl.kidtvchannel.haule.kidtvchannel.Adapter.SliderAdapter;
import hl.kidtvchannel.haule.kidtvchannel.DB.Sqlite;
import hl.kidtvchannel.haule.kidtvchannel.Models.Movie;
import hl.kidtvchannel.haule.kidtvchannel.Models.Slider;
import hl.kidtvchannel.haule.kidtvchannel.Notifications.Constants;
import hl.kidtvchannel.haule.kidtvchannel.R;

/**
 *Create by Hau Le on 1/19/2018.
 */

public class MainActivity extends AppCompatActivity{
    //declare variable use for activity
    private HorizontalInfiniteCycleViewPager paper;
    private ProgressBar progressBar;
    private List<Movie> arrayList;
    private CycleViewAdapter adaper;
    //declere for firebase...
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private List<Slider> list;
    private RecyclerView recyclerView;
    private SliderAdapter sliderAdapter;
    public int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add animation
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        overridePendingTransition(R.anim.layout_in_up, R.anim.layout_out_up);
        //add content
        setContentView(R.layout.activity_main);
        // call menthod init control
        initControl();
        setEventActionBar();
        _initFirebase();
        _createDatabaseLocal();
        _notifications();

        FirebaseMessaging.getInstance().subscribeToTopic("news");

    }

    private void _notifications() {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            NotificationChannel notificationChannel
                    = new NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setDescription(Constants.CHANNEL_DESCRIPTION);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            //notificationChannel.setSound(alarmSound, RingtoneManager.TYPE_NOTIFICATION);

            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void _createDatabaseLocal() {
        Sqlite db = new Sqlite(this, "movies.sqlite",null,3);
        //create table on database, if table not exists
        db.createTable("CREATE TABLE IF NOT EXISTS Movies(Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "playlistName VARCHAR, playlistId VARCHAR, image VARCHAR, " +
                "createDate VARCHAR, category VARCHAR, derectors VARCHAR, description VARCHAR)");
    }

    private void _initFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        updateData();
    }

    private void setEventActionBar() {
        //set event back action when click button of action bar
        View view = getSupportActionBar().getCustomView();
        ImageView imageView= view.findViewById(R.id.button_list_love_movies);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FavoriteMoviesActivity.class);
                startActivity(intent);
            }
        });
    }

    //auto scroll recycler view
    private class RemindTask extends TimerTask{
       @Override
       public void run() {
           runOnUiThread(new Runnable(){
               public void run() {
                   if(position == list.size()) {
                       position = -1;
                       position++;
                   }else{
                       position++;
                   }
                   recyclerView.getLayoutManager().scrollToPosition(position);
               }
           });
       }
    }

    private void initControl() {
        //declare for action bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_main_activity);

        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);

        paper = findViewById(R.id.horizontal_cycle);
        progressBar = findViewById(R.id.progress_bar_main);

        arrayList = new ArrayList<>();
        adaper = new CycleViewAdapter(getBaseContext(), (ArrayList<Movie>) arrayList);
    }

    //TODO: menthod get data from firebase
    private void updateData() {
        //query and condition
        Query getData = databaseReference.child("playlist").orderByChild("statDelete").equalTo(true);
        getData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //TODO: menthod called when get data success
                //clear list movies...
                arrayList.clear();
                //add movies to list
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    //get object movies from firebase
                    arrayList.add(data.getValue(Movie.class));
                }

                for (int i = arrayList.size()-1; i>4; i--){
                    list.add(new Slider(arrayList.get(i).getCreateDate(), arrayList.get(i).getKey(),
                            arrayList.get(i).getPlaylistId(), arrayList.get(i).getPlaylistName(),
                            arrayList.get(i).getDerectors(), arrayList.get(i).getCategory(),
                            arrayList.get(i).getDescription(), arrayList.get(i).isStatDelete(),
                            arrayList.get(i).getImages()));
                }

                //init slider adapter
                sliderAdapter = new SliderAdapter(list, getApplicationContext());
                //set recycler view horizontal
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                        LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManager);
                //set adapetr
                recyclerView.setAdapter(sliderAdapter);

                //auto scroll recyclerview...
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new RemindTask(), 0, 4000); // delay*/

                //TODO: important, set adapter after get data
                paper.setAdapter(adaper);

                //set visible for slide, banner, progress bar
                progressBar.setVisibility(View.GONE);
                paper.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO: menthod if get data from firebase failed
               showDialogMessage("Lỗi kết nối", MainActivity.this);
            }
        });
    }

    public void showDialogMessage(String message, Context context){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_message_load_data_failed);
        dialog.setCancelable(true);
        TextView textView = dialog.findViewById(R.id.text_view_content_message);
        Button button = dialog.findViewById(R.id.button_dismiss_dialog);
        textView.setText(message);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
