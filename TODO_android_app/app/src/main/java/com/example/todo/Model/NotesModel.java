package com.example.todo.Model;

public class NotesModel {

    private String notesTitle,notesSubTitle,priority,notesData,date,deadLineDate;
    private int notesId;

    public NotesModel(String notesTitle, String notesSubTitle, String priority, String notesData, int notesId, String date, String deadLineDate) {
        this.notesTitle = notesTitle;
        this.notesSubTitle = notesSubTitle;
        this.priority = priority;
        this.notesData = notesData;
        this.notesId = notesId;
        this.date=date;
        this.deadLineDate=deadLineDate;
    }

    public String getNotesTitle() {
        return notesTitle;
    }

    public String getNotesSubTitle() {
        return notesSubTitle;
    }

    public String getPriority() {
        return priority;
    }

    public String getNotesData() {
        return notesData;
    }

    public String getDate() {
        return date;
    }

    public int getNotesId() {
        return notesId;
    }

    public String getDeadLineDate() {
        return deadLineDate;
    }
}
