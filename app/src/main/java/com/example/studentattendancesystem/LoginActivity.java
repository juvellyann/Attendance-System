package com.example.studentattendancesystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentattendancesystem.admin.AdminWall;
import com.example.studentattendancesystem.teacher.TeacherWall;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Spinner spinner;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.editText2);
        spinner = (Spinner) findViewById(R.id.spinner);


        List<String> categories = new ArrayList();
        categories.add("Admin");
        categories.add("Teacher");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter(this, R.layout.spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }


    public void onButtonClick(View v) {
        String userid = username.getText().toString().trim();
        String Password = password.getText().toString().trim();
        String Goto = spinner.getSelectedItem().toString().trim();

        if (userid.isEmpty()) {
            username.setError("Email is required!");
            username.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(userid).matches()) {
            username.setError("Please enter valid email!");
            username.requestFocus();
            return;
        }

        if (Password.isEmpty()) {
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }

        if (Password.length() < 6) {
            password.setError("Minimum password length is 6 characters!");
            password.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(userid, Password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Goto);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(UserID)) {
                            username.getText().clear();
                            password.getText().clear();
                            if(Goto.equals("Admin")){
                                startActivity(new Intent(LoginActivity.this, AdminWall.class));
                            }
                            if(Goto.equals("Teacher")){
                                startActivity(new Intent(LoginActivity.this, TeacherWall.class));
                            }
                        }else{
                            Toast.makeText(LoginActivity.this, "No account in "+Goto+"!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            } else {
                Toast.makeText(LoginActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
            }
        });
    }
}

