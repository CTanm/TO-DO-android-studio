package com.example.todo.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.todo.services.NotificationPublisher;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.support.v4.app.INotificationSideChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;
import com.example.todo.Database.NotesDatabase;
import com.example.todo.MainActivity;
import com.example.todo.R;
import com.example.todo.databinding.ActivityNewNoteBinding;
import com.example.todo.services.NotificationReceiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewNoteActivity extends AppCompatActivity {
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    ActivityNewNoteBinding activityNewNoteBinding;
    String title="",subtitle="",note="",priority="",toastString="",deadLine;
    NotesDatabase notesDatabase;
    Intent intent;
    private final static String default_notification_channel_id = "default" ;
    final Calendar myCalendar = Calendar. getInstance () ;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNewNoteBinding=ActivityNewNoteBinding.inflate(getLayoutInflater());
        setContentView(activityNewNoteBinding.getRoot());
        title="";
        subtitle="";
        note="";
        priority="";
        toastString="";
        deadLine="";
        notesDatabase=new NotesDatabase(getApplicationContext(),"notes_db",null,1);




       activityNewNoteBinding.greenPriority.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(activityNewNoteBinding.greenPriority.getDrawable()==null){
                   activityNewNoteBinding.greenPriority.setImageResource(R.drawable.ic_baseline_done_24);
                   activityNewNoteBinding.redPriority.setImageDrawable(null);
                   activityNewNoteBinding.yellowPriority.setImageDrawable(null);
                   priority="Green";
               }
               else{
                   activityNewNoteBinding.greenPriority.setImageDrawable(null);
               }

           }
       });

        activityNewNoteBinding.redPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activityNewNoteBinding.redPriority.getDrawable()==null){
                    activityNewNoteBinding.redPriority.setImageResource(R.drawable.ic_baseline_done_24);
                    activityNewNoteBinding.greenPriority.setImageDrawable(null);
                    activityNewNoteBinding.yellowPriority.setImageDrawable(null);
                    priority="Red";
                }
                else{
                    activityNewNoteBinding.redPriority.setImageDrawable(null);
                }

            }
        });

        activityNewNoteBinding.yellowPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activityNewNoteBinding.yellowPriority.getDrawable()==null){
                    activityNewNoteBinding.yellowPriority.setImageResource(R.drawable.ic_baseline_done_24);
                    activityNewNoteBinding.redPriority.setImageDrawable(null);
                    activityNewNoteBinding.greenPriority.setImageDrawable(null);
                    priority="Yellow";
                }
                else{
                    activityNewNoteBinding.yellowPriority.setImageDrawable(null);
                }
            }
        });


        activityNewNoteBinding.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=activityNewNoteBinding.noteTitleEditText.getText().toString();
                subtitle=activityNewNoteBinding.noteSubTitleEditText.getText().toString();
                note=activityNewNoteBinding.noteEditText.getText().toString();
                deadLine=activityNewNoteBinding.deadLineDateEditText.getText().toString();

                if(subtitle.isEmpty()||note.isEmpty()||priority.isEmpty()||title.isEmpty()||deadLine.isEmpty()){
                    toastString="Some fields are missing...";
                }
                else{
                    toastString="Saving...";
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar c = Calendar.getInstance();
                    String date = "Created on: "+sdf.format(c.getTime());
                    notesDatabase.insertData(title,subtitle,priority,note,date,deadLine);
                    intent=new Intent(NewNoteActivity.this, MainActivity.class);
                }
                Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_SHORT).show();
                if(toastString.equals("Saving...")){
                    startActivity(intent);
                }

            }
        });
    }
//    private void scheduleNotification(Notification notification  , long delay) {
//        Intent notificationIntent = new Intent( this, NotificationPublisher. class ) ;
//        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID , 1 ) ;
//        notificationIntent.putExtra(NotificationPublisher. NOTIFICATION , notification) ;
//        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
//        assert alarmManager != null;
//        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , delay , pendingIntent) ;
//    }
//    private Notification getNotification (String content) {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, default_notification_channel_id ) ;
//        builder.setContentTitle( "Scheduled Notification" ) ;
//        builder.setContentText(content) ;
//        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
//        builder.setAutoCancel( true ) ;
//        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
//        return builder.build() ;
//    }
    DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
        myCalendar .set(Calendar. YEAR , year) ;
        myCalendar .set(Calendar. MONTH , monthOfYear) ;
        myCalendar .set(Calendar. DAY_OF_MONTH , dayOfMonth) ;
        updateLabel() ;
    };
    public void setDate (View view) {
        new DatePickerDialog(
                NewNoteActivity. this, date ,
                myCalendar .get(Calendar. YEAR ) ,
                myCalendar .get(Calendar. MONTH ) ,
                myCalendar .get(Calendar. DAY_OF_MONTH )
        ).show() ;


            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 11);
            calendar.set(Calendar.MINUTE, 53);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.YEAR,myCalendar.get(Calendar.YEAR));
            calendar .set(Calendar. MONTH , myCalendar .get(Calendar. MONTH ) ) ;
            calendar .set(Calendar. DAY_OF_MONTH , myCalendar .get(Calendar. DAY_OF_MONTH )) ;

            if (calendar.getTime().compareTo(new Date()) < 0)
                calendar.add(Calendar.DAY_OF_MONTH, 1);

            Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            if (alarmManager != null) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

            }

    }
    private void updateLabel () {
        String myFormat = "dd/MM/yy" ; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat , Locale. getDefault ()) ;
        Date date = myCalendar .getTime() ;
        activityNewNoteBinding.deadLineDateEditText.setText(sdf.format(date));

    }


}