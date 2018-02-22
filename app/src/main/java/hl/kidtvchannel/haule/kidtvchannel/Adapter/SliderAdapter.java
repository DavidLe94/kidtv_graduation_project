package hl.kidtvchannel.haule.kidtvchannel.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import hl.kidtvchannel.haule.kidtvchannel.Activity.DetailMoviesActivity;
import hl.kidtvchannel.haule.kidtvchannel.Activity.MainActivity;
import hl.kidtvchannel.haule.kidtvchannel.Models.Slider;
import hl.kidtvchannel.haule.kidtvchannel.R;

/**
 * Created by Hau Le on 2/5/2018.
 */

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.RecyclerViewHolder> {
    private List<Slider> list = new ArrayList<>();
    private Context context;

    public SliderAdapter(List<Slider> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_cycler_view_slider, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {

        CycleViewAdapter cycleViewAdapter = new CycleViewAdapter();
        holder.imageView.setImageBitmap(cycleViewAdapter.decodeBase64toBitmap
                (list.get(position).getImages().get(1)));

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: " +  list.get(position).getPlaylistName());
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_slider);
        }
    }
}
