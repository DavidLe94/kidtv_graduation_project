package hl.kidtvchannel.haule.kidtvchannel.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import hl.kidtvchannel.haule.kidtvchannel.Models.VideoItem;
import hl.kidtvchannel.haule.kidtvchannel.R;

/**
 * Created by Hau Le on 1/20/2018.
 */

public class VideoItemAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<VideoItem> list;
    private LayoutInflater layoutInflater;

    public VideoItemAdapter(Context context, ArrayList<VideoItem> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
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

    public static class Holder{
        ImageView imgThumb;
        TextView textViewTitle, textViewChannel;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Holder holder = null;

        if(view == null){
            view = layoutInflater.inflate(R.layout.grid_view_one_column_list_video, null);
            holder = new Holder();
            holder.imgThumb = view.findViewById(R.id.image_view_thumb_video);
            holder.textViewChannel = view.findViewById(R.id.text_view_channel);
            holder.textViewTitle = view.findViewById(R.id.text_view_title);
            view.setTag(holder);
        }else{
            holder = (Holder) view.getTag();
        }

        VideoItem videoItem = list.get(position);

        holder.textViewTitle.setText(videoItem.getTitle());
        holder.textViewChannel.setText(videoItem.getChannel());
        Uri uri = Uri.parse(videoItem.getThumbnails());
        Picasso.with(context).load(uri).into(holder.imgThumb);

        return view;
    }
}
