package com.example.studentattendancesystem.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.example.studentattendancesystem.LoginActivity;
import com.example.studentattendancesystem.R;
import com.google.firebase.auth.FirebaseAuth;

public class AdminWall extends AppCompatActivity implements View.OnClickListener{

    Button TeacherListBT,StudentListBT, CreateDeleteBT,UpdateCourseBT,LogoutBT;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_wall);
        TeacherListBT = findViewById(R.id.btnteacherlist);
        StudentListBT = findViewById(R.id.btnstudentlist);
        CreateDeleteBT = findViewById(R.id.btncreatecourse);
        LogoutBT = findViewById(R.id.btnlogout);
        TeacherListBT.setOnClickListener(this);
        StudentListBT .setOnClickListener(this);
        CreateDeleteBT .setOnClickListener(this);
        LogoutBT.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnteacherlist:
                startActivity(new Intent(this, AdminTeacherList.class));
                break;
            case R.id.btnstudentlist:
                startActivity(new Intent(this, AdminStudentList.class));
                break;
            case R.id.btncreatecourse:
                startActivity(new Intent(this, AdminCreateDelete.class));
                break;
            case R.id.btnlogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                break;
        }
    }


}
