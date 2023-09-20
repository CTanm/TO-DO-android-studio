package com.example.todo.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.todo.Database.NotesDatabase;
import com.example.todo.MainActivity;
import com.example.todo.Model.NotesModel;
import com.example.todo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class UpdateNoteActivity extends AppCompatActivity {

    Intent intent;
    ArrayList<NotesModel> notesModelArrayList;
    NotesDatabase notesDatabase;
    NotesModel notesModel;
    String title,subTitle,note,priority,deadline;
    EditText editTextTitle,editTextSubtitle,editTextNote,deadLineEditText;
    ImageView priorityImageViewRed,priorityImageViewGreen,priorityImageViewYellow;
    FloatingActionButton doneUpdate;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        editTextTitle=findViewById(R.id.noteTitleEditTextUpdate);
        editTextSubtitle=findViewById(R.id.noteSubTitleEditTextUpdate);
        editTextNote=findViewById(R.id.noteEditTextUpdate);
        priorityImageViewRed=findViewById(R.id.redPriorityUpdate);
        priorityImageViewGreen=findViewById(R.id.greenPriorityUpdate);
        priorityImageViewYellow=findViewById(R.id.yellowPriorityUpdate);
        doneUpdate=findViewById(R.id.doneButtonUpdate);
        deadLineEditText=findViewById(R.id.deadLineDateEditTextUpdate);


        intent =getIntent();
        id=intent.getIntExtra("noteId",0);

        notesDatabase=new NotesDatabase(UpdateNoteActivity.this,"notes_db",null,1);
        notesModelArrayList=new ArrayList<>(notesDatabase.getDataBasedOnId(id));
        notesModel=notesModelArrayList.get(0);
        title=notesModel.getNotesTitle();
        subTitle=notesModel.getNotesSubTitle();
        note=notesModel.getNotesData();
        priority=notesModel.getPriority();
        deadline=notesModel.getDeadLineDate();

        editTextTitle.setText(title);
        editTextSubtitle.setText(subTitle);
        editTextNote.setText(note);
        deadLineEditText.setText(deadline);

        switch (priority){
            case "Red": priorityImageViewRed.setImageResource(R.drawable.ic_baseline_done_24);
                        break;
            case "Yellow": priorityImageViewYellow.setImageResource(R.drawable.ic_baseline_done_24);
                            break;
            case "Green": priorityImageViewGreen.setImageResource(R.drawable.ic_baseline_done_24);
                            break;
        }

        priorityImageViewGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(priorityImageViewGreen.getDrawable()==null){
                    priorityImageViewGreen.setImageResource(R.drawable.ic_baseline_done_24);
                    priorityImageViewRed.setImageDrawable(null);
                    priorityImageViewYellow.setImageDrawable(null);
                    priority="Green";
                }
                else{
                    priorityImageViewGreen.setImageDrawable(null);
                }

            }
        });

        priorityImageViewRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(priorityImageViewRed.getDrawable()==null){
                    priorityImageViewRed.setImageResource(R.drawable.ic_baseline_done_24);
                    priorityImageViewGreen.setImageDrawable(null);
                    priorityImageViewYellow.setImageDrawable(null);
                    priority="Red";
                }
                else{
                    priorityImageViewRed.setImageDrawable(null);
                }

            }
        });

        priorityImageViewYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(priorityImageViewYellow.getDrawable()==null){
                    priorityImageViewYellow.setImageResource(R.drawable.ic_baseline_done_24);
                    priorityImageViewRed.setImageDrawable(null);
                    priorityImageViewGreen.setImageDrawable(null);
                    priority="Yellow";
                }
                else{
                    priorityImageViewYellow.setImageDrawable(null);
                }
            }
        });



        doneUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=editTextTitle.getText().toString();
                subTitle=editTextSubtitle.getText().toString();
                note=editTextNote.getText().toString();
                deadline=deadLineEditText.getText().toString();

                String toastString;
                if(subTitle.isEmpty()||note.isEmpty()||priority.isEmpty()||title.isEmpty()||deadline.isEmpty()){
                    toastString="Some fields are missing...";
                }
                else{
                    toastString="Saving...";
                    SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
                    Calendar c=Calendar.getInstance();
                    String date="Created on: "+sdf.format(c.getTime());
                    notesDatabase.updateData(id,title,subTitle,priority,note,date,deadline);
                    intent=new Intent(UpdateNoteActivity.this,MainActivity.class);
                }

                Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_SHORT).show();
                if(toastString.equals("Saving...")){
                    startActivity(intent);
                }
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.update_top_bar,menu);

        MenuItem menuItem=menu.findItem(R.id.deleteData);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                notesDatabase.DeleteData(id);
                intent=new Intent(UpdateNoteActivity.this,MainActivity.class);
                Toast.makeText(UpdateNoteActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);



    }

}