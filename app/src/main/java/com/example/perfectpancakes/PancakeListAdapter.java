package com.example.perfectpancakes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perfectpancakes.models.Pancake;

import java.util.Collections;
import java.util.List;

public class PancakeListAdapter extends RecyclerView.Adapter {

    private List<Pancake> pancakes = Collections.emptyList();

    public PancakeListAdapter(){
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pancake_list_item, parent, false);
        return new PancakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position){
        TextView titleView = holder.itemView.findViewById(R.id.list_item_title);
        TextView dateView = holder.itemView.findViewById(R.id.list_item_date);
        titleView.setText(pancakes.get(position).getTitle());
        dateView.setText(pancakes.get(position).getDate());
    }

    @Override
    public int getItemCount() {return pancakes.size();}

    public void setPancakes(List<Pancake> pancakes){
        this.pancakes = pancakes;
        notifyDataSetChanged();
    }

    class PancakeViewHolder extends RecyclerView.ViewHolder{

        public PancakeViewHolder(@NonNull View itemView){

            super(itemView);
        }
    }
}
