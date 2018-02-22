package hl.kidtvchannel.haule.kidtvchannel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.ArrayList;

import hl.kidtvchannel.haule.kidtvchannel.Activity.DetailMoviesActivity;
import hl.kidtvchannel.haule.kidtvchannel.R;

/**
 * Created by Hau Le on 1/20/2018.
 * Reference source Duy Pham.
 */

public class CycleViewAdapter extends PagerAdapter {
    //declare
    private Context context;
    private LayoutInflater layoutInflater;

    //TODO: important, list use for get data of movies object
    private ArrayList<hl.kidtvchannel.haule.kidtvchannel.Models.Movie> list;

    public CycleViewAdapter(Context context, ArrayList<hl.kidtvchannel.haule.kidtvchannel.Models.Movie> list) {
        this.context = context;
        this.list = list;
        layoutInflater=LayoutInflater.from(context);
    }

    public CycleViewAdapter() {

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
    @Override
    public  void destroyItem(ViewGroup container, int posision, Object object){
        container.removeView((View)object);
    }
    @Override
    public Object instantiateItem(final ViewGroup container, final int position){

        final View view = layoutInflater.inflate(R.layout.card_item, container,false);
        //declare and init...
        final ImageView imageView = view.findViewById(R.id.imageView);
        //set data to view
        imageView.setImageBitmap(decodeBase64toBitmap(list.get(position).getImages().get(0)));
        //add view to container
        container.addView(view);

        /**
        * TODO: set event for view(item in slide image),
        * and then send data to DetailMoviesActivity
         */
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sned data to list movies activity
                Intent intent = new Intent(context, DetailMoviesActivity.class);
                intent.putExtra("Image", list.get(position).getImages().get(1));
                intent.putExtra("PlaylistName", list.get(position).getPlaylistName());
                intent.putExtra("PlaylistId", list.get(position).getPlaylistId());
                intent.putExtra("CreateDate", list.get(position).getCreateDate());
                intent.putExtra("Description", list.get(position).getDescription());
                intent.putExtra("Category", list.get(position).getCategory());
                intent.putExtra("Derectors", list.get(position).getDerectors());
                context.startActivity(intent);

            }
        });
        return view;
    }

    //TODO: menthod decode base64 to bitmap image
    public Bitmap decodeBase64toBitmap(String str){
        String pureBase64Encoded = str.substring(str.indexOf(",") + 1);
        byte[] decodedString = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}
