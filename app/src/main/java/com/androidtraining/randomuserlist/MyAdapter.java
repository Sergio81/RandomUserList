package com.androidtraining.randomuserlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidtraining.randomuserlist.api.GlideApp;

import com.androidtraining.randomuserlist.modules.Result;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private List<Result> myDataset;
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public EditText firstNameET;
        public EditText lastName;
        public ImageView userImageIV;
        public TextView countTV;

        public MyViewHolder(View itemView) {
            super(itemView);

            firstNameET = itemView.findViewById(R.id.firstNameET);
            lastName = itemView.findViewById(R.id.lastNameET);
            userImageIV = itemView.findViewById(R.id.userImageIV);
            countTV = itemView.findViewById(R.id.countTV);
        }
    }

    public MyAdapter(){}

    public MyAdapter(List<Result> myDataset){
        this.myDataset = myDataset;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.user_item, viewGroup, false);

        MyViewHolder viewHolder = new MyViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        Result result = myDataset.get(position);

        ImageView userImageIV = myViewHolder.userImageIV;
        GlideApp.with(context).load(result.getPicture().getLarge()).into(userImageIV);
        TextView countTV = myViewHolder.countTV;
        countTV.setText("User " + String.valueOf(position + 1));
        EditText firstNameET = myViewHolder.firstNameET;
        firstNameET.setText(capitalize(result.getName().getFirst()));
        EditText lastNameET = myViewHolder.lastName;
        lastNameET.setText(capitalize(result.getName().getLast()));
    }

    public String capitalize(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    @Override
    public int getItemCount() {
        return myDataset.size();
    }


}
