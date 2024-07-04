package com.example.assignment_two.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_two.Interfaces.MapZoomCallback;
import com.example.assignment_two.R;
import com.example.assignment_two.Models.Score;
import com.example.assignment_two.Utilities.ImageLoader;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder> {


    private final ArrayList<Score> records;
    private MapZoomCallback mapZoomCallback;

    public RecordAdapter(ArrayList<Score> records, MapZoomCallback mapZoomCallback) {
        this.records = records;
        this.mapZoomCallback = mapZoomCallback;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_record_item, parent, false);

        return new RecordViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {

        Score score = getItem(position);

        ImageLoader.getInstance().load("https://images5.alphacoders.com/413/413842.jpg", holder.record_IMG_side);
        holder.record_TXT_score.setText("Your Score: " + score.getValue());
        holder.record_TXT_time.setText(score.getFormattedTime());

        holder.record_FBT_map.setOnClickListener(v -> itemClicked(score.getLatitude(),score.getLongitude()));

        if(score.getAddress() != null)
            holder.record_TXT_address.setText(score.getAddress());
        else holder.record_TXT_address.setText("Unavailable Address");

    }

    private void itemClicked(double latitude, double longitude) {
        if(mapZoomCallback != null)
            mapZoomCallback.onClickZoomListener(latitude,longitude);
    }

    @Override
    public int getItemCount() {
        return records == null ? 0 : Math.min(records.size(), 10);
    }



    private Score getItem(int position) {
        return records.get(position);
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder {
        private final CardView record_CARD_data;
        private final ExtendedFloatingActionButton record_FBT_map;
        private final ShapeableImageView record_IMG_side;
        private final MaterialTextView record_TXT_score;
        private final MaterialTextView record_TXT_address;
        private final MaterialTextView record_TXT_time;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);

            record_FBT_map = itemView.findViewById(R.id.record_FBT_map);
            record_CARD_data = itemView.findViewById(R.id.record_CARD_data);
            record_IMG_side = itemView.findViewById(R.id.record_IMG_side);
            record_TXT_score = itemView.findViewById(R.id.record_TXT_score);
            record_TXT_address= itemView.findViewById(R.id.record_TXT_address);
            record_TXT_time = itemView.findViewById(R.id.record_TXT_time);
        }
    }


}
