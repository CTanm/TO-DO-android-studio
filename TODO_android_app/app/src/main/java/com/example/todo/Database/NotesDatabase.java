package com.example.todo.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todo.Model.NotesModel;

import java.util.ArrayList;

public class NotesDatabase extends SQLiteOpenHelper {
    String tableName="notesTable";
    ArrayList<NotesModel> notesModelArrayList;
    public NotesDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        notesModelArrayList=new ArrayList<>();
        db.execSQL("CREATE TABLE "+tableName+
                "(notes_title TEXT,notes_id INTEGER PRIMARY KEY Autoincrement NOT NULL, notes_subtitle TEXT, notes_data TEXT, notes_priority TEXT,date TEXT,deadLine TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertData(String title, String subTitle, String priority, String notesData, String date,String deadLine){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("notes_title",title);
        contentValues.put("notes_subtitle",subTitle);
        contentValues.put("notes_priority",priority);
        contentValues.put("notes_data",notesData);
        contentValues.put("date",date);
        contentValues.put("deadLine",deadLine);
        sqLiteDatabase.insert(tableName,null,contentValues);
        sqLiteDatabase.close();
    }
    public ArrayList<NotesModel> getAllData(){
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        notesModelArrayList=new ArrayList<>();
        Cursor cursor;
        cursor=sqLiteDatabase.rawQuery("SELECT DISTINCT * FROM "+tableName,null);
        while (cursor.moveToNext()){
                String notesTitle=cursor.getString(0);
                int notesId=cursor.getInt(1);
                String notesSubtitle=cursor.getString(2);
                String notesData=cursor.getString(3);
                String notesPriority=cursor.getString(4);
                String date=cursor.getString(5);
                String deadLine=cursor.getString(6);
                notesModelArrayList.add(new NotesModel(notesTitle,notesSubtitle,notesPriority,notesData,notesId,date,deadLine));
        }
        cursor.close();
        sqLiteDatabase.close();
        return notesModelArrayList;
    }

    public ArrayList<NotesModel> getDataBasedOnId(int id){
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        notesModelArrayList=new ArrayList<>();
        Cursor cursor;
        cursor=sqLiteDatabase.rawQuery("SELECT DISTINCT * FROM "+tableName+" WHERE notes_id ="+id,null);
        while (cursor.moveToNext()){
            String notesTitle=cursor.getString(0);
            int notesId=cursor.getInt(1);
            String notesSubtitle=cursor.getString(2);
            String notesData=cursor.getString(3);
            String notesPriority=cursor.getString(4);
            String date=cursor.getString(5);
            String deadLine=cursor.getString(6);
            notesModelArrayList.add(new NotesModel(notesTitle,notesSubtitle,notesPriority,notesData,notesId,date,deadLine));
        }
        cursor.close();
        sqLiteDatabase.close();
        return notesModelArrayList;
    }

    public void updateData(int id,String title,String subtitle,String priority,String note,String date,String deadLine){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("notes_title",title);
        contentValues.put("notes_subtitle",subtitle);
        contentValues.put("notes_priority",priority);
        contentValues.put("notes_data",note);
        contentValues.put("date",date);
        contentValues.put("deadLine",deadLine);
        sqLiteDatabase.update(tableName,contentValues,"notes_id=?",new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    public void DeleteData(int id){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.delete(tableName,"notes_id=?",new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

}
