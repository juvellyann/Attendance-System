package com.example.studentattendancesystem.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.studentattendancesystem.R;

import java.util.Calendar;

public class TeacherPickDate extends AppCompatActivity implements View.OnClickListener{

    TextView DateTV;
    Button NextB;
    Calendar cal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_pick_date);
        NextB = findViewById(R.id.Next);
        DateTV = findViewById(R.id.Date);

        cal = Calendar.getInstance();
        DateTV.setOnClickListener(this);
        NextB.setOnClickListener(this);

    }

    private final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @SuppressLint("SetTextI18n")
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            String year1 = String.valueOf(selectedYear);
            String month1 = String.valueOf(selectedMonth + 1);
            String day1 = String.valueOf(selectedDay);
            DateTV.setText(year1 + "/" + month1 + "/" + day1);

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Date:

                DatePickerDialog datePicker = new DatePickerDialog(this,
                        R.style.Theme_StudentAttendanceSystem, datePickerListener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH));
                datePicker.setCancelable(false);
                datePicker.setTitle("Select the date");
                datePicker.show();
                break;
            case R.id.Next:
               String data= DateTV.getText().toString();
               if(data.isEmpty()){
                   DateTV.setError("No Date is set!");
                   DateTV.requestFocus();
                   return;
               }
                Intent i = new Intent(this,TeacherStudentAttendance.class);
                i.putExtra("date",data);
                startActivity(i);
                break;
        }

    }
}