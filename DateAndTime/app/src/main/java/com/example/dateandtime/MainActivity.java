package com.example.dateandtime;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView dateTimeInLongTextView, format1, format2,
            format3, format4, format5, format6, format7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String dateTime;
        Calendar calendar;
        SimpleDateFormat simpleDateFormat;

        // register all the text view with appropriate IDs
        dateTimeInLongTextView = (TextView) findViewById(R.id.dateTimeLongValue);
        format1 = (TextView) findViewById(R.id.format1);
        format2 = (TextView) findViewById(R.id.format2);
        format3 = (TextView) findViewById(R.id.format3);
        format4 = (TextView) findViewById(R.id.format4);
        format5 = (TextView) findViewById(R.id.format5);
        format6 = (TextView) findViewById(R.id.format6);
        format7 = (TextView) findViewById(R.id.format7);

        // get the Long type value of the current system date
        Long dateValueInLong = System.currentTimeMillis();
        dateTimeInLongTextView.setText(dateValueInLong.toString());

        // different format type to format the
        // current date and time of the system
        // format type 1
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss aaa z");
        dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        format1.setText(dateTime);

        // format type 2
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss aaa z");
        dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        format2.setText(dateTime);

        // format type 3
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss aaa z");
        dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        format3.setText(dateTime);

        // format type 4
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd.LLL.yyyy HH:mm:ss aaa z");
        dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        format4.setText(dateTime);

        // format type 5
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd.LLLL.yyyy HH:mm:ss aaa z");
        dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        format5.setText(dateTime);

        // format type 6
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("E.LLLL.yyyy HH:mm:ss aaa z");
        dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        format6.setText(dateTime);

        // format type 7
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("EEEE.LLLL.yyyy KK:mm:ss aaa z");
        dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        format7.setText(dateTime);
    }
}