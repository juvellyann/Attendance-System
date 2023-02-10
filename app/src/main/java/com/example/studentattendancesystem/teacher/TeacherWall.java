package com.example.studentattendancesystem.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentattendancesystem.LoginActivity;
import com.example.studentattendancesystem.R;
import com.example.studentattendancesystem.admin.AdminCreateDeleteAccount;
import com.example.studentattendancesystem.admin.AdminTeacherList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TeacherWall extends AppCompatActivity implements View.OnClickListener{

    Button NextB, CreateRemoveB,LogoutB;
    Spinner ClassS;
    List<String> arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_wall);
        NextB = findViewById(R.id.btnnext);
        CreateRemoveB = findViewById(R.id.CreateRemove);
        LogoutB = findViewById(R.id.Logout);
        ClassS = findViewById(R.id.ClassS);

        NextB.setOnClickListener(this);
        CreateRemoveB.setOnClickListener(this);
        LogoutB.setOnClickListener(this);


        arr = new ArrayList();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Course");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        arr.add(ds.getKey().toString());
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter(TeacherWall.this, R.layout.spinner_item, arr);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        ClassS.setAdapter(dataAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnnext:
                startActivity(new Intent(this, TeacherMenu.class));
                break;
            case R.id.CreateRemove:
                startActivity(new Intent(this, TeacherCreateDelete.class));
                break;
            case R.id.Logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                break;
        }
    }
}
