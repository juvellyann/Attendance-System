package com.example.studentattendancesystem.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentattendancesystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminTeacherList extends AppCompatActivity {
    ListView listview;
    List<String> arr;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_teacher_list);
        listview = findViewById(R.id.teacherlist);
        Button CDAcount=findViewById(R.id.btshow);

        arr = new ArrayList();
        FirebaseDatabase.getInstance().getReference("Teacher").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){

                    arr.add(ds.child("TeacherName").getValue().toString()+" - "+ds.child("Course").getValue().toString());
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter(AdminTeacherList.this, R.layout.item_student_list, R.id.textView, arr);
                    listview.setAdapter(dataAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        CDAcount.setOnClickListener(view -> startActivity(new Intent(AdminTeacherList.this,AdminCreateDeleteAccount.class)));

    }
}




