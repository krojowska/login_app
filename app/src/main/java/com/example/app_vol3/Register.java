package com.example.app_vol3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity implements View.OnClickListener{
    Button bRegister;
    EditText etName, etYear, etEmail, etPassword;
    private FirebaseAuth firebaseAuth; //wifi must be on

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();

        etName = (EditText) findViewById(R.id.etName);
        etYear = (EditText) findViewById(R.id.etYear);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bRegister:
                if(validate()){
                    firebaseAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(Register.this, MainActivity.class));
                                    } else {
                                        Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
                break;
        }
    }

    private Boolean validate() {
        Boolean result = false;

        String name = etName.getText().toString();
        String year = etYear.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if(name.isEmpty() || year.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(Register.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else {
            result = true;
        }
        return result;
    }
}
