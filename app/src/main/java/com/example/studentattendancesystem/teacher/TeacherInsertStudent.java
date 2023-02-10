package com.example.studentattendancesystem.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.studentattendancesystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TeacherInsertStudent extends AppCompatActivity {
    Button AddB;
    EditText StudentNameET, IDET;
    Spinner ClassS;
    List<String> arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_insert_student);
        StudentNameET = findViewById(R.id.StudentName);
        IDET = findViewById(R.id.ID);
        ClassS = findViewById(R.id.ClassS);
        AddB = findViewById(R.id.Add);
        AddB.setOnClickListener(view->{
            String studentName = StudentNameET.getText().toString().trim();
            String ID = IDET.getText().toString().trim();
            String course = ClassS.getSelectedItem().toString().trim();
            if(studentName.isEmpty()){
                StudentNameET.setError("Student Name is required!");
                StudentNameET.requestFocus();
                return;
            }

            if(ID.isEmpty()){
                IDET.setError("ID is required!");
                IDET.requestFocus();
                return;
            }
          DatabaseReference reference =  FirebaseDatabase.getInstance().getReference("Student").child(studentName);
            reference.child("StudentName").setValue(studentName);
            reference.child("ID").setValue(ID);
            reference.child("Course").setValue(course);
            startActivity(new Intent(this,TeacherStudentList.class));
        });

        arr = new ArrayList();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Course");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        arr.add(ds.getKey().toString());
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter(TeacherInsertStudent.this, R.layout.spinner_item1, arr);
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
}