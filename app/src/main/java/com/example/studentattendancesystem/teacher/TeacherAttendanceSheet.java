package com.example.studentattendancesystem.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.FileObserver;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.example.studentattendancesystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TeacherAttendanceSheet extends AppCompatActivity implements View.OnClickListener {
    Calendar cal;
    TextView DateTV;
    ListView container;
    Button button;
    List<String> arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_attendance_sheet);
        DateTV = findViewById(R.id.date);
        container= findViewById(R.id.list);
        cal = Calendar.getInstance();
        button=findViewById(R.id.button);
        arr=new ArrayList<>();
        DateTV.setOnClickListener(this);
        button.setOnClickListener(this);

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
            case R.id.date:

                DatePickerDialog datePicker = new DatePickerDialog(this,
                        R.style.Theme_StudentAttendanceSystem, datePickerListener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH));
                datePicker.setCancelable(false);
                datePicker.setTitle("Select the date");
                datePicker.show();

                break;
            case R.id.button:
                String date = DateTV.getText().toString();
                if(date.isEmpty()){
                    DateTV.setError("No Date is set!");
                    DateTV.requestFocus();
                    return;
                }
                FirebaseDatabase.getInstance().getReference("Attendance").child(date).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds:snapshot.getChildren()){
                            arr.add(ds.getKey().toString()+" - "+ds.getValue().toString());
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter(TeacherAttendanceSheet.this,R.layout.item_student_list,R.id.textView, arr);
                            container.setAdapter(dataAdapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                break;
        }
    }
}