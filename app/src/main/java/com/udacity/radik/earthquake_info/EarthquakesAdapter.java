package com.udacity.radik.earthquake_info;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Radik on 31.03.2018.
 */

public class EarthquakesAdapter extends RecyclerView.Adapter<EarthquakesAdapter.EarthquakeViewHolder> {

    private List<EarthQuake> list;

    public EarthquakesAdapter(List<EarthQuake> list, MainActivity context) {
        this.list = list;
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


    public class EarthquakeViewHolder extends RecyclerView.ViewHolder {

        private TextView mLevelTextView;
        private TextView mPlaceTextView;
        private TextView mDistanceTextView;
        private TextView mDateTextView;
        private TextView mTimeTextView;
        private Context context;

        public EarthquakeViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            mLevelTextView = itemView.findViewById(R.id.tv_level);
            mPlaceTextView = itemView.findViewById(R.id.tv_place);
            mDistanceTextView = itemView.findViewById(R.id.tv_distance);
            mDateTextView = itemView.findViewById(R.id.tv_date);
            mTimeTextView = itemView.findViewById(R.id.tv_time);
        }

        public void bind(int position) {
            EarthQuake earthQuake = list.get(position);
            mLevelTextView.setText(String.valueOf(earthQuake.getMagnitude()));
            StateListDrawable drawable = (StateListDrawable) mLevelTextView.getBackground();
            int color = getColor(earthQuake.getMagnitude());
            drawable.setColorFilter(color, PorterDuff.Mode.SRC);
            mPlaceTextView.setText(earthQuake.getLocation());
            mDistanceTextView.setText(earthQuake.getDistance());
            mDateTextView.setText(earthQuake.getDate());
            mTimeTextView.setText(earthQuake.getTime());
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
        }
    }
