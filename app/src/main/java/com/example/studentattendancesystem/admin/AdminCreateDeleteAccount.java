package com.example.studentattendancesystem.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.studentattendancesystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminCreateDeleteAccount extends AppCompatActivity implements View.OnClickListener {
    EditText TeacherNameET, IDET, EmailET, PasswordET;
    Button AddB;
    Spinner ClassS;
    List<String> arr;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addteacher);

        mAuth = FirebaseAuth.getInstance();

        TeacherNameET = findViewById(R.id.TeacherName);
        IDET = findViewById(R.id.ID);
        PasswordET = findViewById(R.id.password);
        EmailET = findViewById(R.id.Email);
        AddB = findViewById(R.id.Add);
        ClassS = (Spinner) findViewById(R.id.SelectClass);
        AddB.setOnClickListener(this);

        arr = new ArrayList();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Course");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        arr.add(ds.getKey().toString());
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter(AdminCreateDeleteAccount.this, R.layout.spinner_item1, arr);
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
        String teacherName = TeacherNameET.getText().toString().trim();
        String id = IDET.getText().toString().trim();
        String Email = EmailET.getText().toString().trim();
        String course = ClassS.getSelectedItem().toString().trim();
        String password = PasswordET.getText().toString().trim();

        if (teacherName.isEmpty()) {
            TeacherNameET.setError("Name is required!");
            TeacherNameET.requestFocus();
            return;
        }

        if (id.isEmpty()) {
            IDET.setError("ID is required!");
            IDET.requestFocus();
            return;
        }

        if (Email.isEmpty()) {
            EmailET.setError("Email is required!");
            EmailET.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            EmailET.setError("Please provide valid email!");
            EmailET.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            PasswordET.setError("Email is required!");
            PasswordET.requestFocus();
            return;
        }

        if (password.length() < 6) {
            PasswordET.setError("Minimum password length should be 6 characters!");
            PasswordET.requestFocus();
            return;
        }

        switch (view.getId()) {
            case R.id.Add:
                mAuth.createUserWithEmailAndPassword(Email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Teacher").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                reference.child("TeacherName").setValue(teacherName);
                                reference.child("ID").setValue(id);
                                reference.child("Course").setValue(course);
                                reference.child("Password").setValue(password);
                                startActivity(new Intent(AdminCreateDeleteAccount.this, AdminTeacherList.class));
                            } else {
                                Toast.makeText(AdminCreateDeleteAccount.this, "Failed to register try again!", Toast.LENGTH_LONG).show();
                            }
                        });
                break;

        }
    }


}