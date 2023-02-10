package com.example.studentattendancesystem.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.studentattendancesystem.R;
import com.example.studentattendancesystem.teacher.TeacherInsertStudent;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.zip.Inflater;

public class AdminStudentList extends AppCompatActivity {


    LayoutInflater vi;
    LinearLayout layout_container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_student_list);

        vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout_container = findViewById(R.id.container);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Student");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    String student = ds.child("StudentName").getValue().toString();
                    String id = ds.child("ID").getValue().toString();
                    String Course = ds.child("Course").getValue().toString();
                    AddNew(student,id, Course);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void AddNew(String student, String id, String course) {
        View anotherView = vi.inflate(R.layout.item_student,null);
        ((TextView) anotherView.findViewById(R.id.studentname)).setText(student);
        ((TextView) anotherView.findViewById(R.id.student_id)).setText(id);
        ((TextView) anotherView.findViewById(R.id.student_course)).setText(course);

        anotherView.findViewById(R.id.remove).setOnClickListener(view -> {
            FirebaseDatabase.getInstance().getReference("Student").child(student).removeValue();
            finish();
            startActivity(getIntent());
        });
        layout_container.addView(anotherView);
    }

}