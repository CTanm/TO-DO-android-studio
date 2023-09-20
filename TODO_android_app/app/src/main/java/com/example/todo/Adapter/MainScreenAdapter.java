package com.example.todo.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Activity.UpdateNoteActivity;
import com.example.todo.MainActivity;
import com.example.todo.Model.NotesModel;
import com.example.todo.R;

import java.util.ArrayList;

public class MainScreenAdapter extends RecyclerView.Adapter<MainScreenAdapter.ViewHolder> {

    Context context;
    ArrayList<NotesModel> notesModelArrayList;
    clickInterface click;

    public MainScreenAdapter(Context context, ArrayList<NotesModel> notesModelArrayList,clickInterface click) {
        this.context = context;
        this.notesModelArrayList = notesModelArrayList;
        this.click=click;
    }

    @NonNull
    @Override
    public MainScreenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.notes_card_view,parent,false);
        return new MainScreenAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainScreenAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        NotesModel notesModel=notesModelArrayList.get(position);
        holder.title.setText(notesModel.getNotesTitle());
        holder.date.setText(notesModel.getDate());
        holder.deadLineDate.setText(notesModel.getDeadLineDate());
        String priority=notesModel.getPriority();

        switch (priority){
            case "Red":holder.imageView.setBackgroundResource(R.drawable.red_priority);
                break;
            case "Yellow":holder.imageView.setBackgroundResource(R.drawable.yellow_priority);
                break;
            case "Green":holder.imageView.setBackgroundResource(R.drawable.green_priority);
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onClickCard(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return notesModelArrayList.size();
    }

    public  static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title,date,deadLineDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.cardViewPriority);
            title=itemView.findViewById(R.id.cardViewTitle);
            date=itemView.findViewById(R.id.cardViewDate);
            deadLineDate=itemView.findViewById(R.id.cardViewDeadLineDate);
        }
    }

    public interface clickInterface{
        public void onClickCard(int position);
    }
}
