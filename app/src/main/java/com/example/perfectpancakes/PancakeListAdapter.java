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
    private OnItemListener onItemListener;

    public PancakeListAdapter(OnItemListener onItemListener){
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pancake_list_item, parent, false);
        return new PancakeViewHolder(view,onItemListener);
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



    public class PancakeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        OnItemListener onItemListener;
        public PancakeViewHolder(@NonNull View itemView, OnItemListener onItemListener){

            super(itemView);
            this.onItemListener=onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }
    public interface OnItemListener {
        void onItemClick(int position);
    }
}
