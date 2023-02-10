package com.example.studentattendancesystem.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentattendancesystem.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.jar.Attributes;

public class AdminCreateDelete extends AppCompatActivity implements View.OnClickListener {

    EditText CourseNameET, CourseCodeET;
    Button AddB, RemoveB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_delete);
        CourseCodeET = findViewById(R.id.edtcoursecode);
        CourseNameET = findViewById(R.id.edtcoursename);
        AddB = (Button)findViewById(R.id.Add);
        RemoveB = (Button)findViewById(R.id.Remove);
        AddB.setOnClickListener(this);
        RemoveB.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String name = CourseNameET.getText().toString().trim();
        String code = CourseCodeET.getText().toString().trim();

        if (name.isEmpty()) {
            CourseNameET.setError("Course Name is required!");
            CourseNameET.requestFocus();
            return;
        }

        if (code.isEmpty()) {
            CourseCodeET.setError("Course Code is required!");
            CourseCodeET.requestFocus();
            return;
        }
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Course");
        Toast.makeText(this,"test2",Toast.LENGTH_LONG);
        switch (view.getId()) {
            case R.id.Add:
                reference.child(name).setValue(code);
                startActivity(new Intent(this,AdminWall.class));
                break;
            case R.id.Remove:
                reference.child(name).removeValue();
                startActivity(new Intent(this,AdminWall.class));
                break;
        }
    }
}