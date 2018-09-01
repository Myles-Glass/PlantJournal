package com.example.myles.first;

import android.content.Context;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private PlantList mPlantList = new PlantList();
    private Plant infoPlant = new Plant(); //The plant that is clicked

    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;

    public RecyclerViewAdapter(PlantList plantList) {
        mPlantList = plantList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listplant, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.plantName.setText(mPlantList.getPlant(position).getName());

        holder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Plant Clicked: " + mPlantList.getPlant(position).getName());

                infoPlant = mPlantList.getPlant(position);
                Intent intent = new Intent(view.getContext(), PlantInfoActivity.class);
                intent.putExtra("Info Plant", infoPlant);
                view.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlantList.getSize();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView plantName;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            plantName = itemView.findViewById(R.id.plant_object);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

    //Not used
    public Plant getClickedPlant()
    {
        return infoPlant;
    }
}
