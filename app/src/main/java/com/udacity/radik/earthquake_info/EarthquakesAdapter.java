package com.udacity.radik.earthquake_info;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.radik.earthquake_info.Model.Data.EarthQuakes.EarthQuake;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Radik on 31.03.2018.
 */

public class EarthquakesAdapter extends RecyclerView.Adapter<EarthquakesAdapter.EarthquakeViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(String url);
    }

    private List<EarthQuake> list;
    private final OnItemClickListener listener;

    public EarthquakesAdapter(List<EarthQuake> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @Override
    public EarthquakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new EarthquakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EarthquakeViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void swapData(List<EarthQuake> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public class EarthquakeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mLevelTextView;
        private TextView mPlaceTextView;
        private TextView mDistanceTextView;
        private TextView mDateTextView;
        private TextView mTimeTextView;
        private Context context;

        public EarthquakeViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            context = itemView.getContext();
            mLevelTextView = itemView.findViewById(R.id.tv_level);
            mPlaceTextView = itemView.findViewById(R.id.tv_place);
            mDistanceTextView = itemView.findViewById(R.id.tv_distance);
            mDateTextView = itemView.findViewById(R.id.tv_date);
            mTimeTextView = itemView.findViewById(R.id.tv_time);
        }

        public void bind(int position) {
            EarthQuake earthQuake = list.get(position);
            mLevelTextView.setText(String.valueOf(earthQuake.getMag()));
            StateListDrawable drawable = (StateListDrawable) mLevelTextView.getBackground();
            int color = getColor(earthQuake.getMag());
            drawable.setColorFilter(color, PorterDuff.Mode.SRC);
            String location = parseLocation(earthQuake.getPlace());
            mPlaceTextView.setText(location);
            String distance = parseDistance(earthQuake.getPlace());
            mDistanceTextView.setText(distance);
            String date = parseDate(earthQuake.getTime());
            mDateTextView.setText(date);
            String time = parseTime(earthQuake.getTime());
            mTimeTextView.setText(time);
        }

        private String parseTime(Long time) {
            Date dateObject = new Date(time);
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
            return timeFormat.format(dateObject);

        }

        private String parseDate(Long time) {
            Date dateObject = new Date(time);
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
            return dateFormat.format(dateObject);
        }

        private String parseDistance(String place) {
            String distance;
            if(place.contains("of")){
                distance = place.substring(0, place.indexOf("of") + 2);
            } else {
                distance = "Near the";
            }
            return distance;
        }

        private String parseLocation(String place) {
            String location;
            if(place.contains("of")){
                location = place.substring(place.indexOf("of") + 3, place.length());
            } else {
                location = place;
            }
            return location;
        }

        private int getColor(Double magnitude) {
                int magnitudeColorResourceId;
                int magnitudeFloor = (int) Math.floor(magnitude);
                switch (magnitudeFloor) {
                    case 0:
                    case 1:
                        magnitudeColorResourceId = R.color.magnitude1;
                        break;
                    case 2:
                        magnitudeColorResourceId = R.color.magnitude2;
                        break;
                    case 3:
                        magnitudeColorResourceId = R.color.magnitude3;
                        break;
                    case 4:
                        magnitudeColorResourceId = R.color.magnitude4;
                        break;
                    case 5:
                        magnitudeColorResourceId = R.color.magnitude5;
                        break;
                    case 6:
                        magnitudeColorResourceId = R.color.magnitude6;
                        break;
                    case 7:
                        magnitudeColorResourceId = R.color.magnitude7;
                        break;
                    case 8:
                        magnitudeColorResourceId = R.color.magnitude8;
                        break;
                    case 9:
                        magnitudeColorResourceId = R.color.magnitude9;
                        break;
                    default:
                        magnitudeColorResourceId = R.color.magnitude10plus;
                        break;
                }
                return ContextCompat.getColor(context, magnitudeColorResourceId);
            }

        @Override
        public void onClick(View view) {
          listener.onItemClick(list.get(getAdapterPosition()).getUrl());
        }
    }
}
