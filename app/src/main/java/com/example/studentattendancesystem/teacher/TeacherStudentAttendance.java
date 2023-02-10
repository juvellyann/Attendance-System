package com.example.studentattendancesystem.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentattendancesystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class TeacherStudentAttendance extends AppCompatActivity {
    Button DoneB;
    LayoutInflater vi;
    LinearLayout layout_container;
    HashMap<String, String> arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_student_attendance);
        String value = getIntent().getExtras().getString("date");
        Toast.makeText(this, value, Toast.LENGTH_LONG).show();
        arr = new HashMap<>();
        vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout_container = findViewById(R.id.container);
        FirebaseDatabase.getInstance().getReference("Student").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String student = ds.child("StudentName").getValue().toString();
                    AddNew(student);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DoneB = findViewById(R.id.Done);
        DoneB.setOnClickListener(view -> {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Attendance").child(value);
            for (Map.Entry<String, String> entry : arr.entrySet()) {
                reference.child(entry.getKey()).setValue(entry.getValue());
            }
            startActivity(new Intent(this,TeacherMenu.class));
        });
    }

    private void AddNew(String student) {
        View anotherView = vi.inflate(R.layout.item_takeattendance, null);
        ((TextView) anotherView.findViewById(R.id.textView7)).setText(student);

        ((Button) anotherView.findViewById(R.id.radioButton1)).setOnClickListener(view -> {
            RadioButton radioButton = (RadioButton) anotherView.findViewById(((RadioGroup) ((RadioGroup) anotherView.findViewById(R.id.radioGroup))).getCheckedRadioButtonId());
            arr.put(student, radioButton.getText().toString());
        });

        ((Button) anotherView.findViewById(R.id.radioButton2)).setOnClickListener(view -> {
            RadioButton radioButton = (RadioButton) anotherView.findViewById(((RadioGroup) ((RadioGroup) anotherView.findViewById(R.id.radioGroup))).getCheckedRadioButtonId());
            arr.put(student, radioButton.getText().toString());
        });

        layout_container.addView(anotherView);
    }
}