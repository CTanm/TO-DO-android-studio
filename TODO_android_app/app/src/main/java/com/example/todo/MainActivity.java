package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.todo.Activity.NewNoteActivity;
import com.example.todo.Activity.UpdateNoteActivity;
import com.example.todo.Adapter.MainScreenAdapter;
import com.example.todo.Database.NotesDatabase;
import com.example.todo.Model.NotesModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainScreenAdapter.clickInterface{

    FloatingActionButton addNote;
    RecyclerView recyclerView;
    MainScreenAdapter mainScreenAdapter;
    ArrayList<NotesModel> notesModelArrayList;
    NotesDatabase notesDatabase;
    Intent intent;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addNote=findViewById(R.id.addNewNoteButton);
        recyclerView=findViewById(R.id.recyclerViewMain);
        relativeLayout=findViewById(R.id.emptyLayout);

        notesDatabase=new NotesDatabase(this,"notes_db",null,1);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(linearLayoutManager);

        notesModelArrayList=new ArrayList<>(notesDatabase.getAllData());
        mainScreenAdapter=new MainScreenAdapter(this,notesModelArrayList,this::onClickCard);
        recyclerView.setAdapter(mainScreenAdapter);
        mainScreenAdapter.notifyDataSetChanged();

        if(mainScreenAdapter.getItemCount()==0){
            relativeLayout.setVisibility(View.VISIBLE);
        }


        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NewNoteActivity.class));
            }
        });
    }

    @Override
    public void onClickCard(int position) {
        int id=notesModelArrayList.get(position).getNotesId();
        intent=new Intent(MainActivity.this, UpdateNoteActivity.class);
        intent.putExtra("noteId",id);
        startActivity(intent);
    }
}