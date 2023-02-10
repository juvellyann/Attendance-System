package com.example.studentattendancesystem.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.studentattendancesystem.R;

public class TeacherMenu extends AppCompatActivity implements View.OnClickListener{

    Button ViewAttendanceB, TakeAttendanceB, StudentListB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_menu);
        ViewAttendanceB = findViewById(R.id.ViewAttendance);
        TakeAttendanceB = findViewById(R.id.TakeAttendance);
        StudentListB = findViewById(R.id.StudentList);
        ViewAttendanceB.setOnClickListener(this);
        TakeAttendanceB.setOnClickListener(this);
        StudentListB.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.ViewAttendance:
                startActivity(new Intent(this,TeacherAttendanceSheet.class));
                break;
            case R.id.TakeAttendance:
                startActivity(new Intent(this,TeacherPickDate.class));
                break;
            case R.id.StudentList:
                startActivity(new Intent(this,TeacherStudentList.class));
                break;
        }
    }
}