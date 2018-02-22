package hl.kidtvchannel.haule.kidtvchannel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hl.kidtvchannel.haule.kidtvchannel.Activity.DetailMoviesActivity;
import hl.kidtvchannel.haule.kidtvchannel.Activity.FavoriteMoviesActivity;
import hl.kidtvchannel.haule.kidtvchannel.DB.Sqlite;
import hl.kidtvchannel.haule.kidtvchannel.Models.Favorites;
import hl.kidtvchannel.haule.kidtvchannel.R;

/**
 * Created by Hau Le on 1/28/2018.
 */

public class FavAdapter extends BaseAdapter {

    private ArrayList<Favorites> list;
    private Context context;
    private LayoutInflater layoutInflater;
    //declare SQLite
    private Sqlite db;

    public FavAdapter(ArrayList<Favorites> list, Context context) {
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        db = new Sqlite(context, "movies.sqlite",null,3);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        Holder holder = null;

        if(view == null){
            view = layoutInflater.inflate(R.layout.custom_cycler_view, null);
            holder = new Holder();
            holder.imageView = view.findViewById(R.id.image_view_cycler_view_fav);
            holder.textView = view.findViewById(R.id.text_view_cycler_view_fav);
            holder.imageViewRemove = view.findViewById(R.id.image_view_remove);
            view.setTag(holder);
        }else{
            holder = (Holder) view.getTag();
        }

        Favorites fav = list.get(position);
        CycleViewAdapter cycleViewAdapter = new CycleViewAdapter();

        holder.textView.setText(fav.getPlaylistName());
        holder.imageView.setImageBitmap(cycleViewAdapter.decodeBase64toBitmap(fav.getImage()));

        final Holder finalHolder = holder;
        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                finalHolder.imageViewRemove.setVisibility(View.VISIBLE);

                finalHolder.imageViewRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.deleteMovies(list.get(position).getPlaylistName());
                        list.remove(position);
                        finalHolder.imageViewRemove.setVisibility(View.GONE);
                    }
                });
                return true;
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMoviesActivity.class);
                //put data to intent
                intent.putExtra("PlaylistName", list.get(position).getPlaylistName());
                intent.putExtra("PlaylistId", list.get(position).getPlaylistId());
                intent.putExtra("Image", list.get(position).getImage());
                intent.putExtra("Description", list.get(position).getDescription());
                intent.putExtra("CreateDate", list.get(position).getCreateDate());
                intent.putExtra("Derectors", list.get(position).getDerectors());
                intent.putExtra("Category", list.get(position).getCategory());
                //changed activity with data
                context.startActivity(intent);
            }
        });
        return view;
    }

    private static class Holder{
        ImageView imageView, imageViewRemove;
        TextView textView;
    }

    public void notifyData(ArrayList<Favorites> arrayList){
        this.list = arrayList;
        notifyDataSetChanged();
    }
}
